package rules;

import constants.Constants.Direction;
import models.Coordinates;
import models.GameField;

/**
 * 
 * Interface governing game rules
 *
 */
public interface Rules {
	/**
	 * Moves all tiles in the game field to one specified direction.
	 * @param field game field
	 * @param direction
	 */
	public void moveTilesByDirection(GameField field, Direction direction);
	
	/**
	 * Check if the game is lost or can continue.
	 * @param field game field
	 * @return boolean, true if the game can continue, false if it is lost
	 */
	public boolean isLost(GameField field);
	
	/**
	 * Get the current score value
	 * @param field game field
	 * @return int score
	 */
	public int getScore(GameField field);

	/**
	 * Finds where tile can be moved by chosen direction.
	 * @param coordinates coordinates of the tile to be moved
	 * @param direction direction of the movement
	 * @return Coordinates of the ending point of the tile movement
	 */
	public Coordinates getTargetMovement(Coordinates coordinates, Direction direction);
}
