package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	Board board = Board.getInstance();
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}

	public Solution createSuggestion(Card room) {
	    ArrayList<Card> possiblePeople = new ArrayList<>();
	    ArrayList<Card> possibleWeapons = new ArrayList<>();

	    

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
	    Set<BoardCell> targets = board.getTargets();

	    // Prefer an unseen room
	    for (BoardCell t : targets) {
	        if (t.isRoom()) {
	            Room room = board.getRoom(t);
	            Card roomCard = board.getCard(room.getName());
	            if (!seen.contains(roomCard)) {
	                return t;
	            }
	        }
	    }

	    // Otherwise pick a random target
	    int item = new Random().nextInt(targets.size());
	    int i = 0;
	    for (BoardCell cell : targets) {
	        if (i == item) {
	            return cell;
	        }
	        i++;
	    }

	    return null;
	}
}

