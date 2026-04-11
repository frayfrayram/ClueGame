package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Player;

class ComputerAITest {
	private static Board board;
	
	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");
		// Initialize will load BOTH config files
		board.initialize();
		board.setSolution("Kevin", "Wiimote", "Bar");
	}
	
	
	@Test
	void selectTargets() {
		Player p = board.getPlayer("Avi");
		assertEquals(board.getCell(2, 1), p.selectTarget());
	}

}
