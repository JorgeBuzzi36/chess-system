package chesslayer;

import boardlayer.Board;
import boardlayer.Piece;
import boardlayer.Position;

public abstract class ChessPiece extends Piece {
	
	private Color color;
	private int moveCount=0;
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition() {
		
		
		return new ChessPosition().fromPosition(this.position);
	}
	
	protected void increasseMoveCount() {
		this.moveCount++;
	}
	
	protected void decreasseMoveCount() {
		this.moveCount--;
	}
	protected boolean isThereOpponentPiece (Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p !=null && p.getColor()!=this.color;
	}
}
