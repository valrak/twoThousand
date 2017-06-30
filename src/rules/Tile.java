package rules;

public class Tile {
	
	private int value = 0;
	
	public Tile() { 
		
	}
	
	public Tile(int value) {
		this.value = value;
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
	
	
}
