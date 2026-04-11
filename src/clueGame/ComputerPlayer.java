package clueGame;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}

	public Solution createSuggestion(Card person, Card weapon, Card room) {
		return new Solution(person, weapon, room);
	}

	public BoardCell selectTarget() {
		Map<Character, Room> roomMap = Board.getInstance().getRoomMapChar();
		Set<BoardCell> targets = Board.getInstance().getTargets();
		
		for(BoardCell t : targets) {
			if(!seen.contains(Board.getInstance().getCard(roomMap.get(t.getInitial()).getName()))) {
				return t;
			}		
		}
		
		int size = targets.size();
		int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
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

