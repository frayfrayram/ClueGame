package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Room;
import clueGame.Solution;

public class ComputerAITest {

	private Board board;
	private ComputerPlayer cpu;

	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		board.initialize();

		cpu = (ComputerPlayer) board.getPlayer("Avi");

		cpu.getHand().clear();
		cpu.getSeen().clear();
	}

	@Test
	public void computerSuggestionTest() {
		Card currentRoom = board.getCard(board.getRoom(board.getCell(3, 6)).getName());

		// Room must match the room passed in
		Solution suggestion = cpu.createSuggestion(currentRoom);
		assertEquals(currentRoom, suggestion.getRoom());

		// Only one unseen weapon and one unseen person
		cpu.getSeen().clear();

		Card onlyWeapon = board.getCard("Wiimote");
		Card onlyPerson = board.getCard("Kevin");

		for (Card weapon : board.getWeaponMap().values()) {
			if (!weapon.equals(onlyWeapon)) {
				cpu.updateSeen(weapon);
			}
		}

		for (Player p : board.getPlayerMap().values()) {
			Card personCard = board.getCard(p.getName());
			if (!personCard.equals(onlyPerson)) {
				cpu.updateSeen(personCard);
			}
		}

		suggestion = cpu.createSuggestion(currentRoom);
		assertEquals(currentRoom, suggestion.getRoom());
		assertEquals(onlyWeapon, suggestion.getWeapon());
		assertEquals(onlyPerson, suggestion.getPerson());

		// Multiple unseen weapons -> random among unseen weapons
		cpu.getSeen().clear();

		Card weapon1 = board.getCard("Wiimote");
		Card weapon2 = board.getCard("Angry Text");
		Card fixedPerson = board.getCard("Kevin");

		for (Card weapon : board.getWeaponMap().values()) {
			if (!weapon.equals(weapon1) && !weapon.equals(weapon2)) {
				cpu.updateSeen(weapon);
			}
		}

		for (Player p : board.getPlayerMap().values()) {
			Card personCard = board.getCard(p.getName());
			if (!personCard.equals(fixedPerson)) {
				cpu.updateSeen(personCard);
			}
		}

		Set<Card> chosenWeapons = new HashSet<>();
		for (int i = 0; i < 100; i++) {
			suggestion = cpu.createSuggestion(currentRoom);
			assertEquals(currentRoom, suggestion.getRoom());
			assertEquals(fixedPerson, suggestion.getPerson());
			assertTrue(suggestion.getWeapon().equals(weapon1) || suggestion.getWeapon().equals(weapon2));
			chosenWeapons.add(suggestion.getWeapon());
		}
		assertEquals(2, chosenWeapons.size());

		// Multiple unseen persons -> random among unseen persons
		cpu.getSeen().clear();

		Card person1 = board.getCard("Kevin");
		Card person2 = board.getCard("Franklin");
		Card fixedWeapon = board.getCard("Wiimote");

		for (Player p : board.getPlayerMap().values()) {
			Card personCard = board.getCard(p.getName());
			if (!personCard.equals(person1) && !personCard.equals(person2)) {
				cpu.updateSeen(personCard);
			}
		}

		for (Card weapon : board.getWeaponMap().values()) {
			if (!weapon.equals(fixedWeapon)) {
				cpu.updateSeen(weapon);
			}
		}

		Set<Card> chosenPeople = new HashSet<>();
		for (int i = 0; i < 100; i++) {
			suggestion = cpu.createSuggestion(currentRoom);
			assertEquals(currentRoom, suggestion.getRoom());
			assertEquals(fixedWeapon, suggestion.getWeapon());
			assertTrue(suggestion.getPerson().equals(person1) || suggestion.getPerson().equals(person2));
			chosenPeople.add(suggestion.getPerson());
		}
		assertEquals(2, chosenPeople.size());
	}

	@Test
	public void computerTargetTest() {
		BoardCell selected;
		Set<BoardCell> observed = new HashSet<>();

		// 1. If room in target list has not been seen, select it
		board.calcTargets(board.getCell(2, 2), 2);
		selected = cpu.selectTarget();

		assertTrue(board.getTargets().contains(selected));
		assertTrue(selected.isRoom());

		Room room = board.getRoom(selected);
		Card roomCard = board.getCard(room.getName());
		assertFalse(cpu.getSeen().contains(roomCard));

		// 2. If no rooms in target list, select randomly
		board.calcTargets(board.getCell(7, 1), 1);
		Set<BoardCell> noRoomTargets = board.getTargets();
		observed.clear();

		for (int i = 0; i < 100; i++) {
			selected = cpu.selectTarget();
			assertTrue(noRoomTargets.contains(selected));
			assertFalse(selected.isRoom());
			observed.add(selected);
		}
		assertTrue(observed.size() > 1);

		// 3. If room in target list has been seen, choose randomly from all targets
		board.calcTargets(board.getCell(2, 2), 2);
		Set<BoardCell> targets = board.getTargets();

		cpu.getSeen().clear();
		for (BoardCell cell : targets) {
			if (cell.isRoom()) {
				Room seenRoom = board.getRoom(cell);
				cpu.updateSeen(board.getCard(seenRoom.getName()));
			}
		}

		observed.clear();
		for (int i = 0; i < 100; i++) {
			selected = cpu.selectTarget();
			assertTrue(targets.contains(selected));
			observed.add(selected);
		}
		assertTrue(observed.size() > 1);
	}
}