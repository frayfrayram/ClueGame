package clueGame;

import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	
	private char initial;
	private boolean label;
	private boolean roomCenter;
	private char secretPassage;
	private DoorDirection doorDirection;
	private Set<BoardCell> adjList;
	private boolean isRoom, isOccupied ;



	public BoardCell(String token) {
		// TODO Auto-generated constructor stub
		initial = token.charAt(0);
		doorDirection = DoorDirection.NONE;
		label = false;
		roomCenter = false;
		secretPassage = ' ';
		
		if (token.length() > 1) {
			char second = token.charAt(1);

			if (second == '<') {
				doorDirection = DoorDirection.LEFT;
			} else if (second == '>') {
				doorDirection = DoorDirection.RIGHT;
			} else if (second == '^') {
				doorDirection = DoorDirection.UP;
			} else if (second == 'v') {
				doorDirection = DoorDirection.DOWN;
			} else if (second == '#') {
				label = true;
			} else if (second == '*') {
				roomCenter = true;
			} else {
				secretPassage = second;
			}
		}
	}

	public Set<BoardCell> getAdjList(){
		return adjList;
		// just returns adjList, simple getter
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return doorDirection;
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return doorDirection != DoorDirection.NONE;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return label;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 0;
	}

	public char getInitial() {
		// TODO Auto-generated method stub
		return initial;
	}
	
	public boolean isRoom() {
		return isRoom;
	}

}
