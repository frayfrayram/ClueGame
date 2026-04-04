package tests;

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

public class BoardAdjTargetTest {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	
	@BeforeEach
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesRooms()
	{
		// we want to test a couple of different rooms.
		// First, the study that only has a single door but a secret room
		Set<BoardCell> testList = board.getCell(4,0).getAdjList();
		assertEquals(2, testList.size());
		assertTrue(testList.contains(board.getCell(5, 2)));
		assertTrue(testList.contains(board.getCell(20, 13)));

		
		// now test the ballroom (note not marked since multiple test here)
		testList = board.getCell(13, 1).getAdjList();
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCell(9, 2)));
	}

	
	// Ensure door locations include their rooms and also additional walkways
	// These cells are LIGHT ORANGE on the planning spreadsheet
	@Test
	public void testAdjacencyDoor()
	{
		Set<BoardCell> testList = board.getCell(3, 3).getAdjList();
		assertEquals(4, testList.size());
		assertTrue(testList.contains(board.getCell(3, 6)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(4, 3)));
	}
	
	// Test a variety of walkway scenarios
	// These tests are Dark Orange on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on bottom edge of board, just one walkway piece
		Set<BoardCell> testList = board.getCell(18, 1).getAdjList();
		assertEquals(3, testList.size());
		assertTrue(testList.contains(board.getCell(17, 1)));
		assertTrue(testList.contains(board.getCell(18, 0)));
		assertTrue(testList.contains(board.getCell(19, 1)));
	
	}
	
	
	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsInDiningRoom() {
		// test a roll of 1
		board.calcTargets(board.getCell(3, 6), 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3, 8)));
		assertTrue(targets.contains(board.getCell(3, 3)));	
		
		// test a roll of 2
		board.calcTargets(board.getCell(3, 6), 2);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(2, 3)));
		assertTrue(targets.contains(board.getCell(2, 8)));	


	}
	

	// Tests out of room center, 1, 3 and 4
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsAtDoor() {
		// test a roll of 1, at door
		board.calcTargets(board.getCell(5, 2), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCell(4, 0)));
		assertTrue(targets.contains(board.getCell(3, 2)));	
	}

	@Test
	public void testTargetsInWalkway1() {
		// test a roll of 1
		board.calcTargets(board.getCell(24, 7), 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(21, 7)));
	}

	@Test
	public void testTargetsInWalkway2() {
		// test a roll of 1
		board.calcTargets(board.getCell(15, 0), 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(15, 2)));
		assertTrue(targets.contains(board.getCell(17, 0)));	
		assertTrue(targets.contains(board.getCell(16, 1)));	
	}

	@Test
	// test to make sure occupied locations do not cause problems
	public void testTargetsOccupied() {
		// test a roll of 4 blocked 2 down
		board.getCell(17, 0).setOccupied(true);
		board.calcTargets(board.getCell(15, 0), 2);
		Set<BoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(15, 2)));
		assertTrue(targets.contains(board.getCell(16, 1)));	
		assertFalse( targets.contains( board.getCell(17, 0))) ;

	}
}
