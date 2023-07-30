package chesslayer;

public class ChessMove {
	
	private ChessPosition[] chessPosition;
	private String chessMove;
	private ChessMatch chessMatch;
	
	
	public ChessMove(String chessMove,ChessMatch chessMatch) {
		this.chessMove=chessMove;
		this.chessMatch=chessMatch;
	}
	
	private boolean pieceCanMoveThere(ChessPiece matchPosition, int column, int row) {
		boolean[][] possibleMoves = matchPosition.possibleMoves();
		
		for(int i=0;i<chessMatch.getBoard().getColumns();i++) {
			for(int j =0 ; j<chessMatch.getBoard().getRows();j++) {
				
				return	possibleMoves[i][j]&&column==i&&row==j;
			}
		}
		return false;
		
	}
	
	
	public ChessPosition[] extractChessPosition() {
		ChessPiece[][]matchPosition = chessMatch.getPieces();
		String chessPiece = chessMove.substring(0).toUpperCase();
		String targetPosition = chessMove.substring(1,2);
		char column = targetPosition.charAt(0);
		int row = Integer.parseInt(targetPosition.substring(1));
		
		for(int i=0;i<chessMatch.getBoard().getColumns();i++) {
			for(int j=0 ;j<chessMatch.getBoard().getRows();j++) {
				if(chessPiece==matchPosition[i][j].toString()&&matchPosition[i][j].getColor()==chessMatch.getCurrentPlayer()
						&& pieceCanMoveThere(matchPosition[i][j], column, row)) {
					int[] anotherPiece =null;
					anotherPiece =isThereAnotherPieceWithTheSamePossibleMove(column,row,chessPiece);
					ChessPosition sourceChessPosition = new ChessPosition();
					sourceChessPosition = sourceChessPosition.fromPosition(i,j);
					ChessPosition targetChessPosition = new ChessPosition();
					targetChessPosition = targetChessPosition.fromPosition(column,row);
					if(anotherPiece==null) {
						
						chessPosition[0] = sourceChessPosition;
						chessPosition[1] = targetChessPosition;
						return chessPosition;
					}
					else {
						ChessPosition anotherSourceChessPosition = new ChessPosition();
						anotherSourceChessPosition = anotherSourceChessPosition.fromPosition(anotherPiece[0],anotherPiece[1]);
						System.out.println("There are two different pieces of that type that can go to the same square, they are:"
								+chessPiece+ sourceChessPosition +" and "+chessPiece+anotherSourceChessPosition);
						
					}
				}
				
			}
		}
		
		return null;
	}

	private int[] isThereAnotherPieceWithTheSamePossibleMove(char column, int row, String chessPiece) {
		ChessPiece[][]matchPosition = chessMatch.getPieces();
		
		for(int i=1;i<chessMatch.getBoard().getColumns();i++) {
			for(int j =0;j<chessMatch.getBoard().getRows();j++) {
				if(i!=column&&j!=row&&pieceCanMoveThere(matchPosition[i][j], column, row)) {
					int[] anotherPiece = {i,j};
					return anotherPiece;
					
				}
				
			}
		}
		
		
		return null;
	}
	
}
