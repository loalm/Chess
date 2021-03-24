package chess.pieces;

import java.util.EnumSet;

import chess.Board;
import chess.Color;
import chess.Direction;
import chess.Pos;

public final class Pawn extends Piece {

	private Pawn(Color color,byte startRow) {
		super(color,1);
		// this.startRow=startRow;
	}

	// public final byte startRow;

	public static final Pawn WHITE = new Pawn(Color.WHITE,(byte) 2);
	public static final Pawn BLACK = new Pawn(Color.BLACK,(byte) 7);

	public static Pawn get(Color color) {
		return color.isWhite() ? WHITE : BLACK;
	}

	@Override
	public EnumSet<Pos> tellAvailableMovesFrom(Pos start,Board board) {
		final EnumSet<Pos> okMoves = EnumSet.noneOf(Pos.class);
		final Direction direction = isWhite() ? Direction.UP : Direction.DOWN;
		final Pos nextPos = start.add(direction);
		if (board.isEmpty(nextPos)) {
			okMoves.add(nextPos);
			if ((isWhite()&&start.row==2)||//
					(isBlack()&&start.row==7)) {
				final Pos nextPos2 = nextPos.add(direction);
				if (board.isEmpty(nextPos2)) {
					okMoves.add(nextPos2);
				}
			}
		}
		if (start.col>'A') {
			final Direction leftStrike = isWhite() ? Direction.LEFT_UP : Direction.LEFT_DOWN;
			final Pos leftStrikePos = start.add(leftStrike);
			if (board.hasOpponent(leftStrikePos)) {
				okMoves.add(leftStrikePos);
			}
		}
		if (start.col<'H') {
			final Direction rightStrike = isWhite() ? Direction.RIGHT_UP : Direction.RIGHT_DOWN;
			final Pos rightStrikePos = start.add(rightStrike);
			if (board.hasOpponent(rightStrikePos)) {
				okMoves.add(rightStrikePos);
			}
		}
		return okMoves;
	}

}
