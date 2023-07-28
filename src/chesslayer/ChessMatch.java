package chesslayer;
import boardlayer.Board;
import boardlayer.Piece;

public class ChessMatch {
	
	private Board board;
	private int turn =1;
	private Color currentPlayer=Color.WHITE;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVunerable;
	private ChessPiece promoted;
	
	public ChessMatch() {
		board = new Board();
		this.startMatch();
	}
	public Board getBoard() {
		return this.board;
	}
	
	private void startMatch() {
	// Gerar peças brancas
	for(int i =0 ; i<board.getColumns();i++) {
		board.placePiece(new Pawn(this.board,Color.WHITE),new ChessPosition((char)((int)'a'+i),2).toPosition());
	}
	
	board.placePiece(new Rook(this.board,Color.WHITE), new ChessPosition('a',1).toPosition());
	board.placePiece(new Rook(this.board,Color.WHITE), new ChessPosition('h',1).toPosition());
	board.placePiece(new Knight(this.board,Color.WHITE), new ChessPosition('b',1).toPosition());
	board.placePiece(new Knight(this.board,Color.WHITE), new ChessPosition('g',1).toPosition());
	board.placePiece(new Bishop(this.board,Color.WHITE), new ChessPosition('c',1).toPosition());
	board.placePiece(new Bishop(this.board,Color.WHITE), new ChessPosition('f',1).toPosition());
	board.placePiece(new King(this.board,Color.WHITE), new ChessPosition('e',1).toPosition());
	board.placePiece(new Queen(this.board,Color.WHITE), new ChessPosition('d',1).toPosition());
	
	// Gerar peças pretas
	for(int i =0 ; i<board.getColumns();i++) {
		board.placePiece(new Pawn(this.board,Color.BLACK),new ChessPosition((char)((int)'a'+i),7).toPosition());
	}	
	
	board.placePiece(new Rook(this.board,Color.BLACK), new ChessPosition('a',8).toPosition());
	board.placePiece(new Rook(this.board,Color.BLACK), new ChessPosition('h',8).toPosition());
	board.placePiece(new Knight(this.board,Color.BLACK), new ChessPosition('b',8).toPosition());
	board.placePiece(new Knight(this.board,Color.BLACK), new ChessPosition('g',8).toPosition());
	board.placePiece(new Bishop(this.board,Color.BLACK), new ChessPosition('c',8).toPosition());
	board.placePiece(new Bishop(this.board,Color.BLACK), new ChessPosition('f',8).toPosition());
	board.placePiece(new King(this.board,Color.BLACK), new ChessPosition('e',8).toPosition());
	board.placePiece(new Queen(this.board,Color.BLACK), new ChessPosition('d',8).toPosition());	
		
	}

	public ChessPiece[][] getPieces() {
		
		ChessPiece[][] chessBoardPosition = new ChessPiece[board.getColumns()][board.getRows()];
		
		for ( int i =0 ; i<board.getColumns();i++) {
			for(int j=0;j<board.getRows();j++){
				
				chessBoardPosition[i][j]= (ChessPiece)board.piece(i,j);   
			}
		}
		return chessBoardPosition;	
	}
	
	public boolean[][] matchPossibleMoves(ChessPosition source){
		
		return this.board.piece(source.toPosition()).possibleMoves();
		
	}
	
	public boolean[][] movablePieces() {
	    boolean[][] movP = new boolean[board.getColumns()][board.getRows()];
	    int aux = 0;

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

	    if (aux == 0) {
	        throw new ChessException("No more possible moves, Draw by stalemate");
	    }

	    return movP;
	}
	

}
