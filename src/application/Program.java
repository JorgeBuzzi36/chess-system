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
		UI.printBoard(chessMatch.getPieces(), greenTiles);
		System.out.println("Do you want to have the possible movable pieces highlighted?y/n");
		char optionHighlitedPieces = sc.next().charAt(0);
		System.out.println("Do you want to have the possible moves highlighted?y/n");
		char optionHighlitedMoves = sc.next().charAt(0);
		sc.nextLine();
		while (!chessMatch.isCheckMate()) {
			greenTiles = chessMatch.movablePieces();
			if (optionHighlitedPieces == 'y') {
				UI.printBoard(chessMatch.getPieces(), greenTiles);
			}
			// Codigo para notação algebrica curta ( ex: pe4 ) 
			
				//Falta programação defensiva pra input errado
				//Falta implementar lista com Log das jogadas efetuadas (opcional)
			/*
			//-----------------------------------------------------
			System.out.println(chessMatch.getCurrentPlayer() + " to move:");
			String chessMoveScan = sc.nextLine();
			ChessMove chessMove = new ChessMove (chessMoveScan,chessMatch);
			ChessPosition[] move = chessMove.extractChessPosition();
			
			if (move!=null) {
				ChessPosition sourcePosition = move[0];		
				ChessPosition targetPosition = move[1];
				chessMatch.performChessMove(sourcePosition, targetPosition);
			}
			else {
				System.out.println("Invaid chess move");
			}
			//----------------------------------------------------------
			 */
			 
			
			// Codigo notaçao algebrica longa (ex: enter e2 then enter e4)
			//------------------------------------------------
			
			System.out.println(chessMatch.getCurrentPlayer() + " to move:");
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
			
			
			//-----------------------------------------------------------
			

		}
		System.out.println("Check Mate!!, the "+chessMatch.getCurrentPlayer()+" pieces lost the game");
		
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
				System.out.println("Enter a valid position format (A letter and a number no space)");
				position = sc.nextLine();
			}
			
			char column = position.charAt(0);
			int row = Integer.parseInt(position.substring(1));
			
			
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
