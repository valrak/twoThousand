package twoThousand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.LinkedHashMap;

import org.junit.Test;

import constants.Constants.Direction;
import models.Coordinates;
import models.GameField;
import models.Tile;

public class GameFieldTest {

	@Test
	public void testGetTileFromField() {
		GameField field = new GameField(4, 2);
		assertEquals(0, field.getTile(0, 0).getValue());
		assertEquals(null, field.getTile(5, 0));
	}
	
	@Test
	public void testRandomTileInField() {
		GameField field = new GameField(4, 6);
		Coordinates coords = field.putInFieldAtRandom(2);
		assertEquals(2, field.getTile(coords).getValue());
	}
	
	@Test
	public void testGetRandomZeroCoord() {
		GameField field = new GameField(4, 5);
		Coordinates randomZeroTile = field.getRandomZeroCoord();
		assertEquals(0, field.getTile(randomZeroTile).getValue());
		
		// fill whole play field with non zero tiles, except one
		// test if the random function get that one
		int excX = 2;
		int excY = 1;
		for (int y = 0; y < field.getMaxY(); y++) {
			for (int x = 0; x < field.getMaxX(); x++) {
				if (x != excX || y != excY) {
					field.getTile(x, y).setValue(2);
				}
			}
		}
		
		Coordinates emptyTileCoord = field.getRandomZeroCoord();
		assertEquals(0, field.getTile(emptyTileCoord).getValue());
		assertEquals(excX, emptyTileCoord.getX());
		assertEquals(excY, emptyTileCoord.getY());
		// fill also the last one, result should be null
		field.getTile(excX, excY).setValue(2);
		emptyTileCoord = field.getRandomZeroCoord();
		assertNull(emptyTileCoord);
	}
	
	private GameField prepareField() {
		GameField field = new GameField(4, 5);
		int tileNumber = 1;
		for (int y = 0; y < field.getMaxY(); y++) {
			for (int x = 0; x < field.getMaxX(); x++) {
				field.getTile(x, y).setValue(tileNumber);
				tileNumber += 1;
			}
		}
		return field;
	}
	
	@Test
	public void testGetTiles() {
		GameField field = prepareField();
		LinkedHashMap<Coordinates, Tile> tiles = field.getTiles();
		assertEquals(1, tiles.get(new Coordinates(0, 0)).getValue());
		assertEquals(20, tiles.get(new Coordinates(3, 4)).getValue());
	}

}
