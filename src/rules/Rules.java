package rules;

import constants.Constants.Direction;
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

}
