package chesslayer;

import boardlayer.Board;
import boardlayer.Position;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
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
            	break;
        	
        	
        }
        
        
        return pMov;
    }
	
}
