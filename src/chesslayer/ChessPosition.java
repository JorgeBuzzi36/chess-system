package chesslayer;

import boardlayer.Position;

public class ChessPosition {
	
	private char column;
	private int row;
	
	
	public ChessPosition() {
		
	}
	
	public ChessPosition(char column, int row) {
		this.column = column;
		this.row = row;
	}
	
	
	
	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public Position toPosition() {
		
		return new Position(((int)this.column-97),row-1);
	}
	
	public ChessPosition fromPosition(Position position) {
		return new ChessPosition((char)(position.getCollumn()+97),position.getRow()+1);
	}
	public ChessPosition fromPosition(int collumn ,int row) {
		return new ChessPosition((char)(collumn+97),row+1);
	}
	@Override
	public String toString() {
		return "ChessPosition [column=" + column + ", row=" + row + "]";
	}

	

	
}
