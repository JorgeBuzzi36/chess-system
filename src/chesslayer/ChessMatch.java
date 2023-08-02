package chesslayer;

import java.util.Scanner;

import boardlayer.Board;
import boardlayer.Piece;

public class ChessMatch {

	private Board board;
	private int turn = 1;
	private Color currentPlayer = Color.WHITE;
	private int check;
	private boolean checkMate;
	private boolean castleAvaliable;
	private ChessPiece enPassantVunerable;
	private ChessPosition whiteKingPosition = new ChessPosition('e', 1);
	private ChessPosition blackKingPosition = new ChessPosition('e', 8);
	private ChessPosition currentKingPosition;

	public ChessMatch() {
		board = new Board();
		this.startMatch();
		// for any starting king position
		this.setKingsPositions();

	}

	public ChessPiece getEnPassantVunerable() {
		if (enPassantVunerable != null) {
			return enPassantVunerable;
		} else {
			return null;
		}
	}

	public boolean isCastleAvaliable() {
		return castleAvaliable;
	}

	public void setCastleAvaliable(boolean castleAvaliable) {
		this.castleAvaliable = castleAvaliable;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Color currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public boolean isCheckMate() {
		return checkMate;
	}

	public Board getBoard() {
		return this.board;
	}

	private void setKingsPositions() {
		// Implement later
		this.currentKingPosition = this.whiteKingPosition;
	}

	private void startMatch() {
		// Generate white pieces
		// for (int i = 0; i < board.getColumns(); i++) {
		// board.placePiece(new Pawn(this.board, Color.WHITE,this),
		// new ChessPosition((char) ((int) 'a' + i), 2).toPosition());
		// }

		// board.placePiece(new Rook(this.board, Color.WHITE), new ChessPosition('a',
		// 1).toPosition());
		// board.placePiece(new Rook(this.board, Color.WHITE), new ChessPosition('h',
		// 1).toPosition());
		// board.placePiece(new Knight(this.board, Color.WHITE), new ChessPosition('b',
		// 1).toPosition());
		// board.placePiece(new Knight(this.board, Color.WHITE), new ChessPosition('g',
		// 1).toPosition());
		// board.placePiece(new Bishop(this.board, Color.WHITE), new ChessPosition('c',
		// 1).toPosition());
		// board.placePiece(new Bishop(this.board, Color.WHITE), new ChessPosition('f',
		// 1).toPosition());
		board.placePiece(new King(this.board, Color.WHITE, this), new ChessPosition('e', 1).toPosition());
		// board.placePiece(new Queen(this.board, Color.WHITE), new ChessPosition('d',
		// 1).toPosition());

		// Gererate black pieces
		for (int i = 0; i < board.getColumns(); i++) {
			board.placePiece(new Pawn(this.board, Color.BLACK, this),
					new ChessPosition((char) ((int) 'a' + i), 7).toPosition());
		}

		board.placePiece(new Rook(this.board, Color.BLACK), new ChessPosition('a', 8).toPosition());
		board.placePiece(new Rook(this.board, Color.BLACK), new ChessPosition('h', 8).toPosition());
		board.placePiece(new Knight(this.board, Color.BLACK), new ChessPosition('b', 8).toPosition());
		board.placePiece(new Knight(this.board, Color.BLACK), new ChessPosition('g', 8).toPosition());
		board.placePiece(new Bishop(this.board, Color.BLACK), new ChessPosition('c', 8).toPosition());
		board.placePiece(new Bishop(this.board, Color.BLACK), new ChessPosition('f', 8).toPosition());
		board.placePiece(new King(this.board, Color.BLACK, this), new ChessPosition('e', 8).toPosition());
		board.placePiece(new Queen(this.board, Color.BLACK), new ChessPosition('d', 8).toPosition());

		// test
		board.placePiece(new Rook(this.board, Color.WHITE), new ChessPosition('c', 1).toPosition());
		board.placePiece(new Rook(this.board, Color.BLACK), new ChessPosition('a', 1).toPosition());
		//board.placePiece(new Bishop(this.board, Color.BLACK), new ChessPosition('g', 3).toPosition());
		board.placePiece(new Rook(this.board, Color.BLACK), new ChessPosition('e', 5).toPosition());
	}

	public ChessPiece[][] getPieces() {

		ChessPiece[][] chessBoardPosition = new ChessPiece[board.getColumns()][board.getRows()];

		for (int i = 0; i < board.getColumns(); i++) {
			for (int j = 0; j < board.getRows(); j++) {

				chessBoardPosition[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return chessBoardPosition;
	}

	public void isCheck() {
		boolean[][] attackedSquares;
		this.check = 0;
		for (int i = 0; i < this.board.getColumns(); i++) {
			for (int j = 0; j < this.board.getRows(); j++) {
				if (this.board.piece(i, j) != null) {

					attackedSquares = this.board.piece(i, j).possibleMoves();
					int bKingColumn = this.blackKingPosition.toPosition().getCollumn();
					int bKingRow = this.blackKingPosition.toPosition().getRow();
					int wKingColumn = this.whiteKingPosition.toPosition().getCollumn();
					int wKingRow = this.whiteKingPosition.toPosition().getRow();
					// Verifies if the king is being attacked
					if (attackedSquares[bKingColumn][bKingRow] || attackedSquares[wKingColumn][wKingRow]) {

						this.check++;

					}

				}
			}
		}
		if(check>0) {
			System.out.println("Check! "+check);
		}
	}
	
	
	
	public boolean[][] matchPossibleMoves(ChessPosition source) {
		boolean[][] possibleMoves = new boolean[this.board.getColumns()][this.board.getRows()];
		possibleMoves = this.board.piece(source.toPosition()).possibleMoves();
		if (check>0) {
			King kingUnderAttack = (King) this.board.piece(this.currentKingPosition.toPosition());
			kingUnderAttack.helpMe(possibleMoves,check);
		}
		return possibleMoves;
	}

	private void pawnHandler(ChessPiece p, ChessPosition target) {

		int rowCheck = target.toPosition().getRow();
		if (p.getMoveCount() == 0 && (rowCheck == 4 || rowCheck == 3)) {
			this.enPassantVunerable = p;
		}

		if (p.getColor() == Color.WHITE && rowCheck == 7) {
			this.promotePiece(p);
		}
		if (p.getColor() == Color.BLACK && rowCheck == 0) {
			this.promotePiece(p);
		}

	}

	private void promotePiece(ChessPiece p) {
		System.out.println("\n What do you want to promote to: (q)ueen (b)ishop (k)night (r)ook");
		try {

			// this might cause a memory leak
			Scanner sc1 = new Scanner(System.in);

			char promotePiece = sc1.next().charAt(0);
			while (promotePiece != 'q' && promotePiece != 'b' && promotePiece != 'k' && promotePiece != 'r') {
				System.out.println("Choose a valid option");
				promotePiece = sc1.next().charAt(0);
			}

			switch (promotePiece) {
			case 'q':
				board.placePiece(new Queen(this.board, Color.WHITE), p.getPosition());
				break;

			case 'b':
				board.placePiece(new Bishop(this.board, Color.WHITE), p.getPosition());
				break;

			case 'k':
				board.placePiece(new Knight(this.board, Color.WHITE), p.getPosition());
				break;
			case 'r':
				board.placePiece(new Knight(this.board, Color.WHITE), p.getPosition());
				break;

			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

	public ChessPiece performChessMove(ChessPosition source, ChessPosition target) {

		ChessPiece p = (ChessPiece) getBoard().piece(source.toPosition());
		Piece captured = null;

		if (p.toString() == "P") {
			this.pawnHandler(p, target);

		}
		if (p.isThereOpponentPiece(target.toPosition())) {
			captured = getBoard().removePiece(target.toPosition());

		}
		if (this.enPassantVunerable != null && this.enPassantVunerable.getPosition() != null) {
			if (this.currentPlayer == Color.WHITE) {
				ChessPosition enPassantTest = new ChessPosition(target.getColumn(), target.getRow() - 1);
				if (!p.isThereOpponentPiece(target.toPosition())
						&& this.enPassantVunerable.getPosition().equals(enPassantTest.toPosition())) {
					captured = getBoard().removePiece(enPassantTest.toPosition());
				}
			}
			if (this.currentPlayer == Color.BLACK) {
				ChessPosition enPassantTest = new ChessPosition(target.getColumn(), target.getRow() + 1);
				if (!p.isThereOpponentPiece(target.toPosition())
						&& this.enPassantVunerable.getPosition().equals(enPassantTest.toPosition())) {
					captured = getBoard().removePiece(enPassantTest.toPosition());
				}
			}
		}
		// Long Castle
		if (this.castleAvaliable && target.getColumn() == 'c' && p.toString().equals("K")) {
			ChessPosition longRook = new ChessPosition('a', target.getRow());
			getBoard().removePiece(longRook.toPosition());
			board.placePiece(new Rook(this.board, this.getCurrentPlayer()),
					new ChessPosition('d', target.getRow()).toPosition());
		}
		// Short Castle
		if (this.castleAvaliable && target.getColumn() == 'g' && p.toString().equals("K")) {
			ChessPosition shortRook = new ChessPosition('h', target.getRow());
			getBoard().removePiece(shortRook.toPosition());
			board.placePiece(new Rook(this.board, this.getCurrentPlayer()),
					new ChessPosition('f', target.getRow()).toPosition());
		}

		// p getting assigned the piece in the source position again to prevent after
		// promotion reference shenanigans
		p = (ChessPiece) getBoard().piece(source.toPosition());
		getBoard().removePiece(source.toPosition());
		getBoard().placePiece(p, target.toPosition());

		if (p.toString().equals("K")) {
			if (p.getColor() == Color.WHITE) {
				this.whiteKingPosition = target;
			}
			if (p.getColor() == Color.BLACK) {
				this.blackKingPosition = target;
			}

		}

		if (this.enPassantVunerable != null && this.enPassantVunerable.getMoveCount() > 0) {
			this.enPassantVunerable = null;
		}

		p.increasseMoveCount();

		if (this.currentPlayer == Color.WHITE) {
			this.currentPlayer = Color.BLACK;
			this.currentKingPosition = this.blackKingPosition;
		} else {
			this.currentPlayer = Color.WHITE;
			this.currentKingPosition = this.whiteKingPosition;
		}
		return (ChessPiece) captured;
	}

	public boolean[][] movablePieces() {
		boolean[][] movP = new boolean[board.getColumns()][board.getRows()];
		int aux = 0;
		if (this.check>1) {
			movP[this.currentKingPosition.toPosition().getCollumn()][this.currentKingPosition.toPosition().getRow()] =true;
			aux++;
		}
		else {
		for (int i = 0; i < board.getColumns(); i++) {
			for (int j = 0; j < board.getRows(); j++) {
				Piece piece = board.piece(i, j);
				if (piece != null && piece.getColor() == currentPlayer) {
					movP[i][j] = piece.isThereAnyPossibleMoves();

					if (movP[i][j]) {
						aux++;
					}
				}
			}
		}
		}
		if (aux == 0 && this.check==0) {
			throw new ChessException("No more possible moves, Draw by stalemate");
		} else if (aux == 0 && this.check>0) {
			this.checkMate = true;
		}

		return movP;
	}

}
