package chess;

import java.util.EnumSet;

public enum Direction {
	UP(0,1), LEFT(-1,0), RIGHT(1,0), DOWN(0,-1), //
	RIGHT_UP(1,1), RIGHT_DOWN(1,-1), LEFT_DOWN(-1,-1), LEFT_UP(-1,1), //
	HORSE1(-2,1), HORSE2(-1,2), HORSE3(1,2), HORSE4(2,1), //
	HORSE5(2,-1), HORSE6(1,-2), HORSE7(-1,-2), HORSE8(-2,-1);
	public static final EnumSet<Direction> STRAIGHTS = EnumSet.of(UP,LEFT,RIGHT,DOWN);
	public static final EnumSet<Direction> DIAGONALS = EnumSet.of(RIGHT_DOWN,RIGHT_UP,LEFT_UP,
																																LEFT_DOWN);
	public static final EnumSet<Direction> NORMALS = EnumSet.of(UP,LEFT,RIGHT,DOWN,RIGHT_DOWN,
																															RIGHT_UP,LEFT_UP,LEFT_DOWN);
	public static final EnumSet<Direction> HORSES = EnumSet.range(HORSE1,HORSE8);

	public final int letterDir,numberDir;

	private Direction(int letterDir,int numberDir) {
		this.letterDir = letterDir;
		this.numberDir = numberDir;
	}

}
