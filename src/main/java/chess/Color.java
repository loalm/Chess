package chess;

public enum Color {
	WHITE(1,2,8), BLACK(8,7,1);

	private Color(int firstRow,int secondRow,int lastRow) {
		this.firstRow = (byte) firstRow;
		this.secondRow = (byte) secondRow;
		this.lastRow = (byte) lastRow;
	}

	public final byte firstRow,secondRow,lastRow;

	public Color inverse() {
		return this==BLACK ? WHITE : BLACK;
	}

	public boolean isBlack() {
		return this==BLACK;
	}

	public boolean isWhite() {
		return this==WHITE;
	}
}
