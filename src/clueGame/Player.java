package clueGame;

public abstract class Player {
	private String name;
	private String color;
	private int row;
	private int col;
	private Card[] hand;
	
	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
	}
	
	public void setHand(Card p, Card w, Card r) {
		hand[0] = p;
		hand[1] = w;
		hand[2] = r;
	}
	
	
	//----------------------------getters---------------------
	public Card[] getHand() {
		return hand;
	}
	public String getName() {
		return name;
	}
	public String getColor() {
		return color;
	}
	public int getRow(){
		return row;
	}
	public int getCol() {
		return col;
	}
}
