package twoThousand;

import static org.junit.Assert.*;

import org.junit.Test;

import models.Tile;

public class TileTest {

	@Test
	public void isZeroTest() {
		Tile t = new Tile();
		assertTrue(t.isZero());
		t.setValue(2);
		assertFalse(t.isZero());
	}
	
	@Test
	public void mergeTest() {
		Tile t = new Tile();
		t.setValue(2);
		Tile otherTile = new Tile();
		otherTile.setValue(2);
		t.mergeWith(otherTile);
		assertEquals(4, t.getValue());
		assertEquals(2, otherTile.getValue());
	}

}
