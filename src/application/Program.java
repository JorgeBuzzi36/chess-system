package application;

import java.util.Scanner;

import boardlayer.Position;
import chesslayer.ChessMatch;
import chesslayer.ChessPosition;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		boolean greenTiles[][]=new boolean[chessMatch.getBoard().getColumns()][chessMatch.getBoard().getRows()];
		boolean resetTiles[][]=new boolean[chessMatch.getBoard().getColumns()][chessMatch.getBoard().getRows()];
		UI.printBoard(chessMatch.getPieces(),greenTiles);
		System.out.println("Do you want to have the possible movable pieces highlighted?y/n");
		char optionHighlitedPieces = sc.next().charAt(0);
		System.out.println("Do you want to have the possible moves highlighted?y/n");
		char optionHighlitedMoves = sc.next().charAt(0);
		
		
		
			while(!chessMatch.isCheckMate()) {
				greenTiles=resetTiles;
				greenTiles=chessMatch.movablePieces();
				if(optionHighlitedPieces=='y') {
					UI.printBoard(chessMatch.getPieces(),greenTiles);
				}
				
				
				System.out.println(chessMatch.getCurrentPlayer()+"to move:");
				ChessPosition sourcePosition = new ChessPosition(sc.next().charAt(0),sc.nextInt());
				while(!greenTiles[sourcePosition.toPosition().getCollumn()][sourcePosition.toPosition().getRow()]) {
					System.out.println("Enter a valid position");
					sourcePosition = new ChessPosition(sc.next().charAt(0),sc.nextInt());
				}
				greenTiles=chessMatch.matchPossibleMoves(sourcePosition);
	
				if(optionHighlitedMoves=='y') {
					UI.printBoard(chessMatch.getPieces(),greenTiles);
				}
				System.out.println("to:");
				ChessPosition targetPosition = new ChessPosition(sc.next().charAt(0),sc.nextInt());
				while(!greenTiles[targetPosition.toPosition().getCollumn()][targetPosition.toPosition().getRow()]) {
					System.out.println("Enter a valid position");
					targetPosition = new ChessPosition(sc.next().charAt(0),sc.nextInt());
				}
				chessMatch.performChessMove(sourcePosition, targetPosition);
				
				if(chessMatch.getEnPassantVunerable()!=null) {
				System.out.println("\nOnPessant:" + chessMatch.getEnPassantVunerable().getChessPosition());
				}
				
			}
			sc.close();
	}

}
