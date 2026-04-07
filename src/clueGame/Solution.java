package clueGame;

public class Solution {
	private Card PERSON;
	private Card WEAPON;
	private Card ROOM;
	private Card SOLUTION[];
	
	public Solution(Card p, Card w, Card r) {
		this.PERSON = p;
		this.WEAPON = w;
		this.ROOM = r;
		
		SOLUTION[0] = PERSON;
		SOLUTION[1] = WEAPON;
		SOLUTION[2] = ROOM;
	}
	
	public Card[] getAnswer() {
		return SOLUTION;
	}
}
