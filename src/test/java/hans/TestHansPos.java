package hans;

import static chess.Pos.*;

import org.junit.Assert;
import org.junit.Test;

import chess.Direction;
import chess.Pos;

public class TestHansPos {

	@Test
	public void test1() {
		Assert.assertEquals(A1,Pos.get("A1"));
		Assert.assertEquals(B1,Pos.get("b1"));
		Assert.assertTrue(A1==Pos.get("A1"));
		Assert.assertTrue(B1==Pos.get("b1"));
	}

	@Test
	public void test2() {
		Assert.assertEquals(A2,A1.add(Direction.UP));
		Assert.assertEquals(B2,A1.add(Direction.RIGHT_UP));
		Assert.assertEquals(B1,A1.add(Direction.RIGHT));
	}

	@Test
	public void test3() {
		Assert.assertEquals(7,A1.directedPositions.get(Direction.UP).length);
		Assert.assertEquals(0,A1.directedPositions.get(Direction.DOWN).length);
	}

	@Test
	public void test4() {
		Assert.assertEquals(1,B2.directedPositions.get(Direction.DOWN).length);
		Assert.assertEquals(1,B2.directedPositions.get(Direction.LEFT).length);
		Assert.assertEquals(6,B2.directedPositions.get(Direction.UP).length);
		Assert.assertEquals(6,B2.directedPositions.get(Direction.RIGHT).length);
	}

	@Test
	public void test5() {
		Assert.assertEquals(2,C3.directedPositions.get(Direction.LEFT_DOWN).length);
		Assert.assertEquals(5,C3.directedPositions.get(Direction.RIGHT_UP).length);
		Assert.assertEquals(2,C3.directedPositions.get(Direction.LEFT_UP).length);
		Assert.assertEquals(2,C3.directedPositions.get(Direction.RIGHT_DOWN).length);
	}

	@Test
	public void test6() {
		Assert.assertEquals(2,A1.horseMoves.size());
		Assert.assertEquals(8,D4.horseMoves.size());
	}

}
