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
import clueGame.Solution;

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
		Set<Card> testDeck = board.getDeck();
		assertEquals(21, testDeck.size());
	}
	
	@Test
	public void testDeck() {
		Set<Card> testDeck = board.getDeck();
		assertTrue(testDeck.contains(board.getCard("Wiimote")));
		assertTrue(testDeck.contains(board.getCard("Franklin")));
		assertTrue(testDeck.contains(board.getCard("Franklin Room")));
		// one card of each type
	}
	
	@Test
	public void testSolution() {
		assertTrue(board.checkAnswer(board.getCard("Kevin"),
								   	board.getCard("Wiimote"),
									board.getCard("Bar")));
	}
	
	@Test
	public void testDeal() {
		board.deal();
		Set<Card> testDeck = board.getDeck();
		assertEquals(0, testDeck.size());
		assertEquals(4, board.getPlayer("Franklin").getHand().size());
		assertEquals(4, board.getPlayer("Kevin").getHand().size());
		assertEquals(3, board.getPlayer("Juliet").getHand().size());
	}


}
