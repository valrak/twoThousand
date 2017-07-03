package twoThousand;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import constants.Constants.Direction;
import models.Coordinates;
import models.GameField;
import models.Tile;
import rules.Rules;
import rules.RulesStandard;

public class RulesStandardTest {
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
	public void testGetTargetMovementNoMovement() {
		// there is no place to move
		GameField field = prepareField();
		Rules rules = new RulesStandard();
		
		Coordinates targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.UP, field);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.DOWN, field);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.LEFT, field);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.RIGHT, field);
		assertEquals(new Coordinates(2, 2), targetMovement);
	}
	
	@Test
	public void testGetTargetMovementEmptySpace() {
		GameField field = prepareField();
		field.getTile(2, 2).reset();
		field.getTile(1, 2).reset();
		Rules rules = new RulesStandard();
		Coordinates targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.UP, field);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.DOWN, field);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(3, 2), Direction.LEFT, field);
		assertEquals(new Coordinates(1, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(0, 2), Direction.RIGHT, field);
		assertEquals(new Coordinates(2, 2), targetMovement);
	}
	
	@Test
	public void testGetTargetMovementMerging() {
		GameField field = prepareField();
		Rules rules = new RulesStandard();
		field.getTile(2, 2).setValue(64);
		field.getTile(1, 2).setValue(64);
		field.getTile(2, 1).reset();
		field.getTile(2, 0).setValue(64);
		field.getTile(2, 3).setValue(64);
		Coordinates targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.UP, field);
		assertEquals(new Coordinates(2, 0), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.DOWN, field);
		assertEquals(new Coordinates(2, 3), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.LEFT, field);
		assertEquals(new Coordinates(1, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(1, 2), Direction.RIGHT, field);
		assertEquals(new Coordinates(2, 2), targetMovement);
	}
}
