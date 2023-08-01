package chesslayer;

import java.util.Arrays;

import boardlayer.Board;
import boardlayer.Position;

public class Queen extends ChessPiece {

	public Queen(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Q";
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

			// walks to a giving direction as long the position exists and there is no piece

			while (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {
				pMov[collumn][row] = true;
				collumn += dCollumn;
				row += dRow;
			}

			// when stops walking, check if the next positions doesn't exists or if its a
			// piece, checks if its a opponent piece if the later is the case

			if (getBoard().positionExists(collumn, row) && isThereOpponentPiece(new Position(collumn, row))) {
				pMov[collumn][row] = true;
			}
		}
		boolean[][] onlyLegalMoves = checkLegalMoves();

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

	private boolean[][] checkLegalMoves() {

		boolean[][] onlyLegalMoves = new boolean[getBoard().getColumns()][getBoard().getRows()];
		boolean[][] everyMoveIsLegal = new boolean[getBoard().getColumns()][getBoard().getRows()];
		for (boolean[] b : everyMoveIsLegal) {
			Arrays.fill(b, true);
		}
		int[][] directions = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 }, { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 } };

		for (int[] direction : directions) {

			int dCollumn = direction[0];
			int dRow = direction[1];
			int collumn = getPosition().getCollumn() + dCollumn;
			int row = getPosition().getRow() + dRow;
			
			//Checks if the piece is directly shielding another piece
			while (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {

				collumn += dCollumn;
				row += dRow;
			}
			//Checks if the piece being shielded is the King
			if (getBoard().positionExists(collumn, row) && isThereMyKing(new Position(collumn, row))) {
				
				dCollumn *= -1;
				dRow *= -1;
				collumn += dCollumn;
				row += dRow;
			//Checking all the legal moves that will keep the king save 	
				while (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {

					onlyLegalMoves[collumn][row] = true;
					collumn += dCollumn;
					row += dRow;
				}
				collumn = getPosition().getCollumn() + dCollumn;
				row = getPosition().getRow() + dRow;
				
				if (getBoard().positionExists(collumn, row) && isThereOpponentPiece(new Position(collumn, row))) {
					onlyLegalMoves[collumn][row] = true;
					//Checa se ha tretas
					if(checkThreats(dCollumn*dRow,getBoard().piece(new Position(collumn,row)).toString())) {
					return onlyLegalMoves;
					}
				} else {
					return everyMoveIsLegal;
				}
			}

		}
		return everyMoveIsLegal;
	}

	

	private boolean checkThreats(int direction, String string) {
		// Returns true if its a Queen or a Bishop on a diagonal
		if(direction!=0 && (string.equals("B")||string.equals("Q"))) {
			return true;
		}
		// Returns true if its a Rook or a Queen on a straight line 
		if(direction==0 && (string.equals("R")||string.equals("Q"))) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean isThereMyKing(Position position) {

		return getBoard().piece(position).getColor().equals(this.getColor())
				&& getBoard().piece(position).toString().equals("K");
	}
}