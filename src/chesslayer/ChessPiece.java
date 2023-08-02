package chesslayer;

import java.util.Arrays;

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
	
	public boolean[][] checkLegalMoves() {

		boolean[][] onlyLegalMoves = new boolean[getBoard().getColumns()][getBoard().getRows()];
		boolean[][] everyMoveIsLegal = new boolean[getBoard().getColumns()][getBoard().getRows()];
		for (boolean[] b : everyMoveIsLegal) {
			Arrays.fill(b, true);
		}
		int[][] directions = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 }, { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 } };

		for (int[] direction : directions) {

			int dCollumn = direction[0];
			int dRow = direction[1];
			int collumn = getPosition().getCollumn() + dCollumn;
			int row = getPosition().getRow() + dRow;
			
			//Checks if the piece is directly shielding another piece
			while (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {

				collumn += dCollumn;
				row += dRow;
			}
			//Checks if the piece being shielded is the King
			if (getBoard().positionExists(collumn, row) && isThereMyKing(new Position(collumn, row))) {
				//Reverse the direction from the king towards a potential attacking piece
				dCollumn *= -1;
				dRow *= -1;
				collumn += dCollumn;
				row += dRow;
			//Validate all squares between the queen and the piece being tested	
				while (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {

					onlyLegalMoves[collumn][row] = true;
					collumn += dCollumn;
					row += dRow;
				}
				//Goes to the opposite direction from the king, and look for a potential enemy piece threat to the king
				collumn = getPosition().getCollumn() + dCollumn;
				row = getPosition().getRow() + dRow;
				while (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {

					onlyLegalMoves[collumn][row] = true;
					collumn += dCollumn;
					row += dRow;
				}
				if (getBoard().positionExists(collumn, row) && isThereOpponentPiece(new Position(collumn, row))) {
					onlyLegalMoves[collumn][row] = true;
					//dCollumn*dRow is checking if i am on a diagonal or a straight line
					// This will check if the enemy piece found is pining the current piece
					if(checkThreats(dCollumn*dRow,getBoard().piece(new Position(collumn,row)).toString())) {
					return onlyLegalMoves;
					}
				} else {
					return everyMoveIsLegal;
				}
			}

		}
		return everyMoveIsLegal;
	}

	
	
	public boolean checkThreats(int direction, String string) {
		// Returns true if its a Queen or a Bishop on a diagonal
		if(direction!=0 && (string.equals("B")||string.equals("Q"))) {
			return true;
		}
		// Returns true if its a Rook or a Queen on a straight line 
		if(direction==0 && (string.equals("R")||string.equals("Q"))) {
			return true;
		}
		else {
			return false;
		}
	}

	private boolean isThereMyKing(Position position) {

		return getBoard().piece(position).getColor().equals(this.getColor())
				&& getBoard().piece(position).toString().equals("K");
	}
	
}
