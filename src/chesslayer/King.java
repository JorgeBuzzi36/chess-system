package chesslayer;

import boardlayer.Board;
import boardlayer.Position;

public class King extends ChessPiece{
	private ChessMatch chessMatch;
	public King(Board board, Color color,ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch=chessMatch;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
        boolean[][] pMov = new boolean[getBoard().getColumns()][getBoard().getRows()];

        // Directions: up, down, left, right and Diagonals: up-right, up-left, down-right, down-left
        int[][] directions = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 },{ 1, 1 }, { -1, 1 }, { 1, -1}, { -1, -1 }  };

        for (int[] direction : directions) {
        	
        	
            int dCollumn = direction[0];
            int dRow = direction[1];
            int collumn = getPosition().getCollumn() + dCollumn;
            int row = getPosition().getRow() + dRow;
            
            //Go a single time towards the direction
            
            if (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {
                pMov[collumn][row] = true;
                collumn += dCollumn;
                row += dRow;
            }

            // Checks if the next positions doesn't exists or if its a piece, checks if its a opponent piece if the later is the case
            
            if (getBoard().positionExists(collumn, row) && isThereOpponentPiece(new Position(collumn, row))) {
                pMov[collumn][row] = true;
            }
        }

        return pMov;
    }
	
}
