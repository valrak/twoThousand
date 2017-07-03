package models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class GameField {

	private Tile[][] field;
	private LinkedHashMap<Coordinates, Tile> tiles = new LinkedHashMap<>();
	
	private int maxX = 0;
	private int maxY = 0;

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
		tiles = populateTiles();
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
	
	/**
	 * Iterate through field and return tile references in linked hashmap
	 * @return LinkedHashMap<Coordinates, Tile>
	 */
	private LinkedHashMap<Coordinates, Tile> populateTiles() {
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
	
	public LinkedHashMap<Coordinates, Tile> getTiles() {
		return tiles;
	}
}
