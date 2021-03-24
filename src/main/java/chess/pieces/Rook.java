package chess.pieces;

import java.util.EnumSet;

import chess.Board;
import chess.Color;
import chess.Pos;

public final class Rook extends Piece {

	private Rook(Color color) {
		super(color,4);
	}

	public static final Rook WHITE = new Rook(Color.WHITE);
	public static final Rook BLACK = new Rook(Color.BLACK);

	public static Rook get(Color color) {
		return color.isWhite() ? WHITE : BLACK;
	}

	@Override
	public EnumSet<Pos> tellAvailableMovesFrom(Pos start,Board board) {
		return tellAvailableMovesFrom(start,board,start.straightPositions);
	}

}