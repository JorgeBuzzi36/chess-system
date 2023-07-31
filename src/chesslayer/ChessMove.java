package chesslayer;

public class ChessMove {
	
	private ChessPosition[] chessPosition = new ChessPosition[2];
	private String chessMove;
	private ChessMatch chessMatch;
	
	
	public ChessMove(String chessMove,ChessMatch chessMatch) {
		this.chessMove=chessMove;
		this.chessMatch=chessMatch;
	}
	
	private boolean pieceCanMoveThere(ChessPiece matchPosition, ChessPosition chessPosition) {
		boolean[][] possibleMoves = matchPosition.possibleMoves();
		
		for(int i=0;i<chessMatch.getBoard().getColumns();i++) {
			for(int j =0 ; j<chessMatch.getBoard().getRows();j++) {
				
				if(possibleMoves[i][j]&&chessPosition.toPosition().getCollumn()==i&&chessPosition.toPosition().getRow()==j) {
				return	true;
				}
			}
		}
		return false;
		
	}
	
	
	public ChessPosition[] extractChessPosition() {
		
		ChessPiece[][]matchPosition = chessMatch.getPieces();
		
		//Convert string to a String with the piece name and a Chess Position i'm targeting
		String chessPiece = chessMove.substring(0,1);
		chessPiece = chessPiece.toUpperCase();
		String targetPosition = chessMove.substring(1,3);
		char chessPositionColumn = targetPosition.charAt(0);
		int chessPositionRow = Integer.parseInt(targetPosition.substring(1));
		ChessPosition targetChessPosition=new ChessPosition(chessPositionColumn,chessPositionRow);
		
		for(int i=0;i<chessMatch.getBoard().getColumns();i++) {
			for(int j=0 ;j<chessMatch.getBoard().getRows();j++) {
				
				
				if(matchPosition[i][j]!=null&&chessPiece.equals(matchPosition[i][j].toString())&&matchPosition[i][j].getColor().equals(chessMatch.getCurrentPlayer())
						&& pieceCanMoveThere(matchPosition[i][j], targetChessPosition)) {
					int[] anotherPiece =null;
					ChessPosition sourceChessPosition = new ChessPosition();
					sourceChessPosition = sourceChessPosition.fromPosition(i,j);
					// ARRUMAR ISSO AQUI
					anotherPiece =isThereAnotherPieceWithTheSamePossibleMove();
					
					
					if(anotherPiece==null) {
						
						chessPosition[0] = sourceChessPosition;
						chessPosition[1] = targetChessPosition;
						System.out.println("Moving :"+ chessPiece+ chessPosition[0]+" to " + chessPosition[1]);
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
	
	
	private int[] isThereAnotherPieceWithTheSamePossibleMove() {
		
		
		
		return null;
	}
	
}
