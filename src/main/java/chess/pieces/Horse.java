package chess.pieces;

import java.util.EnumSet;

import chess.Board;
import chess.Color;
import chess.Pos;

public final class Horse extends Piece {

	public Horse(Color color) {
		super(color,3);
	}

	public static final Horse WHITE = new Horse(Color.WHITE);
	public static final Horse BLACK = new Horse(Color.BLACK);

	public static Horse get(Color color) {
		return color.isWhite() ? WHITE : BLACK;
	}

	@Override
	public EnumSet<Pos> tellAvailableMovesFrom(Pos start,Board board) {
		return removeFriends(start.horseMoves,board);
	}

}
