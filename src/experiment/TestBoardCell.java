package experiment;
// Franklin Rambo, William Drescher

import java.util.HashSet;
import java.util.Set;


// Preliminary stub class just to allow for tests to pass
public class TestBoardCell {
	private int row, column;
	private boolean isRoom, isOccupied ;
	private Set<TestBoardCell> adjList;
	
	public TestBoardCell(int row, int column) {
		this.row = row;
		this.column = column;
		this.adjList = new HashSet<>();
	}
	
	 private boolean checkValid(int r, int c, int numRows, int numCols) {
	        return r >= 0 && r < numRows && c >= 0 && c < numCols;
	    }
	
	
	public void addAdjacencies(TestBoardCell[][] grid, int numRows, int numCols) {
        adjList.clear();

        // up
        if (checkValid(row - 1, column, numRows, numCols)) {
            adjList.add(grid[row - 1][column]);
        }

        // down
        if (checkValid(row + 1, column, numRows, numCols)) {
            adjList.add(grid[row + 1][column]);
        }

        // left
        if (checkValid(row, column - 1, numRows, numCols)) {
            adjList.add(grid[row][column - 1]);
        }

        // right
        if (checkValid(row, column + 1, numRows, numCols)) {
            adjList.add(grid[row][column + 1]);
        }
    }
		
	// check if top or bottom is valid, then add
	
	public Set<TestBoardCell> getAdjList(){
		return adjList;
		// just returns adjList, simple getter
	}
	
	public void setRoom(boolean room) {
		isRoom = room;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
}