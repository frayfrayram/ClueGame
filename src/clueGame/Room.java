package clueGame;

public class Room {
	
	private String name;
	private BoardCell labelCell;
	private BoardCell centerCell;

	public Room(String name) {
		this.name = name;
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;	
	}

	public BoardCell getLabelCell() {
		// TODO Auto-generated method stub
		return labelCell;
	}

	public BoardCell getCenterCell() {
		// TODO Auto-generated method stub
		return centerCell;
	}


}
