package boardlayer;

public class Board {
	
	private static int columns=8;
	private static int rows=8;
	private Piece pieces[][];
	
	public Board() {
		pieces = new Piece[this.columns][this.rows];	
	
	}

	public static int getColumns() {
		return columns;
	}

	public static int getRows() {
		return rows;
	}
	
	public Piece piece(int column, int row) {
		return this.pieces[column][row];
	}
	
	public Piece piece(Position position) {
		return this.pieces[position.getCollumn()][position.getRow()];
	}
	
	public void placePiece (Piece piece, Position position) {
		
		this.pieces[position.getCollumn()][position.getRow()]=piece;
		
	}
}
