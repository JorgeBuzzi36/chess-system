package boardlayer;


public class Piece {
	
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
	}

	private Board getBoard() {
		return board;
	}
	
	
	
	
}
