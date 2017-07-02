package twoThousand;

import static org.junit.Assert.*;

import org.junit.Test;

import rules.Tile;

public class TileTest {

	@Test
	public void isZeroTest() {
		Tile t = new Tile();
		assertTrue(t.isZero());
		t.setValue(2);
		assertFalse(t.isZero());
	}

}
