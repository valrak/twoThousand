package twoThousand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import constants.Constants.Direction;
import models.Coordinates;
import models.GameField;
import models.Tile;

public class FieldTest {

	@Test
	public void testGetTileFromField() {
		GameField field = new GameField(4, 2);
		assertEquals(0, field.getTile(0, 0).getValue());
		// TODO: null object pattern
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
	
	private GameField prepareField() {
		GameField field = new GameField(4, 5);
		int tileNumber = 1;
		for (int y = 0; y < field.getMaxY(); y++) {
			for (int x = 0; x < field.getMaxX(); x++) {
				field.setTile(x, y, new Tile(tileNumber));
				tileNumber += 1;
			}
		}
		return field;
	}
	@Test
	public void testGetTargetMovementNoMovement() {
		// there is no place to move
		GameField field = prepareField();
		Coordinates targetMovement = field.getTargetMovement(new Coordinates(2, 2), Direction.UP);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = field.getTargetMovement(new Coordinates(2, 2), Direction.DOWN);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = field.getTargetMovement(new Coordinates(2, 2), Direction.LEFT);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = field.getTargetMovement(new Coordinates(2, 2), Direction.RIGHT);
		assertEquals(new Coordinates(2, 2), targetMovement);
	}
	
	@Test
	public void testGetTargetMovementEmptySpace() {
		GameField field = prepareField();
		field.getTile(2, 2).reset();
		field.getTile(1, 2).reset();
		Coordinates targetMovement = field.getTargetMovement(new Coordinates(2, 2), Direction.UP);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = field.getTargetMovement(new Coordinates(2, 2), Direction.DOWN);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = field.getTargetMovement(new Coordinates(3, 2), Direction.LEFT);
		assertEquals(new Coordinates(1, 2), targetMovement);
		targetMovement = field.getTargetMovement(new Coordinates(0, 2), Direction.RIGHT);
		assertEquals(new Coordinates(2, 2), targetMovement);
	}
	
	@Test
	public void testGetTargetMovementMerging() {
		GameField field = prepareField();
		field.getTile(2, 2).setValue(64);
		field.getTile(1, 2).setValue(64);
		field.getTile(2, 1).reset();
		field.getTile(2, 0).setValue(64);
		field.getTile(2, 3).setValue(64);
		Coordinates targetMovement = field.getTargetMovement(new Coordinates(2, 2), Direction.UP);
		assertEquals(new Coordinates(2, 0), targetMovement);
		targetMovement = field.getTargetMovement(new Coordinates(2, 2), Direction.DOWN);
		assertEquals(new Coordinates(2, 3), targetMovement);
		targetMovement = field.getTargetMovement(new Coordinates(2, 2), Direction.LEFT);
		assertEquals(new Coordinates(1, 2), targetMovement);
		targetMovement = field.getTargetMovement(new Coordinates(1, 2), Direction.RIGHT);
		assertEquals(new Coordinates(2, 2), targetMovement);
	}

}
