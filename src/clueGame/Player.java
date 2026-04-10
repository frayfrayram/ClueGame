package clueGame;

import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	private String name;
	private String color;
	private int row;
	private int col;
	private Set<Card> hand;
	
	public Player(String name, String color, int row, int col) {
		this.name = name;
		this.color = color;
		this.row = row;
		this.col = col;
		hand = new HashSet<>();
	}
	
	public void setHand(Card c) {
		hand.add(c);
	}
	
	
	//----------------------------getters---------------------
	public Set<Card> getHand() {
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
