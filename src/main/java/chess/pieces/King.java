package chess.pieces;

import java.util.EnumMap;
import java.util.EnumSet;

import chess.Board;
import chess.Color;
import chess.Pos;

public class King extends Piece {

	public final byte startRow;
	public final Pos startPos,leftCastleTo,rightCastleTo,leftRook,rightRook;
	public final EnumSet<Pos> leftCastlingBetweens,rightCastlingBetweens;
	public final Pos leftRookCastleTo, rightRookCastleTo;

	private King(Color color) {
		super(color,1000);
		startRow = (byte) (color.isWhite() ? 1 : 8);
		startPos = Pos.get('E',startRow);
		leftRook = Pos.get('A',startRow);
		rightRook = Pos.get('H',startRow);
		leftCastleTo = Pos.get('C',startRow);
		rightCastleTo = Pos.get('G',startRow);
		leftRookCastleTo = Pos.get('D',startRow);
		rightRookCastleTo = Pos.get('F',startRow);
		leftCastlingBetweens = EnumSet.of(Pos.get('B',startRow),Pos.get('C',startRow),
																			Pos.get('D',startRow));
		rightCastlingBetweens = EnumSet.of(Pos.get('F',startRow),Pos.get('G',startRow));
	}

	public static final EnumMap<Color,King> instances = new EnumMap<>(Color.class);
	public static final King WHITE = new King(Color.WHITE);// ,E1,C1,G1,A1,H1);
	public static final King BLACK = new King(Color.BLACK);// ,E8,C8,G8,A8,H8);
	static {
		instances.put(Color.WHITE,WHITE);
	}

	public static King get(Color color) {
		return color.isWhite() ? WHITE : BLACK;
	}

	@Override
	public EnumSet<Pos> tellAvailableMovesFrom(Pos start,Board board) {
		{
			final EnumSet<Pos> okMoves = removeFriends(start.neighbours,board);
			// Castling:
			if (!board.hasMoved(start)) {
				// Left castling:
				if (!board.hasMoved(leftRook)) {
					if (board.areEmpty(leftCastlingBetweens)) {
						okMoves.add(leftCastleTo);
					}
				}
				// Right castling:
				if (!board.hasMoved(rightRook)) {
					if (board.areEmpty(rightCastlingBetweens)) {
						okMoves.add(rightCastleTo);
					}
				}
			}
			return okMoves;
		}
	}

}
