package boardlayer;

import chesslayer.ChessPosition;
import chesslayer.Color;

public abstract class Piece {
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		
	}
	
	
	protected Board getBoard() {
		return board;
	}
	
	
	
	public Position getPosition() {
		return position;
	}
	
	public abstract boolean[][] possibleMoves();
	
	public boolean possibleMove(Position position) {
		
		return possibleMoves()[position.getCollumn()][position.getRow()];
		
	}
	public boolean isThereAnyPossibleMoves() {
		
		boolean[][]pMoves = possibleMoves();
		
		
		for (int i=0;i<board.getColumns();i++) {
			for(int j=0;j<board.getRows();j++) {
				if(pMoves[i][j]==true) {
					
					return true;
				}
				
			}
		}
		return false;
		
	}

	public abstract Color getColor();
	
}
