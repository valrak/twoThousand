package rules;

import java.util.LinkedHashMap;

import constants.Constants.Direction;
import models.Coordinates;
import models.GameField;
import models.Tile;

public class RulesStandard implements Rules {

	private int INITIAL_VALUE = 2;
	
	private enum MovementDecision {
		CONTINUE, STOP, MERGE 
	}
	
	@Override
	public void moveTilesByDirection(GameField field, Direction direction) {
		LinkedHashMap<Coordinates, Tile> tiles = field.getTiles();
		for (Coordinates coord : tiles.keySet()) {
			Tile tile = tiles.get(coord);
			if (!tile.isZero()) {
				Coordinates targetCoord = getTargetMovement(coord, direction, field);
				Tile targetTile = field.getTile(targetCoord);
				if (targetTile != null && targetCoord != coord) {
					// merging
					if (targetTile.getValue() == tile.getValue()) {
						targetTile.mergeWith(tile);
						tile.reset();
					}
					// movement
					else {
						targetTile.setValue(tile.getValue());
						tile.reset();
					}
				}
			}
		}
	}

	@Override
	public boolean isLost(GameField field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getScore(GameField field) {
		int score = 0;
		LinkedHashMap<Coordinates, Tile> tiles = field.getTiles();
		for (Coordinates key : tiles.keySet()) {
			Tile tile = tiles.get(key);
			score += tile.getValue();
		}
		return score;
	}

	private MovementDecision doStopMovement(Coordinates targetCoord, Tile targetTile, Tile currentTile) {
		// empty space, continue with search
		if (targetTile == null || targetTile.getValue() == 0) {
			return MovementDecision.CONTINUE;
		}
		else {
			// if both tiles are of same value, they will merge together
			if (targetTile.getValue() == currentTile.getValue()) {
				return MovementDecision.MERGE;
			}
			// tile is occupied with something else
			return MovementDecision.STOP;
		}
	}
	
	@Override
	public Coordinates getTargetMovement(Coordinates coordinates, Direction direction, GameField field) {
		Tile currentTile = field.getTile(coordinates);
		Coordinates potentialTarget = new Coordinates(coordinates.getArray());
		switch (direction) {
		case UP:
			for (int i = coordinates.getY() - 1; i >= 0; i--) {
				Coordinates targetCoord = new Coordinates(coordinates.getX(), i); 
				MovementDecision decision = doStopMovement(targetCoord, field.getTile(targetCoord), currentTile);
				if (decision == MovementDecision.CONTINUE) {
					potentialTarget = targetCoord;
				}
				else if (decision == MovementDecision.MERGE) {
					potentialTarget = targetCoord;
					return potentialTarget;
				}
				else {
					return potentialTarget;
				}
			}
			return potentialTarget;
		case DOWN:
			for (int i = coordinates.getY() + 1; i <= field.getMaxY(); i++) {
				Coordinates targetCoord = new Coordinates(coordinates.getX(), i); 
				MovementDecision decision = doStopMovement(targetCoord, field.getTile(targetCoord), currentTile);
				if (decision == MovementDecision.CONTINUE) {
					potentialTarget = targetCoord;
				}
				else if (decision == MovementDecision.MERGE) {
					potentialTarget = targetCoord;
					return potentialTarget;
				}
				else {
					return potentialTarget;
				}
			}
			return potentialTarget;
		case LEFT:
			for (int i = coordinates.getX() - 1; i >= 0; i--) {
				Coordinates targetCoord = new Coordinates(i, coordinates.getY()); 
				MovementDecision decision = doStopMovement(targetCoord, field.getTile(targetCoord), currentTile);
				if (decision == MovementDecision.CONTINUE) {
					potentialTarget = targetCoord;
				}
				else if (decision == MovementDecision.MERGE) {
					potentialTarget = targetCoord;
					return potentialTarget;
				}
				else {
					return potentialTarget;
				}
			}
			return potentialTarget;
		case RIGHT:
			for (int i = coordinates.getX() + 1; i <= field.getMaxX(); i++) {
				Coordinates targetCoord = new Coordinates(i, coordinates.getY()); 
				MovementDecision decision = doStopMovement(targetCoord, field.getTile(targetCoord), currentTile);
				if (decision == MovementDecision.CONTINUE) {
					potentialTarget = targetCoord;
				}
				else if (decision == MovementDecision.MERGE) {
					potentialTarget = targetCoord;
					return potentialTarget;
				}
				else {
					return potentialTarget;
				}
			}
			return potentialTarget;
		}
		return null;
	}

	@Override
	public Coordinates generateTile(GameField field) {
		Coordinates coordinates = field.getRandomZeroCoord();
		if (coordinates != null) {
			field.getTile(coordinates).setValue(INITIAL_VALUE);
			return coordinates;
		}
		return null;
	}

}
