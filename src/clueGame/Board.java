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
	private Map<Character, Room> roomMapChar;

	
	//These contain the cards and use strings to access
	private Map<String, Card> roomMap;
	private Map<String, Card> weaponMap;
	private Map<String, Card> playerMap;
	private Map<String, Card> cardMap;
	
	//files
	private String layoutConfigFile;
	private String setupConfigFile;

	//board cells and board data
	public BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	public static int ROWS;
	public static int COLS;
	
	//sets to contain all info/data
	private Set<Character> setupSet = new HashSet<Character>();
	private Set<Character> configSet = new HashSet<Character>();
	private Set<Player> playerSet = new HashSet<Player>();
	private Set<String> weaponSet = new HashSet<String>();
	private Set<String> roomSet = new HashSet<String>();
	private Set<Card> deck = new HashSet<Card>();
	
	//answer
	private Card[] theAnswer;


	private  Board() {
		roomMapChar = new HashMap<>();
		roomMap = new HashMap<>();
		weaponMap = new HashMap<>();
		playerMap = new HashMap<>();
		cardMap = new HashMap<>();
	}

	public void initialize(){
	    roomMapChar.clear();
	    roomMap.clear();
	    weaponMap.clear();
	    playerMap.clear();
	    cardMap.clear();

	    setupSet.clear();
	    configSet.clear();
	    playerSet.clear();
	    weaponSet.clear();
	    roomSet.clear();
	    deck.clear();

	    targets = new HashSet<>();
	    visited = new HashSet<>();

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

				// handle players
				if(line.charAt(0) == 'P') {
					String[] parts = line.split(",");
					String name = parts[1].trim();
					String color = parts[2].trim();
					String type = parts[3].trim();
					int row = Integer.parseInt(parts[4].trim());
					int col = Integer.parseInt(parts[5].trim());
					if(type.equals("Human")) {
						playerSet.add(new HumanPlayer(name, color, row, col));
					} 
					if(type.equals("Computer")) {
						playerSet.add(new ComputerPlayer(name, color, row, col));
					}
					
					Card card = new Card(name, CardType.PLAYER);
					playerMap.put(name, card);
					cardMap.put(name, card);
					deck.add(card);
					
					continue;
				}
				
				// weapon handling
				if (line.charAt(0) == 'W') {
					String[] parts = line.split(",");
					String weaponName = parts[1].trim();
					weaponSet.add(weaponName);

					Card card = new Card(weaponName, CardType.WEAPON);
					weaponMap.put(weaponName, card);
					cardMap.put(weaponName, card);
					deck.add(card);

					continue;
				}


				// handle rooms
				String[] parts = line.split(",");
				if (parts.length < 3) {
					throw new BadConfigFormatException("Bad configuration file");
				}

				String type = parts[0].trim();
				String name = parts[1].trim();
				char symbol = parts[2].trim().charAt(0);

				setupSet.add(symbol);

				if (type.equals("Room")) {
					roomMapChar.put(symbol, new Room(name));
					roomSet.add(name);
					
					Card card = new Card(name, CardType.ROOM);
					
					deck.add(card);
					cardMap.put(name, card);
				} else if (type.equals("Space")) {
					roomMapChar.put(symbol, new Room(name));
				}

			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException("could not find file: " + setupConfigFile, e);
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
			throw new RuntimeException("could not find file: " + setupConfigFile, e);
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

		//make sure files initialize
		checkAgainstFiles();	

		
		
		// assign label cells, center cells, and secret passages to rooms
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				BoardCell currentCell = grid[r][c];
				Room currentRoom = roomMapChar.get(currentCell.getInitial());

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
		
		//get solution
	//	getAnswer();
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

	//-----------------------------Card functions----------------------------------
	
	
	public void deal() {
		int i = 0;
		for(Player p : playerSet) {
			//currently goes in order and hands out cards to player, doesn't remove from deck
			p.setHand(playerMap.get(playerSet.iterator().next().getName()),
					weaponMap.get(weaponSet.iterator().next()),
					getRoomMap().get(roomSet.iterator().next()));
			i++;
		}
	}
	
	public void getAnswer() {
		//currently just sets solution to the first card of each type, need to implement random element
		Solution sol = new Solution(playerMap.get(playerSet.iterator().next().getName()),
									weaponMap.get(weaponSet.iterator().next()),
									getRoomMap().get(roomSet.iterator().next()));
		
		theAnswer = sol.getAnswer();
	}


	//------------------------------Getters------------------------------------

	public static Board getInstance() {
		return theInstance;
	}
	public Set<Card> getDeck(){
		return deck;
	}
	
	public Card getCard(String name) {
		return cardMap.get(name);
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
		return roomMapChar.get(c);
	}

	public Room getRoom(BoardCell cell) {
		return roomMapChar.get(cell.getInitial());
	}

	public void setConfigFiles(String layoutFile, String setupFile) {
		this.layoutConfigFile = layoutFile;
		this.setupConfigFile = setupFile;
	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public Map<String, Card> getRoomMap() {
		return roomMap;
	}
	
	public Map<String, Card> getPlayerMap() {
		return playerMap;
	}
	
	public Map<String, Card> getWeaponMap() {
		return weaponMap;
	}

	public void setRoomMap(Map<String, Card> roomMap) {
		this.roomMap = roomMap;
	}

}
