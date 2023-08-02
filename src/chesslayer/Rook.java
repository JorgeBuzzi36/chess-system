package chesslayer;

import java.util.Arrays;

import boardlayer.Board;
import boardlayer.Position;

public class Rook extends ChessPiece{

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
    public boolean[][] possibleMoves() {
        boolean[][] pMov = new boolean[getBoard().getColumns()][getBoard().getRows()];

        // Directions: up, down, left, right
        int[][] directions = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };

        for (int[] direction : directions) {
        	// first loop will be a vector with the directions direction[0]=0 direction[1]=1, second direction[0]=0 direction[1]=-1 and so on...
        	
            int dCollumn = direction[0];
            int dRow = direction[1];
            int collumn = getPosition().getCollumn() + dCollumn;
            int row = getPosition().getRow() + dRow;
            
            //walks to a giving direction as long the position exists and there is no piece
            
            while (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {
                pMov[collumn][row] = true;
                collumn += dCollumn;
                row += dRow;
            }
            // when stops walking, check if the next positions doesn't exists or if its a piece, checks if its a opponent piece if the later is the case
            
            if (getBoard().positionExists(collumn, row) && isThereOpponentPiece(new Position(collumn, row))) {
                pMov[collumn][row] = true;
            }
        }
        boolean[][] onlyLegalMoves = this.checkLegalMoves();

		for (int i = 0; i < getBoard().getColumns(); i++) {
			for (int j = 0; j < getBoard().getRows(); j++) {
				if (pMov[i][j] && onlyLegalMoves[i][j]) {
					pMov[i][j] = true;
				} else {
					pMov[i][j] = false;
				}

			}
		}
		return pMov;
	}
}
