package rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import constants.Constants.Direction;
import models.Coordinates;
import models.GameField;
import models.Tile;

public class RulesStandard extends Rules {

	private int INITIAL_VALUE = 2;
	
	/**
	 * Standard game of 2048 consists of 4x4 dimension game field.
	 */
	public RulesStandard() {
		field = new GameField(4, 4);
		newGame();
	}
	
	private enum MovementDecision {
		CONTINUE, STOP, MERGE 
	}
	
	LinkedHashMap<Coordinates, Tile> directionalField(Direction direction) {
		LinkedHashMap<Coordinates, Tile> mapOfTiles = new LinkedHashMap<>();
		switch (direction) {
		case UP:
			return field.getTiles();
		case DOWN:
			for (int y = field.getMaxY()-1; y >= 0; y--) {
				for (int x = 0; x < field.getMaxX(); x++) {
					Tile tile = field.getTile(x, y);
					Coordinates coord = new Coordinates(x, y);
					mapOfTiles.put(coord, tile);
				}
			}
			return mapOfTiles;
		case LEFT:
			return field.getTiles();
		case RIGHT:
			for (int y = 0; y < field.getMaxY(); y++) {
				for (int x = field.getMaxX()-1; x >= 0; x--) {
					Tile tile = field.getTile(x, y);
					Coordinates coord = new Coordinates(x, y);
					mapOfTiles.put(coord, tile);
				}
			}
			return mapOfTiles;
		}
		return mapOfTiles;
	}
	
	int moveTilesByDirection(Direction direction) {
		int movesCounter = 0;
		LinkedHashMap<Coordinates, Tile> tiles = directionalField(direction);
		ArrayList<Coordinates> alreadyMovedTiles = new ArrayList<>();
		for (Coordinates coord : tiles.keySet()) {
			Tile tile = tiles.get(coord);
			if (!tile.isZero() && !alreadyMovedTiles.contains(coord)) {
				Coordinates targetCoord = getTargetMovement(coord, direction);
				Tile targetTile = field.getTile(targetCoord);
				if (targetTile != null && !targetCoord.equals(coord)) {
					// merging
					if (targetTile.getValue() == tile.getValue()) {
						targetTile.mergeWith(tile);
						tile.reset();
						movesCounter += 1;
						// continue movement after merging
						Coordinates afterMergingTargetCoord = getTargetMovement(targetCoord, direction);
						Tile afterMergingTargetTile = field.getTile(afterMergingTargetCoord);
						// do not allow other merging
						if (!(targetTile.getValue() == afterMergingTargetTile.getValue()) && 
								afterMergingTargetTile != null &&
								!afterMergingTargetCoord.equals(targetCoord)) {
							afterMergingTargetTile.setValue(targetTile.getValue());
							alreadyMovedTiles.add(afterMergingTargetCoord);
							targetTile.reset();
						}
						else {
							alreadyMovedTiles.add(targetCoord);
						}
					}
					// movement
					else {
						targetTile.setValue(tile.getValue());
						alreadyMovedTiles.add(targetCoord);
						tile.reset();
						movesCounter += 1;
					}
				}
			}
		}
		return movesCounter;
	}

	boolean isLost() {
		LinkedHashMap<Coordinates, Tile> tiles = field.getTiles();
		for (Coordinates key : tiles.keySet()) {
			if (tiles.get(key).isZero()) {
				return false;
			}
		}
		// field full, check if there are valid moves
		for (Direction direction : Direction.values()) {
			GameField mockField = new GameField(field);
			try {
				Rules mockRules = this.getClass().newInstance();
				mockRules.setField(mockField);
				if (mockRules.moveTilesByDirection(direction) > 0) {
					return false;
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				System.err.print("Rules class does not have default empty constructor!");
				e.printStackTrace();
			}
		}
		return true;
	}

	int getScore() {
		int score = 0;
		LinkedHashMap<Coordinates, Tile> tiles = field.getTiles();
		for (Coordinates key : tiles.keySet()) {
			Tile tile = tiles.get(key);
			score += tile.getValue();
		}
		return score;
	}

	private MovementDecision doStopMovement(Coordinates targetCoord, Tile targetTile, Tile currentTile) {
		// end of playfield
		if (targetTile == null) {
			return MovementDecision.STOP;
		}
		// if both tiles are of same value, they will merge together
		if (targetTile.getValue() == currentTile.getValue()) {
			return MovementDecision.MERGE;
		}
		// empty space, continue with search
		if (targetTile.getValue() == 0) {
			return MovementDecision.CONTINUE;
		}
		// tile is occupied with something else
		return MovementDecision.STOP;
	}
	
	Coordinates getTargetMovement(Coordinates coordinates, Direction direction) {
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

	Coordinates generateTile() {
		Coordinates coordinates = field.getRandomZeroCoord();
		if (coordinates != null) {
			field.getTile(coordinates).setValue(INITIAL_VALUE);
			return coordinates;
		}
		return null;
	}

	public void newGame() {
		LinkedHashMap<Coordinates, Tile> tiles = field.getTiles();
		for (Coordinates key : tiles.keySet()) {
			Tile tile = tiles.get(key);
			tile.reset();
		}
		generateTile();
	}

	public void playerMove(Direction direction) {
		int moves = moveTilesByDirection(direction);
		if (moves != 0) {
			generateTile();
		}
	}
}
