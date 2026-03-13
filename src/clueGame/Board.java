package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Board {

	/*
     * variable and methods used for singleton pattern
     */
	private static Board theInstance = new Board();
	private Map<Character, Room> roomMap;
	private String layoutConfigFile;
	private String setupConfigFile;
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;


     // constructor is private to ensure only one can be created
     private Board() {
            super() ;
            roomMap = new HashMap<>();
     }
     // this method returns the only Board
     public static Board getInstance() {
            return theInstance;
     }
     /*
      * initialize the board (since we are using singleton pattern)
      */
     public void initialize(){
    	loadSetupConfig();
    	loadLayoutConfig();
     }
	 public BoardCell getCell(int row, int column) {
		// TODO Auto-generated method stub
		return grid[row][column];
	 }
	 public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	 }
	 public int getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	 }
	 public Room getRoom(char c) {
		// TODO Auto-generated method stub
		return roomMap.get(c);
	 }
	 
	 public Room getRoom(BoardCell cell) {
			return roomMap.get(cell.getInitial());
		}
	 
	 public void setConfigFiles(String layoutFile, String setupFile) {
			this.layoutConfigFile = layoutFile;
			this.setupConfigFile = setupFile;
		}
	 
	 private void loadSetupConfig() {
			roomMap.put('C', new Room("Conservatory"));
			roomMap.put('B', new Room("Ballroom"));
			roomMap.put('R', new Room("Billiard Room"));
			roomMap.put('D', new Room("Dining Room"));
			roomMap.put('W', new Room("Walkway"));
			roomMap.put('K', new Room("Kitchen"));
			roomMap.put('L', new Room("Library"));
			roomMap.put('O', new Room("Lounge"));
			roomMap.put('S', new Room("Study"));
			roomMap.put('H', new Room("Hall"));
			roomMap.put('X', new Room("Unused"));
		}
	 
	 private void loadLayoutConfig() {
			try {
				Scanner scanner = new Scanner(new File(layoutConfigFile));

				int rowCount = 0;
				int colCount = -1;

				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] parts = line.split(",");

					if (colCount == -1) {
						colCount = parts.length;
					} else if (parts.length != colCount) {
						scanner.close();
						throw new IllegalArgumentException("layout rows do not all have the same number of columns");
					}

					rowCount++;
				}
				scanner.close();

				numRows = rowCount;
				numColumns = colCount;
				grid = new BoardCell[numRows][numColumns];

				scanner = new Scanner(new File(layoutConfigFile));
				int row = 0;

				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] parts = line.split(",");

					for (int col = 0; col < numColumns; col++) {
						String token = parts[col].trim();
						grid[row][col] = new BoardCell(token);
					}

					row++;
				}

				scanner.close();

			} catch (FileNotFoundException e) {
				throw new RuntimeException("cannot find layout file");
			}
		}

}
