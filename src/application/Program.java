package application;

import boardlayer.Position;
import chesslayer.ChessMatch;
import chesslayer.ChessPosition;

public class Program {

	public static void main(String[] args) {

		ChessMatch chessMatch = new ChessMatch();
		boolean greenTiles[][]=new boolean[chessMatch.getBoard().getColumns()][chessMatch.getBoard().getRows()];
		UI.printBoard(chessMatch.getPieces(),greenTiles);
		greenTiles=chessMatch.movablePieces();
		UI.printBoard(chessMatch.getPieces(),greenTiles);
		greenTiles=chessMatch.matchPossibleMoves(new ChessPosition('d',2));
		UI.printBoard(chessMatch.getPieces(),greenTiles);
		
	}

}
