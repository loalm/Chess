package chess;

public class Move {
	public final Pos from,to;

	public Move(Pos from,Pos to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		return from.toString()+"-"+to.toString();
	}

}
