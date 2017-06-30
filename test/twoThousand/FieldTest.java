package twoThousand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import rules.Coordinates;
import rules.Field;
import rules.Tile;

public class FieldTest {

	@Test
	public void testGetTileFromField() {
		Field field = new Field(4, 4);
		assertEquals(0, field.getTile(0, 0).getValue());
		// TODO: null object pattern
		assertEquals(null, field.getTile(5, 0));
	}
	
	@Test
	public void testRandomTileInField() {
		Field field = new Field(4, 4);
		Coordinates coords = field.putInFieldAtRandom(2);
		assertEquals(2, field.getTile(coords).getValue());
	}
	
	@Test
	public void testGetRandomZeroCoord() {
		Field field = new Field(4, 5);
		Coordinates randomZeroTile = field.getRandomZeroCoord();
		assertEquals(0, field.getTile(randomZeroTile).getValue());
		
		// fill whole play field with non zero tiles, except one
		// test if the random function get that one
		int excX = 2;
		int excY = 1;
		for (int y = 0; y < field.getMaxY(); y++) {
			for (int x = 0; x < field.getMaxX(); x++) {
				if (x != excX || y != excY) {
					field.setTile(x, y, new Tile(2));
				}
			}
		}
		
		Coordinates emptyTileCoord = field.getRandomZeroCoord();
		assertEquals(0, field.getTile(emptyTileCoord).getValue());
		assertEquals(excX, emptyTileCoord.getX());
		assertEquals(excY, emptyTileCoord.getY());
		// fill also the last one, result should be null
		field.setTile(excX, excY, new Tile(2));
		emptyTileCoord = field.getRandomZeroCoord();
		assertNull(emptyTileCoord);
	}

}
