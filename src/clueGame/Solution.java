package clueGame;

import java.util.HashSet;
import java.util.Set;

public class Solution {
	private Card person;
	private Card weapon;
	private Card room;
	private Set<Card> solution;
	
	public Solution(Card p, Card w, Card r) {		
		this.person = p;
		this.weapon = w;
		this.room = r;
		solution = new HashSet<>();
	}
	
	public Set<Card> getAnswer() {

		solution.add(person);
		solution.add(weapon);
		solution.add(room);
		return solution;
	}

	public Card getRoom() {
		return room;
	}
	
	public Card getWeapon() {
		return weapon;
	}
	
	public Card getPerson() {
		return person;
	}
}
