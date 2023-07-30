package application;

import java.util.Scanner;
import java.util.Set;

import chesslayer.ChessMatch;
import chesslayer.ChessMove;
import chesslayer.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch();
		boolean greenTiles[][] = new boolean[chessMatch.getBoard().getColumns()][chessMatch.getBoard().getRows()];
		boolean resetTiles[][] = new boolean[chessMatch.getBoard().getColumns()][chessMatch.getBoard().getRows()];
		UI.printBoard(chessMatch.getPieces(), greenTiles);
		System.out.println("Do you want to have the possible movable pieces highlighted?y/n");
		char optionHighlitedPieces = sc.next().charAt(0);
		System.out.println("Do you want to have the possible moves highlighted?y/n");
		char optionHighlitedMoves = sc.next().charAt(0);

		while (!chessMatch.isCheckMate()) {
			greenTiles = resetTiles;
			greenTiles = chessMatch.movablePieces();
			if (optionHighlitedPieces == 'y') {
				UI.printBoard(chessMatch.getPieces(), greenTiles);
			}
			System.out.println("Move position:");
			sc.nextLine();
			ChessMove chessMove = new ChessMove ("pe4",chessMatch);
			chessMove.extractChessPosition();
			
			System.out.println(chessMatch.getCurrentPlayer() + "to move:");
			ChessPosition sourcePosition = checkInput(sc);

			while (sourcePosition==null||positionCondition(chessMatch,sourcePosition,greenTiles)){
				System.out.println("Enter a valid position");
				sourcePosition = checkInput(sc);
			}
			greenTiles = chessMatch.matchPossibleMoves(sourcePosition);

			if (optionHighlitedMoves == 'y') {
				UI.printBoard(chessMatch.getPieces(), greenTiles);
			}
			System.out.println("to:");
			ChessPosition targetPosition = checkInput(sc);
			while (targetPosition==null||positionCondition(chessMatch,targetPosition,greenTiles)) {
				System.out.println("Enter a valid position");
				targetPosition =checkInput(sc);
			}
			chessMatch.performChessMove(sourcePosition, targetPosition);

			if (chessMatch.getEnPassantVunerable() != null) {
				System.out.println("\nOnPessant:" + chessMatch.getEnPassantVunerable().getChessPosition());
			}

		}
		sc.close();
	}
	private static boolean positionCondition(ChessMatch chessMatch, ChessPosition position, boolean[][]greenTiles) {
		return !chessMatch.getBoard().positionExists(position.toPosition())
				||!greenTiles[position.toPosition().getCollumn()][position.toPosition().getRow()];
	
	}

	private static ChessPosition checkInput(Scanner sc) {

		try {
			Set<Character> checkColumn = Set.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h');
			Set<Integer> checkRow = Set.of(1, 2, 3, 4, 5, 6, 7, 8);
			String position = sc.nextLine();
			while(position.length()!=2) {
				System.out.println("Enter a valid position format (A letter and a number no space");
				position = sc.nextLine();
			}
			
			char column = position.charAt(0);
			int row = Integer.parseInt(position.substring(1));
			
			
			//char column = sc.next().charAt(0);
			//int row = sc.nextInt();
			
			checkColumn.contains(column);
			checkRow.contains(row);
			if(checkColumn.contains(column)||checkRow.contains(row)) {
				return new ChessPosition(column,row);
			}
			else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
