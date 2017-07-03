package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import constants.Constants.Direction;

public class GameField {

	private Tile[][] field;
	
	private int maxX = 0;
	private int maxY = 0;
	
	private enum MovementDecision {
		CONTINUE, STOP, MERGE 
	}

	/**
	 * Create playfield with these dimensions
	 * @param maxX x axis dimension 
	 * @param maxY y axis dimension
	 */
	public GameField(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		field = new Tile[maxY][maxX];
		clearField();
	}

	/**
	 * Clear the playfield - all Tiles will be at default value
	 */
	public void clearField() {
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				field[y][x] = new Tile();
			}
		}
	}

	public Tile getTile(int x, int y) {
		try {
			return field[y][x];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Tile getTile(Coordinates coord) {
		try {
			return field[coord.getY()][coord.getX()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean setTile(Coordinates coord, Tile tile) {
		try {
			field[coord.getY()][coord.getX()] = tile;
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public boolean setTile(int x, int y, Tile tile) {
		try {
			field[y][x] = tile;
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * Place new Tile to the playfield at random empty space
	 * @param value of the new tile
	 * @return Coordinates where the new tile was placed
	 */
	public Coordinates putInFieldAtRandom(int value) {
		Coordinates coordinates = getRandomZeroCoord();
		if (coordinates != null) {
			field[coordinates.getY()][coordinates.getX()].setValue(value);
			return coordinates;
		}
		return null;
	}

	/**
	 * Returns empty tile on the playfield by random
	 * @return Coordinates of the empty tile (it's value = zero)
	 */
	public Coordinates getRandomZeroCoord() {
		ArrayList<Coordinates> coordsList = new ArrayList<>();
		LinkedHashMap<Coordinates, Tile> tiles = getTiles();
		for (Coordinates coord : tiles.keySet()) {
			Tile tile = tiles.get(coord);
			if (tile.isZero()) {
				coordsList.add(coord);
			}
		}
		if (coordsList.isEmpty()) {
			return null;
		}
		Random random = new Random();
		Coordinates randomKey = coordsList.get(random.nextInt(coordsList.size()));
		return randomKey;
	}

	private MovementDecision doStopMovement(Coordinates targetCoord, int currentTileValue) {
		Tile targetTile = getTile(targetCoord);
		// empty space, continue with search
		if (targetTile == null || targetTile.getValue() == 0) {
			return MovementDecision.CONTINUE;
		}
		else {
			// if both tiles are of same value, they will merge together
			if (targetTile.getValue() == currentTileValue) {
				return MovementDecision.MERGE;
			}
			// tile is occupied with something else
			return MovementDecision.STOP;
		}
	}
	
	/**
	 * Finds where tile can be moved by chosen direction.
	 * @param coordinates coordinates of the tile to be moved
	 * @param direction direction of the movement
	 * @return Coordinates of the ending point of the tile movement
	 */
	public Coordinates getTargetMovement(Coordinates coordinates, Direction direction) {
		Tile currentTile = getTile(coordinates);
		Coordinates potentialTarget = new Coordinates(coordinates.getArray());
		switch (direction) {
		case UP:
			for (int i = coordinates.getY() - 1; i >= 0; i--) {
				Coordinates targetCoord = new Coordinates(coordinates.getX(), i); 
				MovementDecision decision = doStopMovement(targetCoord, currentTile.getValue());
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
			for (int i = coordinates.getY() + 1; i <= maxY; i++) {
				Coordinates targetCoord = new Coordinates(coordinates.getX(), i); 
				MovementDecision decision = doStopMovement(targetCoord, currentTile.getValue());
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
				MovementDecision decision = doStopMovement(targetCoord, currentTile.getValue());
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
			for (int i = coordinates.getX() + 1; i <= maxX; i++) {
				Coordinates targetCoord = new Coordinates(i, coordinates.getY()); 
				MovementDecision decision = doStopMovement(targetCoord, currentTile.getValue());
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
	
	public void moveTilesByDirection(Direction direction) {
		LinkedHashMap<Coordinates, Tile> tiles = getTiles();
		for (Coordinates coord : tiles.keySet()) {
			Tile tile = tiles.get(coord);
			if (!tile.isZero()) {
				Coordinates targetCoord = getTargetMovement(coord, direction);
				Tile targetTile = getTile(targetCoord);
				if (targetTile != null && targetTile.getValue() == tile.getValue()) {
					targetTile.mergeWith(tile);
					tile.reset();
				}
			}
		}
	}
	
	public LinkedHashMap<Coordinates, Tile> getTiles() {
		LinkedHashMap<Coordinates, Tile> mapOfTiles = new LinkedHashMap<>();
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				Tile tile = field[y][x];
				Coordinates coord = new Coordinates(x, y);
				mapOfTiles.put(coord, tile);
			}
		}
		return mapOfTiles;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}
}
