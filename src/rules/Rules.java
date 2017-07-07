package rules;

import constants.Constants.Direction;
import models.Coordinates;
import models.GameField;

/**
 * 
 * Interface governing game rules
 *
 */
public abstract class Rules {
	
	GameField field;
	
	/**
	 * Moves all tiles in the game field to one specified direction.
	 * @param field game field
	 * @param direction
	 * @return how many tile moves were made on board
	 */
	abstract int moveTilesByDirection(Direction direction);
	
	/**
	 * Check if the game is lost or can continue.
	 * @param field game field
	 * @return boolean, true if the game can continue, false if it is lost
	 */
	abstract boolean isLost();
	
	/**
	 * Get the current score value
	 * @param field game field
	 * @return int score
	 */
	abstract int getScore();

	/**
	 * Finds where tile can be moved by chosen direction.
	 * @param coordinates coordinates of the tile to be moved
	 * @param direction direction of the movement
	 * @return Coordinates of the ending point of the tile movement
	 */
	abstract Coordinates getTargetMovement(Coordinates coordinates, Direction direction);
	
	/**
	 * Create new tile at random location in play field
	 * @return Coordinates of newly created tile
	 */
	abstract Coordinates generateTile();

	/**
	 * Prepare new game field
	 */
	public abstract void newGame();
	
	/**
	 * Player made move call
	 * @param direction
	 */
	public abstract void playerMove(Direction direction);
	
	public GameField getField() {
		return field;
	}

	public void setField(GameField field) {
		this.field = field;
	}
}
