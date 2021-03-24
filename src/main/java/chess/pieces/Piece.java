package chess.pieces;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map.Entry;

import chess.Board;
import chess.Color;
import chess.Direction;
import chess.Pos;

public abstract class Piece {

	//private static final Logger LOGGER = LoggerFactory.getLogger(Piece.class);

	public final Color color;
	public final float value;

	public float getValue() {
		return value;
	}

	public Piece(Color color,int value) {
		this.color = color;
		this.value = value;
	}

	public final boolean isBlack() {
		return color.isBlack();
	}

	public final boolean isWhite() {
		return color.isWhite();
	}

	public final String toHTML() {
		final char colorSymbol = isWhite() ? 'w' : 'b';
		return "<img src=\"pieces/"+getClass().getSimpleName().toLowerCase()+colorSymbol+".svg\">";
	}

	protected final char getSymbol() {
		return getClass().getSimpleName().charAt(0);
	}

	@Override
	public final String toString() {
		return toRightCase("|"+getSymbol())+'|';
	}

	abstract public EnumSet<Pos> tellAvailableMovesFrom(Pos start,Board board);

	protected final String toRightCase(String string) {
		return isWhite() ? string.toUpperCase() : string.toLowerCase();
	}

	protected final EnumSet<Pos> removeFriends(EnumSet<Pos> positions,Board board) {
		//LOGGER.info("removeFriends({}",positions);
		EnumSet<Pos> cleaned = EnumSet.noneOf(Pos.class);
		for (Pos pos:positions) {
			if (!board.hasFriend(pos)) {
				cleaned.add(pos);
			}
		}
		return cleaned;
		/*
		 * return EnumSet.copyOf(positions.stream().filter(p -> !board.hasFriend(p))
		 * .collect(Collectors.toSet()));
		 */
	}

	protected final EnumSet<Pos> tellAvailableMovesFrom(Pos start,Board board,
																											EnumMap<Direction,Pos[]> potentialMoves) {
		final EnumSet<Pos> okMoves = EnumSet.noneOf(Pos.class);
		for (Entry<Direction,Pos[]> dirPositions:potentialMoves.entrySet()) {
			for (Pos pos:dirPositions.getValue()) {
				if (board.hasFriend(pos)) {
					break;
				} else {
					okMoves.add(pos);
					if (!board.isEmpty(pos)) {
						break;
					}
				}
			}
		}
		return okMoves;
	}

}
