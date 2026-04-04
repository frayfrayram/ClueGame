package tests;
import java.util.Set;

//Franklin Rambo, William Drescher

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import experiment.*;


class BoardTestsExp {
	private TestBoard board1;

	@BeforeEach
	 void setUp() {
	      board1 = new TestBoard();
	  	}

	
// test to see if adjacency functions are set up
	@Test
	void testAdjacency() {
		TestBoardCell cell = board1.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		Assert.assertTrue(testList.contains(board1.getCell(1,0)));
		Assert.assertTrue(testList.contains(board1.getCell(0,1)));
		Assert.assertEquals(2,  testList.size());
	}
	
	//oops
	
	//uugh
// test to see if targets functions are set up
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board1.getCell(0, 0);
		board1.calcTargets(cell,3);
		Set<TestBoardCell> targets = board1.getTargets();
		Assert.assertEquals(6,  targets.size());
		Assert.assertTrue(targets.contains(board1.getCell(3,0)));
		Assert.assertTrue(targets.contains(board1.getCell(2,1)));
		Assert.assertTrue(targets.contains(board1.getCell(0,1)));
		Assert.assertTrue(targets.contains(board1.getCell(1,2)));
		Assert.assertTrue(targets.contains(board1.getCell(0,3)));
		Assert.assertTrue(targets.contains(board1.getCell(1,0)));

	}
	
// tests targets on mixed board (unimplemented)
	@Test
	public void testTargetsMixed() {
		board1.getCell(0, 2).setOccupied(true);
		board1.getCell(1, 2).setRoom(true);
		TestBoardCell cell = board1.getCell(0,3);
		board1.calcTargets(cell,3);
		Set<TestBoardCell> targets = board1.getTargets();
		Assert.assertEquals(3,  targets.size());      
		Assert.assertTrue(targets.contains(board1.getCell(1,2)));
		Assert.assertTrue(targets.contains(board1.getCell(2,2)));
		Assert.assertTrue(targets.contains(board1.getCell(3,3)));

	}

}
