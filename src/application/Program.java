package application;

import boardlayer.Position;
import chesslayer.ChessMatch;
import chesslayer.ChessPosition;

public class Program {

	public static void main(String[] args) {

		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.getPieces());
		
	}

}
