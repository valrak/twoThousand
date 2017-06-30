package rules;

import static constants.Constants.*;

import java.util.ArrayList;
import java.util.Random;

import constants.Constants.Direction;

public class Field {

	private Tile[][] field;

	private int maxX = 0;
	private int maxY = 0;

	public Field(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		field = new Tile[maxX][maxY];
		clearField();
	}

	public void clearField() {
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				field[x][y] = new Tile();
			}
		}
	}

	public Tile getTile(int x, int y) {
		try {
			return field[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Tile getTile(Coordinates coord) {
		try {
			return field[coord.getX()][coord.getY()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean setTile(Coordinates coord, Tile tile) {
		try {
			field[coord.getX()][coord.getY()] = tile;
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}
	
	public boolean setTile(int x, int y, Tile tile) {
		try {
			field[x][y] = tile;
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	// inserts random tile at field
	// TODO: should do only at empty fields!
	public Coordinates putInFieldAtRandom(int value) {
		Coordinates coordinates = getRandomZeroCoord();
		if (coordinates != null) {
			field[coordinates.getX()][coordinates.getY()].setValue(value);
			return coordinates;
		}
		return null;
	}

	public Coordinates getRandomZeroCoord() {
		ArrayList<Coordinates> coordsList = new ArrayList<>();
		for (int y = 0; y < maxY; y++) {
			for (int x = 0; x < maxX; x++) {
				Tile tile = field[x][y];
				if (tile.isZero()) {
					Coordinates coord = new Coordinates(x, y);
					coordsList.add(coord);
				}
			}
		}
		if (coordsList.isEmpty()) {
			return null;
		}
		Random random = new Random();
		Coordinates randomKey = coordsList.get(random.nextInt(coordsList.size()));
		return randomKey;
	}

	// move tile to direction, movement ending when collision with other tile or
	// boundary
	public Coordinates moveTile(int[] coord, Direction direction) {
		Coordinates coordinates = new Coordinates();
		int[] potentialTarget = coord;
		switch (direction) {
		case UP:
			for (int i = coord[Y]; i <= 0; i--) {
				 
				Tile targetSpace = getTile(coord[X], i);
				// empty space
				if (targetSpace == null) {
					potentialTarget[X] = coord[X];
					potentialTarget[Y] = i;
				}
			}
			break;
		case DOWN:
			break;
		case LEFT:
			break;
		case RIGHT:
			break;
		default:
			break;

		}
		return coordinates;
	}

	public int getMaxX() {
		return maxX;
	}

	public int getMaxY() {
		return maxY;
	}
}
