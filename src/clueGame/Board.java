package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
	
	private int currentPlayerIndex = 0;
	private int dieRoll = 0;
	private boolean humanTurnFinished = true;
	private boolean awaitingHumanMove = false;
	
	private static Board theInstance = new Board();
	private Map<Character, Room> roomMapChar;

	private Map<String, Card> roomMap;
	private Map<String, Card> weaponMap;
	private Map<String, Player> playerMap;
	private Map<String, Card> cardMap;

	private String layoutConfigFile;
	private String setupConfigFile;

	public BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	public static int ROWS;
	public static int COLS;

	private Set<Character> setupSet = new HashSet<Character>();
	private Set<Character> configSet = new HashSet<Character>();
	private ArrayList<Player> players = new ArrayList<>();
	private Set<String> playerNameSet = new HashSet<String>();
	private Set<String> weaponSet = new HashSet<String>();
	private Set<String> roomSet = new HashSet<String>();
	private ArrayList<Card> deck = new ArrayList<Card>();
	Set<Card> tempSol = new HashSet<>();

	private Solution theAnswer;

	private Board() {
		roomMapChar = new HashMap<>();
		roomMap = new HashMap<>();
		weaponMap = new HashMap<>();
		playerMap = new HashMap<>();
		cardMap = new HashMap<>();
	}

	public void initialize() {
		roomMapChar.clear();
		roomMap.clear();
		weaponMap.clear();
		playerMap.clear();
		cardMap.clear();

		setupSet.clear();
		configSet.clear();
		players.clear();
		weaponSet.clear();
		roomSet.clear();
		deck.clear();

		targets = new HashSet<>();
		visited = new HashSet<>();

		loadSetupConfig();
		loadLayoutConfig();
	}

	public void loadSetupConfig() {
		try (Scanner scanner = new Scanner(new File(setupConfigFile))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();

				if (line.isEmpty() || line.startsWith("/")) {
					continue;
				}

				if (line.charAt(0) == 'P') {
					String[] parts = line.split(",");
					String name = parts[1].trim();
					String color = parts[2].trim();
					String type = parts[3].trim();
					int row = Integer.parseInt(parts[4].trim());
					int col = Integer.parseInt(parts[5].trim());

					if (type.equals("Human")) {
						Player player = new HumanPlayer(name, color, row, col);
						players.add(player);
						playerNameSet.add(name);
						playerMap.put(name, player);
					}

					if (type.equals("Computer")) {
						Player player = new ComputerPlayer(name, color, row, col);
						players.add(player);
						playerNameSet.add(name);
						playerMap.put(name, player);
					}

					Card card = new Card(name, CardType.PLAYER);
					cardMap.put(name, card);
					deck.add(card);

					continue;
				}

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
				    roomMap.put(name, card);
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

		checkAgainstFiles();

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

		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				BoardCell currentCell = grid[r][c];

				if (currentCell.isDoorway()) {
					BoardCell roomCell = null;

					if (currentCell.getDoorDirection() == DoorDirection.UP) {
						roomCell = grid[r - 1][c];
					} else if (currentCell.getDoorDirection() == DoorDirection.DOWN) {
						roomCell = grid[r + 1][c];
					} else if (currentCell.getDoorDirection() == DoorDirection.LEFT) {
						roomCell = grid[r][c - 1];
					} else if (currentCell.getDoorDirection() == DoorDirection.RIGHT) {
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

	public void calcTargets(BoardCell startCell, int pathlength) {
		visited = new HashSet<>();
		targets = new HashSet<>();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
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
			} else {
				findAllTargets(adjCell, pathlen - 1);
			}

			visited.remove(adjCell);
		}
	}

	//--------------------------------init funcs---------------------------
	
	public void checkAgainstFiles() {
		if (!configSet.equals(setupSet)) {
			throw new BadConfigFormatException("Bad room in setup files");
		}
	}
	
	public void splashScreen() {
		JOptionPane.showMessageDialog(
			    null,
			    "You are Franklin.\nCan you find the solution before your evil ass roomates?",
			    "Welcome to Clue (GOON HOUSE VERSION)",
			    JOptionPane.INFORMATION_MESSAGE
			);
	}

	//--------------------------------card functions---------------------------
	
	public void deal() {
		ArrayList<Card> rooms = new ArrayList<>();
		ArrayList<Card> people = new ArrayList<>();
		ArrayList<Card> weapons = new ArrayList<>();
		ArrayList<Card> remainingCards = new ArrayList<>();

		Random rand = new Random();

		for (Card c : deck) {
			if (c.getType() == CardType.ROOM) {
				rooms.add(c);
			} else if (c.getType() == CardType.PLAYER) {
				people.add(c);
			} else if (c.getType() == CardType.WEAPON) {
				weapons.add(c);
			}
		}

		Card roomAnswer = rooms.remove(rand.nextInt(rooms.size()));
		Card personAnswer = people.remove(rand.nextInt(people.size()));
		Card weaponAnswer = weapons.remove(rand.nextInt(weapons.size()));

		theAnswer = new Solution(roomAnswer, personAnswer, weaponAnswer);

		remainingCards.addAll(rooms);
		remainingCards.addAll(people);
		remainingCards.addAll(weapons);

		Collections.shuffle(remainingCards);

		for (Player p : players) {
			p.getHand().clear();
		}

		int playerIndex = 0;
		for (Card c : remainingCards) {
			players.get(playerIndex).updateHand(c);
			playerIndex = (playerIndex + 1) % players.size();
		}
	}

	public void setSolution(String player, String weapon, String room) {
		theAnswer = new Solution(cardMap.get(player), cardMap.get(weapon), cardMap.get(room));
	}

	public boolean checkAccusation(Card p, Card w, Card r) {
	    return theAnswer.getPerson().equals(p)
	        && theAnswer.getWeapon().equals(w)
	        && theAnswer.getRoom().equals(r);
	}

	public Card handleSuggestion(Card p, Card w, Card r, Player accuser) {
	    int startIndex = players.indexOf(accuser);

	    for (int i = 1; i < players.size(); i++) {
	        Player current = players.get((startIndex + i) % players.size());
	        Card disproval = current.disproveSuggestion(p, w, r);
	        if (disproval != null) {
	            return disproval;
	        }
	    }
	    return null;
	}
	
	//-----------------------------------turn based functions--------------------------------
	
	public void nextPlayerFlow() {
	    Player current = players.get(currentPlayerIndex);

	    if (current instanceof HumanPlayer && !humanTurnFinished) {
	        javax.swing.JOptionPane.showMessageDialog(null, "You must finish your turn!");
	        return;
	    }

	    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	    Player newPlayer = players.get(currentPlayerIndex);

	    dieRoll = new Random().nextInt(6) + 1;
	    calcTargets(getCell(newPlayer.getRow(), newPlayer.getCol()), dieRoll);

	    if (newPlayer instanceof HumanPlayer) {
	        awaitingHumanMove = true;
	        humanTurnFinished = false;
	    } else {
	        awaitingHumanMove = false;
	        BoardCell destination = newPlayer.selectTarget();
	        newPlayer.setLocation(destination.getRow(), destination.getCol());

	    }
	}
	
	public void handleBoardClick(BoardCell clickedCell) {
	    Player current = players.get(currentPlayerIndex);

	    if (!(current instanceof HumanPlayer)) {
	        return;
	    }
	    if (!awaitingHumanMove) {
	        return;
	    }
	    if (!targets.contains(clickedCell)) {
	        JOptionPane.showMessageDialog(null, "Invalid target selected");
	        return;
	    }
	    current.setLocation(clickedCell.getRow(), clickedCell.getCol());

	    awaitingHumanMove = false;
	    humanTurnFinished = true;
	}
	
	//------------------------------getters---------------------------------
	
	public static Board getInstance() {
		return theInstance;
	}

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public Card getCard(String name) {
		return cardMap.get(name);
	}

	public Player getPlayer(String name) {
		return playerMap.get(name);
	}

	public Solution getAnswer() {
		return theAnswer;
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

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public Map<String, Card> getRoomMap() {
		return roomMap;
	}

	public Map<String, Player> getPlayerMap() {
		return playerMap;
	}

	public Map<String, Card> getWeaponMap() {
		return weaponMap;
	}

	public Map<Character, Room> getRoomMapChar() {
		return roomMapChar;
	}

	public void setRoomMap(Map<String, Card> roomMap) {
		this.roomMap = roomMap;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}
}