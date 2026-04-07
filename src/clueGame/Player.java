package clueGame;

public abstract class Player {
	private String name;
	private String color;
	private int row;
	private int col;
	
	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
		
	}
}
