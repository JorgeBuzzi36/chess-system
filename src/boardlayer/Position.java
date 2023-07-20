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
	public void setCollumn(int collumn) {
		this.collumn = collumn;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	@Override
	public String toString() {
		return "Position: " + collumn + ", " + row;
	}
	
	
	
	
}
