package rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import constants.Constants.Direction;
import models.Coordinates;
import models.GameField;
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
	public void testGetTargetMovementOneTile() {
		GameField field = new GameField(4, 4);
		field.getTile(2, 2).setValue(2);
		Rules rules = new RulesStandard();
		rules.setField(field);
		rules.moveTilesByDirection(Direction.UP);
		assertEquals(2, field.getTile(2, 0).getValue());
		rules.moveTilesByDirection(Direction.DOWN);
		assertEquals(2, field.getTile(2, 3).getValue());
		rules.moveTilesByDirection(Direction.LEFT);
		assertEquals(2, field.getTile(0, 3).getValue());
		rules.moveTilesByDirection(Direction.RIGHT);
		assertEquals(2, field.getTile(3, 3).getValue());
	}
	
	@Test
	public void testMultipleMergingSituation() {
		GameField field = new GameField(4, 4);
		field.getTile(0, 0).setValue(2);
		field.getTile(0, 1).setValue(2);
		field.getTile(0, 2).setValue(2);
		field.getTile(0, 3).setValue(2);
		Rules rules = new RulesStandard();
		rules.setField(field);
		rules.moveTilesByDirection(Direction.UP);
		assertEquals(4, field.getTile(0, 0).getValue());
		assertEquals(4, field.getTile(0, 1).getValue());
		assertEquals(0, field.getTile(0, 2).getValue());
		assertEquals(0, field.getTile(0, 3).getValue());
		rules.moveTilesByDirection(Direction.UP);
		assertEquals(8, field.getTile(0, 0).getValue());
		assertEquals(0, field.getTile(0, 1).getValue());
	}
	
	@Test
	public void testGapMergingSituation() {
		GameField field = new GameField(4, 4);
		field.getTile(0, 0).setValue(2);
		field.getTile(0, 1).setValue(0);
		field.getTile(0, 2).setValue(2);
		field.getTile(0, 3).setValue(2);
		Rules rules = new RulesStandard();
		rules.setField(field);
		rules.moveTilesByDirection(Direction.UP);
		assertEquals(4, field.getTile(0, 0).getValue());
		assertEquals(2, field.getTile(0, 1).getValue());
		assertEquals(0, field.getTile(0, 2).getValue());
		assertEquals(0, field.getTile(0, 3).getValue());
	}
	
	@Test
	public void testMovementWithGap() {
		GameField field = new GameField(4, 4);
		field.getTile(0, 0).setValue(8);
		field.getTile(0, 1).setValue(2);
		field.getTile(0, 2).setValue(0);
		field.getTile(0, 3).setValue(4);
		Rules rules = new RulesStandard();
		rules.setField(field);
		rules.moveTilesByDirection(Direction.DOWN);
		assertEquals(0, field.getTile(0, 0).getValue());
		assertEquals(8, field.getTile(0, 1).getValue());
		assertEquals(2, field.getTile(0, 2).getValue());
		assertEquals(4, field.getTile(0, 3).getValue());
	}

	
	@Test
	public void testContinuedMovementMerging() {
		GameField field = new GameField(4, 4);
		field.getTile(2, 3).setValue(2);
		field.getTile(0, 3).setValue(2);
		Rules rules = new RulesStandard();
		rules.setField(field);
		rules.moveTilesByDirection(Direction.RIGHT);
		assertEquals(0, field.getTile(0, 3).getValue());
		assertEquals(0, field.getTile(1, 3).getValue());
		assertEquals(0, field.getTile(2, 3).getValue());
		assertEquals(4, field.getTile(3, 3).getValue());
	}

	
	
	@Test
	public void testGetTargetMovementNoMovement() {
		// there is no place to move
		GameField field = prepareField();
		Rules rules = new RulesStandard();
		rules.setField(field);
		
		Coordinates targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.UP);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.DOWN);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.LEFT);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.RIGHT);
		assertEquals(new Coordinates(2, 2), targetMovement);
	}
	
	@Test
	public void testGetTargetMovementEmptySpace() {
		GameField field = prepareField();
		field.getTile(2, 2).reset();
		field.getTile(1, 2).reset();
		Rules rules = new RulesStandard();
		rules.setField(field);
		Coordinates targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.UP);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.DOWN);
		assertEquals(new Coordinates(2, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(3, 2), Direction.LEFT);
		assertEquals(new Coordinates(1, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(0, 2), Direction.RIGHT);
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
		rules.setField(field);
		Coordinates targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.UP);
		assertEquals(new Coordinates(2, 0), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.DOWN);
		assertEquals(new Coordinates(2, 3), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(2, 2), Direction.LEFT);
		assertEquals(new Coordinates(1, 2), targetMovement);
		targetMovement = rules.getTargetMovement(new Coordinates(1, 2), Direction.RIGHT);
		assertEquals(new Coordinates(2, 2), targetMovement);
	}
	
	@Test
	public void testGenerateTile() {
		GameField field = new GameField(4, 5);
		Rules rules = new RulesStandard();
		rules.setField(field);
		Coordinates tileCoordinates = rules.generateTile();
		assertEquals(2, field.getTile(tileCoordinates).getValue());
	}
	
	
	@Test
	public void testGetScore() {
		GameField field = new GameField(4, 5);
		Rules rules = new RulesStandard();
		rules.setField(field);
		int score = rules.getScore();
		assertEquals(0, score);
		rules.generateTile();
		score = rules.getScore();
		assertEquals(2, score);
		field = prepareField();
		rules.setField(field);
		score = rules.getScore();
		assertEquals(210, score);
	}
	
	@Test
	public void testIsLost() {
		GameField field = new GameField(4, 5);
		Rules rules = new RulesStandard();
		rules.setField(field);
		assertFalse(rules.isLost());
		field = prepareField();
		rules.setField(field);
		assertTrue(rules.isLost());
		field = prepareField();
		rules.setField(field);
		assertTrue(rules.isLost());
		field.getTile(0, 0).setValue(2);
		field.getTile(1, 0).setValue(2);
		rules.setField(field);
		assertFalse(rules.isLost());	
	}
}

