package rules;

public class GameParameters {

	private int gameFieldSizeX = 0;
	private int gameFieldSizeY = 0;

	private int victoryNumber = 0;

	public GameParameters(int gameFieldSizeX, int gameFieldSizeY) {
		this.gameFieldSizeX = gameFieldSizeX;
		this.gameFieldSizeY = gameFieldSizeY;
	}

	public int getGameFieldSizeX() {
		return gameFieldSizeX;
	}

	public void setGameFieldSizeX(int gameFieldSizeX) {
		this.gameFieldSizeX = gameFieldSizeX;
	}

	public int getGameFieldSizeY() {
		return gameFieldSizeY;
	}

	public void setGameFieldSizeY(int gameFieldSizeY) {
		this.gameFieldSizeY = gameFieldSizeY;
	}

	public int getVictoryNumber() {
		return victoryNumber;
	}

	public void setVictoryNumber(int victoryNumber) {
		this.victoryNumber = victoryNumber;
	}

}
