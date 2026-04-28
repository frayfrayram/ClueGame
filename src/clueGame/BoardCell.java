package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	//---------------------------private_variables-------------------------------------------
	private char initial;
	private boolean label;
	private boolean roomCenter;
	private char secretPassage;
	private DoorDirection doorDirection;
	private Set<BoardCell> adjList;
	private boolean isRoom, isOccupied ;
	private int row;
	private int column;
	private boolean highlighted;



	public BoardCell(String token, int row, int col, BoardCell[][] grid) {
		this.row = row;
		this.column = col;
		this.initial = token.charAt(0);
		this.doorDirection = DoorDirection.NONE;
		this.label = false;
		this.roomCenter = false;
		this.secretPassage = ' ';
		adjList = new HashSet<>();

		
		
		//check if the token for the cell is more than one character, this means we have a door
		//second is going to be the arrow pointing to which way the door is facing.
		// and we then use if statements to figure out which way the door is facing
		if (!token.equals("W") && token.length() == 1) {
			isRoom = true;
		} else if((token.length() > 1 && token.charAt(0) != 'W') && (!Character.isLetter(token.charAt(1)))){
			isRoom = true;
		} 
		
		
		if (token.length() > 1) {
		    char second = token.charAt(1);

		    switch (second) {
		        case '<':
		            doorDirection = DoorDirection.LEFT;
		            break;
		        case '>':
		            doorDirection = DoorDirection.RIGHT;
		            break;
		        case '^':
		            doorDirection = DoorDirection.UP;
		            break;
		        case 'v':
		            doorDirection = DoorDirection.DOWN;
		            break;
		        case '#':
		            label = true;
		            break;
		        case '*':
		            roomCenter = true;
		            break;
		        default:
		            secretPassage = second;
		            break;
		    }
		}
	}
	
	
	//-----------------------Adjacencies functions-------------------------------
	
	

	 public void addAdjacencies(BoardCell[][] grid, int numRows, int numCols) {
			adjList.clear();
			

			Board board = Board.getInstance();
		

			if (isRoom && !roomCenter) {
				return;
			}

			//handling adjacencies from room centers
			if (roomCenter) {
				Room currentRoom = board.getRoom(this);
				
				adjList.addAll(currentRoom.getDoorCells());

				if (currentRoom.getSecretPassage() != ' ') {
					Room destinationRoom = board.getRoom(currentRoom.getSecretPassage());
					if (destinationRoom != null && destinationRoom.getCenterCell() != null) {
						adjList.add(destinationRoom.getCenterCell());
					}
				}

				return;
			}

			// walkways and doorways

			if (row - 1 >= 0) {
				BoardCell upCell = grid[row - 1][column];

				if (!upCell.isRoom()) {
					adjList.add(upCell);
				}
				else if (upCell.isDoorway() && upCell.getDoorDirection() == DoorDirection.DOWN) {
					adjList.add(upCell);
				}
			}

			// down
			if (row + 1 < numRows) {
				BoardCell downCell = grid[row + 1][column];

				if (!downCell.isRoom()) {
					adjList.add(downCell);
				}
				else if (downCell.isDoorway() && downCell.getDoorDirection() == DoorDirection.UP) {
					adjList.add(downCell);
				}
			}

			// left
			if (column - 1 >= 0) {
				BoardCell leftCell = grid[row][column - 1];

				if (!leftCell.isRoom()) {
					adjList.add(leftCell);
				}
				else if (leftCell.isDoorway() && leftCell.getDoorDirection() == DoorDirection.RIGHT) {
					adjList.add(leftCell);
				}
			}

			// right
			if (column + 1 < numCols) {
				BoardCell rightCell = grid[row][column + 1];

				if (!rightCell.isRoom()) {
					adjList.add(rightCell);
				}
				else if (rightCell.isDoorway() && rightCell.getDoorDirection() == DoorDirection.LEFT) {
					adjList.add(rightCell);
				}
			}
			
			if (isDoorway()) {
				BoardCell roomCell = null;

				if (doorDirection == DoorDirection.UP) {
					roomCell = grid[row - 1][column];
				}
				else if (doorDirection == DoorDirection.DOWN) {
					roomCell = grid[row + 1][column];
				}
				else if (doorDirection == DoorDirection.LEFT) {
					roomCell = grid[row][column - 1];
				}
				else if (doorDirection == DoorDirection.RIGHT) {
					roomCell = grid[row][column + 1];
				}

				if (roomCell != null) {
					Room destinationRoom = board.getRoom(roomCell);
					if (destinationRoom != null && destinationRoom.getCenterCell() != null) {
						adjList.add(destinationRoom.getCenterCell());
					}
				}
			}
			}		
	 
	 public void draw(Graphics g, int cellWidth, int cellHeight) {
			int x = column * cellWidth;
			int y = row * cellHeight;

			if (initial == 'W') {
				g.setColor(highlighted ? Color.CYAN : Color.YELLOW);
				g.fillRect(x, y, cellWidth, cellHeight);
				g.setColor(Color.BLACK);
				g.drawRect(x, y, cellWidth, cellHeight);
			} else if (initial == 'X') {
				g.setColor(Color.BLACK);
				g.fillRect(x, y, cellWidth, cellHeight);
			} else {
				g.setColor(highlighted ? Color.CYAN : Color.LIGHT_GRAY);
				g.fillRect(x, y, cellWidth, cellHeight);
			}

			if (isDoorway()) {
				g.setColor(Color.BLUE);

				if (doorDirection == DoorDirection.UP) {
					g.fillRect(x, y, cellWidth, 4);
				} else if (doorDirection == DoorDirection.DOWN) {
					g.fillRect(x, y + cellHeight - 4, cellWidth, 4);
				} else if (doorDirection == DoorDirection.LEFT) {
					g.fillRect(x, y, 4, cellHeight);
				} else if (doorDirection == DoorDirection.RIGHT) {
					g.fillRect(x + cellWidth - 4, y, 4, cellHeight);
				}
			}
		}
	
	
	

	//----------------------is_functions-----------------------

	public boolean isDoorway() {
		return doorDirection != DoorDirection.NONE;
	}

	public boolean isLabel() {
		return label;
	}

	public boolean isRoomCenter() {
		return roomCenter;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	
	//----------------- getters --------------------------
	
	public boolean getOccupied() {
		return isOccupied;
	}

	public boolean isHighlighted() {
		return highlighted;
	}
	
	public Set<BoardCell> getAdjList(){
		return adjList;

	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	public char getSecretPassage() {
		return secretPassage;
	}

	public char getInitial() {
		return initial;
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return column;
	}

}
