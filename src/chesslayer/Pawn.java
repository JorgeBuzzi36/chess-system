package chesslayer;

import java.util.Arrays;

import boardlayer.Board;
import boardlayer.Position;

public class Pawn extends ChessPiece {
	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color,ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch=chessMatch;
	}

	@Override
	public String toString() {
		return "P";
	}

	@Override
	public boolean[][] possibleMoves() {
        boolean[][] pMov = new boolean[getBoard().getColumns()][getBoard().getRows()];
        
        switch(this.getColor()) {
        	
        case WHITE: 
        	
        	if(getBoard().piece(position.getCollumn(), position.getRow()+1)==null) {
        	pMov[position.getCollumn()][position.getRow()+1]=true;
        	if(this.getMoveCount()==0&&getBoard().piece(position.getCollumn(), position.getRow()+2)==null) {
        		pMov[position.getCollumn()][position.getRow()+2]=true;
        	}
        }
        	int[][]directionsWhite = {{-1,1},{1,1}};
        	for(int[] direction :directionsWhite) {
        		int dCollumn=direction[0];
        		int dRow=direction[1];
        		int collumn=position.getCollumn()+dCollumn;
        		int row=position.getRow()+dRow;
        		
        		 if (getBoard().positionExists(collumn, row) && isThereOpponentPiece(new Position(collumn, row))) {
                     pMov[collumn][row] = true;
                 }
        	}
        	//Consider implementing equal() in class Position

        	if(this.chessMatch.getEnPassantVunerable()!=null) {
           	 
           	int toTheRight=position.getCollumn()+1;
           	int toTheLeft=position.getCollumn()-1;
           	int sameRow=position.getRow();
           	
           	
           	if(this.chessMatch.getEnPassantVunerable().getPosition().getRow()==sameRow&&
           			this.chessMatch.getEnPassantVunerable().getPosition().getCollumn()==toTheLeft){
           		pMov[toTheLeft][sameRow+1]=true;
           	}
           	else if(this.chessMatch.getEnPassantVunerable().getPosition().getRow()==sameRow&&
           			this.chessMatch.getEnPassantVunerable().getPosition().getCollumn()==toTheRight){
           		pMov[toTheRight][sameRow+1]=true;
           	}
           
           }
        	break;
        	
        case BLACK:	
        	
        	if(getBoard().piece(position.getCollumn(), position.getRow()-1)==null) {
            	pMov[position.getCollumn()][position.getRow()-1]=true;
            	if(this.getMoveCount()==0&&getBoard().piece(position.getCollumn(), position.getRow()-2)==null) {
            		pMov[position.getCollumn()][position.getRow()-2]=true;
            }
        	}
            	int[][]directionsBlack = {{-1,-1},{1,-1}};
            	for(int[] direction :directionsBlack) {
            		int dCollumn=direction[0];
            		int dRow=direction[1];
            		int collumn=position.getCollumn()+dCollumn;
            		int row=position.getRow()+dRow;
            		
            		 if (getBoard().positionExists(collumn, row) && isThereOpponentPiece(new Position(collumn, row))) {
                         pMov[collumn][row] = true;
                     }
            	}
            	//Consider implementing equal() in class Position
                if(this.chessMatch.getEnPassantVunerable()!=null) {
                	
                	int toTheRight=position.getCollumn()+1;
                	int toTheLeft=position.getCollumn()-1;
                	int sameRow=position.getRow();
                	
                	
                	if(this.chessMatch.getEnPassantVunerable().getPosition().getRow()==sameRow&&
                			this.chessMatch.getEnPassantVunerable().getPosition().getCollumn()==toTheLeft){
                		pMov[toTheLeft][sameRow-1]=true;
                	}
                	else if(this.chessMatch.getEnPassantVunerable().getPosition().getRow()==sameRow&&
                			this.chessMatch.getEnPassantVunerable().getPosition().getCollumn()==toTheRight){
                		pMov[toTheRight][sameRow-1]=true;
                	}
                
                }
            	break;
        	
        	
        }
       
        boolean [][] onlyLegalMoves = checkLegalMoves();
        
        for(int i=0;i<getBoard().getColumns();i++) {
        	for(int j=0;j<getBoard().getRows();j++) {
        		if(pMov[i][j] && onlyLegalMoves[i][j]) {
        			pMov[i][j]=true;
        		}
        		else {
        			pMov[i][j]=false;
        		}
        		
        	}
        }
        
        return pMov;
	}

	private boolean[][] checkLegalMoves() {

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

			while (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {

				collumn += dCollumn;
				row += dRow;
			}

			if (getBoard().positionExists(collumn, row) && isThereMyKing(new Position(collumn, row))) {
				
				dCollumn *= -1;
				dRow *= -1;
				collumn += dCollumn;
				row += dRow;

				while (getBoard().positionExists(collumn, row) && !getBoard().isTherePiece(new Position(collumn, row))) {

					onlyLegalMoves[collumn][row] = true;
					collumn += dCollumn;
					row += dRow;
				}
				collumn = getPosition().getCollumn() + dCollumn;
				row = getPosition().getRow() + dRow;
				
				if (getBoard().positionExists(collumn, row) && isThereOpponentPiece(new Position(collumn, row))) {
					onlyLegalMoves[collumn][row] = true;
					return onlyLegalMoves;
				} else {
					return everyMoveIsLegal;
				}
			}

		}
		return everyMoveIsLegal;
	}

	private boolean isThereMyKing(Position position) {

		return getBoard().piece(position).getColor().equals(this.getColor())
				&& getBoard().piece(position).toString().equals("K");
	}
}
