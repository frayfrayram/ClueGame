package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Room {
	
	private String name;
	private BoardCell labelCell;
	private BoardCell centerCell;
	private char secretPassage;
	private Set<BoardCell> doorCells;

	public Room(String name) {
		this.name = name;
		this.secretPassage = ' ';
		this.doorCells = new HashSet<>();
	}

	public String getName() {
		return name;	
	}

	public BoardCell getLabelCell() {
		return labelCell;
	}

	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public char getSecretPassage() {
		return secretPassage;
	}

	public Set<BoardCell> getDoorCells() {
		return doorCells;
	}

	public void setLabelCell(BoardCell labelCell) {
		this.labelCell = labelCell;
	}

	public void setCenterCell(BoardCell centerCell) {
		this.centerCell = centerCell;
	}
	
	public void setSecretPassage(char secretPassage) {
		this.secretPassage = secretPassage;
	}
	
	public void addDoorCell(BoardCell doorCell) {
		doorCells.add(doorCell);
	}
}