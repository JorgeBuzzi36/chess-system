package chesslayer;

import boardlayer.Board;
import boardlayer.Position;

public class King extends ChessPiece {
	private ChessMatch chessMatch;

	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] pMov = new boolean[getBoard().getColumns()][getBoard().getRows()];

		// Directions: up, down, left, right and Diagonals: up-right, up-left,
		// down-right, down-left
		int[][] directions = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 }, { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 } };

		for (int[] direction : directions) {

			int dCollumn = direction[0];
			int dRow = direction[1];
			int collumn = getPosition().getCollumn() + dCollumn;
			int row = getPosition().getRow() + dRow;

			// Go a single time towards the direction

			if (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {
				pMov[collumn][row] = true;
				
			}

			// Checks if there is an opponent

			if (getBoard().positionExists(collumn, row) && isThereOpponentPiece(new Position(collumn, row))) {
				pMov[collumn][row] = true;
			}
		}
		//castle logic
		
		
		if (this.getMoveCount() == 0 && this.conditionLongCastle()) {
			pMov[2][this.position.getRow()] =true;
			chessMatch.setCastleAvaliable(true);
			
		}
		
		if (this.getMoveCount() == 0 && this.conditionShortCastle()) {
			pMov[6][this.position.getRow()] =true;
			chessMatch.setCastleAvaliable(true);
			
		}

		return pMov;
	}
	
	private boolean conditionLongCastle() {
		ChessPiece longRook = (ChessPiece) getBoard().piece(0, this.position.getRow());
		return longRook!= null && longRook.getMoveCount()==0 && getBoard().piece(1, this.position.getRow()) == null
				&& getBoard().piece(2, this.position.getRow()) == null
				&& getBoard().piece(3, this.position.getRow()) == null;
	}
	
	private boolean conditionShortCastle() {
		ChessPiece shortRook = (ChessPiece) getBoard().piece(7, this.position.getRow());
		return shortRook != null && shortRook.getMoveCount()==0 &&getBoard().piece(5, this.position.getRow()) == null
				&& getBoard().piece(6, this.position.getRow()) == null;
				
	}
	private boolean[][] scanForThreats(){
		boolean[][] threats = new boolean[getBoard().getColumns()][getBoard().getRows()];
		
		
		return threats;
}
	
	public boolean canIBeHelped(ChessPiece p) {
		
		
		return true;
	}
	
	public boolean[][] legalMoves(boolean [][] possibleMoves){
		
		return possibleMoves;
	}

	public boolean[][] helpMe(boolean[][] possibleMoves, int check) {
		// Assumir que um rei sendo atacado por 2 peças ao mesmo tempo nao pode ser defendido por outra peça e precisa se mover
		
		return possibleMoves;
		
	}
	
	
}
