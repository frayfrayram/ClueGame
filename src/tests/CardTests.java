package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;

public class CardTests {
	private static Board board;
	
	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
	}

	@Test
	public void testDeckSize(){
		Set<Card> testList = board.getDeck();
		assertEquals(21, testList.size());
	}
	
	@Test
	public void testDeck() {
		Set<Card> testDeck = board.getDeck();
		assertTrue(testDeck.contains(board.getCard("Wiimote")));
		assertTrue(testDeck.contains(board.getCard("Franklin")));
		assertTrue(testDeck.contains(board.getCard("Franklin Room")));
		// one card of each type
	}
	
	
}
