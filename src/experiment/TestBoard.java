package experiment;
//Franklin Rambo, William Drescher

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	final static int COLS = 4;
	final static int ROWS = 4;


	
	public TestBoard() {
	    grid = new TestBoardCell[ROWS][COLS];
	    // create board
	    for (int i = 0; i < ROWS; i++) {
	        for (int j = 0; j < COLS; j++) {
	            grid[i][j] = new TestBoardCell(i, j);
	        }
	    }
	    // create cells
	    
	    for (int i = 0; i < ROWS; i++) {
	        for (int j = 0; j < COLS; j++) {
	            grid[i][j].addAdjacencies(grid, ROWS, COLS);
	        }
	    }
	    // for each cell, create adjacencies
	}
	
	
	public void findAllTargets(TestBoardCell cell, int pathlen) {
		for(TestBoardCell adjCell : cell.getAdjList()) {
			if(visited.contains(adjCell) || adjCell.getOccupied()) {
				continue;
			}
			visited.add(adjCell);
			if(pathlen == 1 || adjCell.isRoom()) {
				targets.add(adjCell);
			}else {
				findAllTargets(adjCell, pathlen-1);
			}
			visited.remove(adjCell);
		}
		// recursive func that goes through and adds targets (basically copied from slides)
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		visited = new HashSet<>();
		targets = new HashSet<>();
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
		//set visited and targets sets, then start recursive func
	}
	
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
		// simple getter
	}
	
	public Set<TestBoardCell> getTargets(){
		return targets;
		// simple getter
	}
}