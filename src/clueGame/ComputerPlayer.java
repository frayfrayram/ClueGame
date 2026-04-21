package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}

	public Solution createSuggestion(Card room) {
	    ArrayList<Card> possiblePeople = new ArrayList<>();
	    ArrayList<Card> possibleWeapons = new ArrayList<>();

	    Board board = Board.getInstance();

	    for (Player p : board.getPlayerMap().values()) {
	        Card personCard = board.getCard(p.getName());
	       
	        //if seen doesn't contain person AND hand doesnt contain person, add to possible ppl
	        if (!seen.contains(personCard) && !getHand().contains(personCard)) {
	            possiblePeople.add(personCard);
	        }
	    }

	    for (Card weaponCard : board.getWeaponMap().values()) {
	    	//same logic as was for person
	        if (!seen.contains(weaponCard) && !getHand().contains(weaponCard)) {
	            possibleWeapons.add(weaponCard);
	        }
	    }

	    Random rand = new Random();
	    Card chosenPerson = possiblePeople.get(rand.nextInt(possiblePeople.size()));
	    Card chosenWeapon = possibleWeapons.get(rand.nextInt(possibleWeapons.size()));

	    return new Solution(chosenPerson, chosenWeapon, room);
	}

	public BoardCell selectTarget() {
		Board board = Board.getInstance();
		Map<Character, Room> roomMap = board.getRoomMapChar();
		//currently no function for roll, so set value at 2
		

		// NEED TO CHANGE THIS WITH A GETROLL() 
		board.calcTargets(board.getCell(getRow(), getCol()), 1);
		Set<BoardCell> targets = board.getTargets();
		
		for(BoardCell t : targets) {
			if(!seen.contains(Board.getInstance().getCard(roomMap.get(t.getInitial()).getName()))) {
				return t;
			}		
		}
		
		int size = targets.size();
		int item = new Random().nextInt(size); 
		int i = 0;
		for(BoardCell obj : targets)
		{
		    if (i == item)
		        return obj;
		    i++;
		}
		
		return null;
	}
}

