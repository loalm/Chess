package chess;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chess.pieces.Bishop;
import chess.pieces.Horse;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Piece;
import chess.pieces.Queen;
import chess.pieces.Rook;

public final class Board {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);

	public static final char[] allCols = {'A','B','C','D','E','F','G','H'};

	public double getScore(Color color) {
		return color.isWhite() ? whiteScore : blackScore;
	}

	private EnumSet<Pos> hasMoved = EnumSet.noneOf(Pos.class);

	public boolean hasMoved(Pos pos) {
		return hasMoved.contains(pos);
	}

	private final EnumMap<Pos,Piece> pieces = new EnumMap<Pos,Piece>(Pos.class);

	private Color nextColor = Color.WHITE;

	double blackScore;

	double whiteScore;

	SortedMap<Long,Move> movesDone = new TreeMap<>();

	private void put(Pos pos,Piece piece) {
		pieces.put(pos,piece);
	}

	private Piece remove(Pos pos) {
		return pieces.remove(pos);
	}

	public Piece get(Pos pos) {
		return pieces.get(pos);
	}

	public Piece get(char col,byte row) {
		return pieces.get(Pos.get(col,row));
	}

	public boolean hasPieceWithColor(Pos pos,Color color) {
		boolean result = isOccupied(pos)&&color==get(pos).color;
		return result;
	}

	public boolean hasFriend(Pos pos) {
		return hasPieceWithColor(pos,nextColor);
	}

	public boolean hasOpponent(Pos pos) {
		return hasPieceWithColor(pos,nextColor.inverse());
	}

	public boolean isEmpty(Pos pos) {
		return !pieces.containsKey(pos);
	}

	public boolean areEmpty(EnumSet<Pos> positions) {
		return positions.stream().allMatch(p -> isEmpty(p));
	}

	public boolean isOccupied(Pos pos) {
		boolean result = pieces.containsKey(pos);
		return result;
	}

	public Board() {
		reset();
	}

	private Board(Board anotherBoard) {
		this.pieces.putAll(anotherBoard.pieces);
		this.nextColor = anotherBoard.nextColor;
	}

	public void reset() {
		setupPieces();
		calculateScores();
	}

	private void setupPieces() {
		for (Color color:Color.values()) {
			for (char col:allCols) {
				put(Pos.get(col,color.secondRow),Pawn.get(color));
			}
			final King king = King.get(color);
			put(Pos.get('B',color.firstRow),Horse.get(color));
			put(Pos.get('G',color.firstRow),Horse.get(color));
			put(Pos.get('C',color.firstRow),Bishop.get(color));
			put(Pos.get('F',color.firstRow),Bishop.get(color));
			put(Pos.get('D',color.firstRow),Queen.get(color));
			put(king.startPos,king);
			put(king.leftRook,Rook.get(color));
			put(king.rightRook,Rook.get(color));
		}
	}

	@Override
	public String toString() {
		String result = "";
		for (byte row = 8;row>=1;row--) {
			result += row;
			for (char col:allCols) {
				Piece p = get(col,row);
				result += p==null ? "| |" : p;
			}
			result += "\n";
		}
		result += "  A  B  C  D  E  F  G  H";
		return result;
	}


	public boolean isAllowed(Pos from,Pos to) {
		Piece fromPiece = get(from);
		Piece toPiece = get(to);
		if (fromPiece==null) {
			return false;
		}
		if (toPiece!=null&&fromPiece.color==toPiece.color) {
			return false;
		}
		if (fromPiece.color!=nextColor) {
			return false;
		}
		return (fromPiece.tellAvailableMovesFrom(from,this)).contains(to);
	}

	public Board copyMove(Pos from,Pos to) {
		Board newBoard = new Board(this);
		newBoard.move(from,to);
		return newBoard;
	}

	public void move(Pos from,Pos to) {
		final Piece pieceFrom = get(from);

		if (!isAllowed(from,to)) {
			throw new IllegalArgumentException("Bad move:"+from+"-"+to);
		}

		// Promotion:
		if (pieceFrom==Pawn.WHITE&&to.row==8) {
			put(to,Queen.WHITE);
		} else if (pieceFrom==Pawn.BLACK&&to.row==1) {
			put(to,Queen.BLACK);
		} else {
			put(to,pieceFrom);
		}

		// Castling:
		if (pieceFrom instanceof King) {
			King king = (King) pieceFrom;
			if (from==king.startPos&&to==king.leftCastleTo) {
				put(king.leftRookCastleTo,remove(king.leftRook));
			}
			if (from==king.startPos&&to==king.rightCastleTo) {
				put(king.rightRookCastleTo,remove(king.rightRook));
			}
		}

		remove(from);
		hasMoved.add(from);
		movesDone.put(System.currentTimeMillis(),new Move(from,to));
		nextColor = nextColor.inverse();
		calculateScores();
		availableMoves = null;
	}

	private EnumMap<Pos,EnumSet<Pos>> availableMoves;

	public EnumMap<Pos,EnumSet<Pos>> getAvailableMoves() {
		if (availableMoves==null) {
			availableMoves = new EnumMap<>(Pos.class);
			for (Entry<Pos,Piece> posPiece:pieces.entrySet()) {
				final Pos pos = posPiece.getKey();
				if (hasFriend(pos)) {
					final Piece piece = posPiece.getValue();
					final EnumSet<Pos> pieceMoves = piece.tellAvailableMovesFrom(pos,this);
					if (!pieceMoves.isEmpty()) {
						availableMoves.put(pos,pieceMoves);
					}
				}
			}
		}
		return availableMoves;
	}

	private void calculateScores() {
		whiteScore = pieces.values().stream().filter(Piece::isWhite).mapToDouble(Piece::getValue).sum();
		blackScore = pieces.values().stream().filter(Piece::isBlack).mapToDouble(Piece::getValue).sum();
	}

	public Color getNextColor() {
		return nextColor;
	}

}
