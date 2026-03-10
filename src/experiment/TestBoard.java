package experiment;
//Franklin Rambo, William Drescher

import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets ;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;


	
	public TestBoard() {
	    grid = new TestBoardCell[ROWS][COLS];

	    for (int i = 0; i < ROWS; i++) {
	        for (int j = 0; j < COLS; j++) {
	            grid[i][j] = new TestBoardCell(i, j);
	        }
	    }

	    for (int i = 0; i < ROWS; i++) {
	        for (int j = 0; j < COLS; j++) {
	            grid[i][j].addAdjacencies(grid, ROWS, COLS);
	        }
	    }
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	}
	
	public Set<TestBoardCell> getTargets(){
		return null;
		
	}
}
