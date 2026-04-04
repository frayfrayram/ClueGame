package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.Arrays;

import experiment.TestBoardCell;

public class Board {
	//----------------------------------------Variables--------------------------------
	private static Board theInstance = new Board();
	private Map<Character, Room> roomMap;
	private String layoutConfigFile;
	private String setupConfigFile;


	public BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	public static int ROWS;
	public static int COLS;
	private Set<Character> setupSet = new HashSet<Character>();
	private Set<Character> configSet = new HashSet<Character>();


	private Board() {
		roomMap = new HashMap<>();
	}

	public void initialize(){
		loadSetupConfig();
		loadLayoutConfig();
	}

	//---------------------------Load_Config_Files-------------------------------------------

	public void loadSetupConfig(){
	    try (Scanner scanner = new Scanner(new File(setupConfigFile))) {
	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine().trim();

	            if (line.isEmpty() || line.startsWith("/")) {
	                continue;
	            }

	            String[] parts = line.split(",");
	            if (parts.length < 3) {
	                throw new BadConfigFormatException("Bad configuration file");
	            }

	            String type = parts[0].trim();
	            String name = parts[1].trim();
	            char symbol = parts[2].trim().charAt(0);
	            
	            setupSet.add(symbol);
	            
	            if (type.equals("Room")) {
	                roomMap.put(symbol, new Room(name));
	            } else if (type.equals("Space")) {
	            	roomMap.put(symbol, new Room(name));
	            }

	        }
	    } catch (FileNotFoundException e) {
	    }
	}

	
	
	public void loadLayoutConfig() {
	    try (Scanner scanner = new Scanner(new File(layoutConfigFile))) {
	        ROWS = 0;
	        COLS = -1;

	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine().trim();

	            if (line.isEmpty()) {
	                continue;
	            }

	            String[] parts = line.split(",");

	            if (COLS == -1) {
	                COLS = parts.length;
	            } else if (parts.length != COLS) {
	                throw new BadConfigFormatException("Inconsistent column count in layout file");
	            }

	            ROWS++;
	        }
	    } catch (FileNotFoundException e) {
	    }

	    try (Scanner scanner = new Scanner(new File(layoutConfigFile))) {
	        grid = new BoardCell[ROWS][COLS];
	        int row = 0;

	        while (scanner.hasNextLine()) {
	            String line = scanner.nextLine().trim();

	            if (line.isEmpty()) {
	                continue;
	            }

	            String[] parts = line.split(",");
	            for (int col = 0; col < COLS; col++) {
	                String token = parts[col].trim();
	                
	                configSet.add(token.charAt(0));
	                
	                grid[row][col] = new BoardCell(token, row, col, grid);
	            }

	            row++;
	        }
	    } catch (FileNotFoundException e) {
	    }

	    checkAgainstFiles();	
	    
	 // assign label cells, center cells, and secret passages to rooms
	    for (int r = 0; r < ROWS; r++) {
	    	for (int c = 0; c < COLS; c++) {
	    		BoardCell currentCell = grid[r][c];
	    		Room currentRoom = roomMap.get(currentCell.getInitial());

	    		if (currentRoom != null) {
	    			if (currentCell.isLabel()) {
	    				currentRoom.setLabelCell(currentCell);
	    			}
	    			if (currentCell.isRoomCenter()) {
	    				currentRoom.setCenterCell(currentCell);
	    			}
	    			if (currentCell.getSecretPassage() != ' ') {
	    				currentRoom.setSecretPassage(currentCell.getSecretPassage());
	    			}
	    		}
	    	}
	    }


	    // assign doorways to rooms
	    for (int r = 0; r < ROWS; r++) {
	    	for (int c = 0; c < COLS; c++) {
	    		BoardCell currentCell = grid[r][c];

	    		if (currentCell.isDoorway()) {
	    			BoardCell roomCell = null;

	    			if (currentCell.getDoorDirection() == DoorDirection.UP) {
	    				roomCell = grid[r - 1][c];
	    			}
	    			else if (currentCell.getDoorDirection() == DoorDirection.DOWN) {
	    				roomCell = grid[r + 1][c];
	    			}
	    			else if (currentCell.getDoorDirection() == DoorDirection.LEFT) {
	    				roomCell = grid[r][c - 1];
	    			}
	    			else if (currentCell.getDoorDirection() == DoorDirection.RIGHT) {
	    				roomCell = grid[r][c + 1];
	    			}

	    			if (roomCell != null) {
	    				if (roomCell.getInitial() != 'W' && roomCell.getInitial() != 'X') {
	    					Room currentRoom = getRoom(roomCell);
	    					currentRoom.addDoorCell(currentCell);
	    					
	    				}
	    				}
	    			}
	    		}
	    	}

	    for (int r = 0; r < ROWS; r++) {
	    	for (int c = 0; c < COLS; c++) {
	    		grid[r][c].addAdjacencies(grid, ROWS, COLS);
	    	}
	    }
	}


	//-----------------------Target_functions-------------------------------

	public void calcTargets(BoardCell startCell, int pathlength) {
		visited = new HashSet<>();
		targets = new HashSet<>();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
		//set visited and targets sets, then start recursive func
	}

	public void findAllTargets(BoardCell cell, int pathlen) {
		for (BoardCell adjCell : cell.getAdjList()) {
			if (visited.contains(adjCell)) {
				continue;
			}

			if (adjCell.getOccupied() && !adjCell.isRoom()) {
				continue;
			}

			visited.add(adjCell);

			if (pathlen == 1 || adjCell.isRoom()) {
				targets.add(adjCell);
			}
			else {
				findAllTargets(adjCell, pathlen - 1);
			}

			visited.remove(adjCell);
		}
	}
	
	//-----------------------------File init check function------------------------
	
	
	public void checkAgainstFiles() {
	    if (!configSet.equals(setupSet)) {
	        throw new BadConfigFormatException("Bad room in setup files");
	    }
		}
	


	
	//------------------------------Getters------------------------------------

	public static Board getInstance() {
		return theInstance;
	}
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	public int getNumRows() {
		return ROWS;
	}
	public int getNumColumns() {
		return COLS;
	}
	public Room getRoom(char c) {
		return roomMap.get(c);
	}

	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public void setConfigFiles(String layoutFile, String setupFile) {
		this.layoutConfigFile = layoutFile;
		this.setupConfigFile = setupFile;
	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

}
