package models;

public class Tile {
	
	private int value = 0;
	
	public Tile() { 
		
	}
	
	public Tile(int value) {
		this.value = value;
	}
	
	public Tile(Tile tile) {
		this.value = tile.value;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	public boolean isZero() {
		if (value == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * Merge tile with other tile.  
	 * @param otherTile
	 * @return int of new value of the tile
	 */
	public int mergeWith(Tile otherTile) {
		value += otherTile.getValue();
		return value;
	}
	
	/**
	 * Reset tile parameters and it's value to zero
	 */
	public void reset() {
		value = 0;
	}
	
	public String toString() {
		return ""+value;
	}
}
