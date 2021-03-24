package chess.pieces;

import java.util.EnumSet;

import chess.Board;
import chess.Color;
import chess.Pos;

public final class Bishop extends Piece {

	private Bishop(Color color) {
		super(color,3);
	}

	public static final Bishop WHITE = new Bishop(Color.WHITE);
	public static final Bishop BLACK = new Bishop(Color.BLACK);

	public static Bishop get(Color color) {
		return color.isWhite() ? WHITE : BLACK;
	}

	@Override
	public EnumSet<Pos> tellAvailableMovesFrom(Pos start,Board board) {
		return tellAvailableMovesFrom(start,board,start.diagonalPositions);
	}

}
