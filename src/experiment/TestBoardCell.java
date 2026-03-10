package experiment;
// Franklin Rambo, William Drescher

import java.util.HashSet;
import java.util.Set;


// Preliminary stub class just to allow for tests to pass
public class TestBoardCell {
	private int row;
	private int column;
	private Set<TestBoardCell> adjList = new HashSet<>();
	
	public TestBoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void addAdjacency( TestBoardCell cell ) {
		int testRow = row;
		int testColumn = column;
		if(((testRow - 1) >= 0) && ((testColumn - 1) >= 0)) {
			TestBoardCell cell1 = new TestBoardCell(testRow+1, testColumn+1);
			TestBoardCell cell2 = new TestBoardCell(testRow+1, testColumn-1);
			TestBoardCell cell3 = new TestBoardCell(testRow-1, testColumn+1);
			TestBoardCell cell4 = new TestBoardCell(testRow-1, testColumn-1);
			adjList.add(cell1);
			adjList.add(cell2);
			adjList.add(cell3);
			adjList.add(cell4);
		}
		// upper bounds not implemented, don't have boardSize variable yet
	}
	
	public Set<TestBoardCell> getAdjList(){
		return adjList;
		// just returns adjList, simple getter
		
	}
	
	public void setRoom(boolean room) {
		
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public void setOccupied(boolean occupied) {
		
	}
	
	boolean getOccupied() {
		return false;
	}
}
