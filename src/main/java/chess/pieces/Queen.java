package chess.pieces;

import java.util.EnumSet;

import chess.Board;
import chess.Color;
import chess.Pos;

public final class Queen extends Piece {

	private Queen(Color color) {
		super(color,7);
	}

	public static final Queen WHITE = new Queen(Color.WHITE);
	public static final Queen BLACK = new Queen(Color.BLACK);

	public static Queen get(Color color) {
		return color.isWhite() ? WHITE : BLACK;
	}

	@Override
	public EnumSet<Pos> tellAvailableMovesFrom(Pos start,Board board) {
		return tellAvailableMovesFrom(start,board,start.directedPositions);
	}

}
