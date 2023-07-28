package boardlayer;

public class Position {
	
	private int collumn;
	private int row;
	
	public Position(int collumn, int row) {
		this.collumn = collumn;
		this.row = row;
	}
	public int getCollumn() {
		return collumn;
	}
	
	public int getRow() {
		return row;
	}
	public void setValues(int collumn,int row) {
		this.row = row;
		this.collumn=collumn;
	}
	@Override
	public String toString() {
		return "Position: " + collumn + ", " + row;
	}
	
	
	
	
}
