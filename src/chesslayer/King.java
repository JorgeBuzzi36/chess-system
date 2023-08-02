package chesslayer;

import boardlayer.Board;
import boardlayer.Position;

public class King extends ChessPiece {
	private ChessMatch chessMatch;
	private boolean[][] aSquares = new boolean[getBoard().getColumns()][getBoard().getRows()];

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
		if (chessMatch.getCurrentPlayer() == this.getColor()) {
			scanForAttackedSquares();
		}
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
				if(!isThatPieceBeingProtected(collumn,row)) {
					pMov[collumn][row] = true;
				}
			}
		}
		// castle logic

		if (this.getMoveCount() == 0 && this.conditionLongCastle()) {
			pMov[2][this.position.getRow()] = true;
			chessMatch.setCastleAvaliable(true);

		}

		if (this.getMoveCount() == 0 && this.conditionShortCastle()) {
			pMov[6][this.position.getRow()] = true;
			chessMatch.setCastleAvaliable(true);

		}

		pMov = filterLegalMoves(pMov);
		return pMov;
	}

	private boolean isThatPieceBeingProtected(int collumn,int row) {
		
		ChessPiece p =(ChessPiece) getBoard().piece(collumn,row);
		return p.amIBeingProtected(p);
	}

	private boolean conditionLongCastle() {
		ChessPiece longRook = (ChessPiece) getBoard().piece(0, this.position.getRow());
		return longRook != null && longRook.getMoveCount() == 0 && getBoard().piece(1, this.position.getRow()) == null
				&& getBoard().piece(2, this.position.getRow()) == null
				&& getBoard().piece(3, this.position.getRow()) == null;
	}

	private boolean conditionShortCastle() {
		ChessPiece shortRook = (ChessPiece) getBoard().piece(7, this.position.getRow());
		return shortRook != null && shortRook.getMoveCount() == 0 && getBoard().piece(5, this.position.getRow()) == null
				&& getBoard().piece(6, this.position.getRow()) == null;

	}

	private void mergeAttackedSquares(boolean[][] opponentPMov) {

		for (int i = 0; i < getBoard().getColumns(); i++) {
			for (int j = 0; j < getBoard().getRows(); j++) {
				if (opponentPMov[i][j]) {
					this.aSquares[i][j] = true;

				}

			}
		}
	}

	private void scanForAttackedSquares() {
		for (int i = 0; i < getBoard().getColumns(); i++) {
			for (int j = 0; j < getBoard().getRows(); j++) {
				if (getBoard().piece(i, j) != null && getBoard().piece(i, j).getColor() != this.getColor()
						&& getBoard().piece(i, j).toString() != "K") {
					mergeAttackedSquares(getBoard().piece(i, j).possibleMoves());

				}
			}
		}
	}

	public boolean[][] filterLegalMoves(boolean[][] pMov) {
		for (int i = 0; i < getBoard().getColumns(); i++) {
			for (int j = 0; j < getBoard().getRows(); j++) {
				if (this.aSquares[i][j]) {
					pMov[i][j] = false;
				}

			}
		}
		return pMov;
	}

	private boolean[][] whereIsTheAttackCommingFrom() {

		boolean[][] attackRoute = new boolean[getBoard().getColumns()][getBoard().getRows()];

		// Directions: up, down, left, right and Diagonals: up-right, up-left,
		// down-right, down-left
		int[][] directions = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 }, { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 } };

		for (int[] direction : directions) {

			int dCollumn = direction[0];
			int dRow = direction[1];
			int collumn = getPosition().getCollumn() + dCollumn;
			int row = getPosition().getRow() + dRow;

			// Searching for a piece on a direction
			while (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {
				collumn += dCollumn;
				row += dRow;
			}
			// Sees if the piece is from the opponent

			if (getBoard().positionExists(collumn, row) && isThereOpponentPiece(new Position(collumn, row))) {
				if (canMoveInThisDirection(dCollumn * dRow, getBoard().piece(new Position(collumn, row)).toString())) {
					attackRoute[collumn][row] = true;
					dCollumn *= -1;
					dRow *= -1;
					collumn += dCollumn;
					row += dRow;
					while (getBoard().positionExists(collumn, row)
							&& !getBoard().isTherePiece(new Position(collumn, row))) {
						attackRoute[collumn][row] = true;
						collumn += dCollumn;
						row += dRow;
					}

				}
			}
		}
		int[][] horseDirections = { { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }, { 2, 1 }, { 2, -1 }, { -2, -1 }, { -2, 1 } };

		for (int[] direction :horseDirections) {

			int dCollumn = direction[0];
			int dRow = direction[1];
			int collumn = getPosition().getCollumn() + dCollumn;
			int row = getPosition().getRow() + dRow;

			if (getBoard().positionExists(collumn, row)
					&& (isThereOpponentPiece(new Position(collumn, row)) || !getBoard().isTherePiece(collumn, row))
					&&getBoard().piece(collumn,row)!=null&&getBoard().piece(collumn,row).toString().equals("H")) {
				attackRoute[collumn][row] = true;
			}
		}
		
		return attackRoute;

	}

	public boolean canIBeHelped(ChessPiece p) {
		boolean[][] canBeDefended = p.possibleMoves();
		boolean[][] attackRoute = this.whereIsTheAttackCommingFrom();
		for (int i = 0; i < getBoard().getColumns(); i++) {
			for (int j = 0; j < getBoard().getRows(); j++) {
				if(attackRoute[i][j]&&canBeDefended[i][j]) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean[][] helpMe(boolean[][] pMov) {
		
		boolean[][] attackRoute = this.whereIsTheAttackCommingFrom();
		for (int i = 0; i < getBoard().getColumns(); i++) {
			for (int j = 0; j < getBoard().getRows(); j++) {
				if(pMov[i][j]&&attackRoute[i][j]) {
					pMov[i][j]=true;
				}
				else {
					pMov[i][j]=false;
				}
			}
		}

		return pMov;

	}

}
