package models;

import static constants.Constants.*;

public class Coordinates {
	
	private int x;
	private int y;
	
	public Coordinates() {
		
	}
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
		
	public Coordinates(int[] coords) {
		this.x = coords[X];
		this.y = coords[Y];
	}
	
	public int[] getArray() {
		int[] coords = new int[2];
		coords[X] = x;
		coords[Y] = y;
		return coords;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void setCoordinates(int[] coord) {
		x = coord[X];
		y = coord[Y];
	}
	
	public boolean equals(Object other) {
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		else {
			Coordinates otherCoord = (Coordinates) other;
			if (otherCoord.getX() == x && otherCoord.getY() == y) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return "Coordinates x="+x+" y="+y;
	}
}
