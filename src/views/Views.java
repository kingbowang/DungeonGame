package views;

import java.util.Map;
import java.util.Set;
import navigation.Direction;
import navigation.Position;

/**
 * Implement Views interface that contains important methods for showing the view.
 *
 * @author Pengbo Wang
 */
public interface Views {

  /**
   * Set the starting position and ending position of the player.
   *
   * @param begin the beginning position
   * @param end   the end position
   */
  void setBeginAndEnd(Position begin, Position end);

  /**
   * Set the current position of the player.
   *
   * @param position the position of the player
   */
  void setPlayerPosition(Position position);

  /**
   * Set the treasure in the dungeon.
   *
   * @param treasure treasure
   */
  void setTreasure(Map<String, Integer> treasure);

  /**
   * Set the direction of movement.
   *
   * @param directionSet direction of movement
   */
  void setAvailableMove(Set<Direction> directionSet);

  /**
   * Set the treasure that the player can pick up.
   *
   * @param treasure treasures picked up by the player
   */
  void setPickUp(Map<String, Integer> treasure);

  /**
   * Set the smell of Otyughs.
   *
   * @param level the level of the smell
   */
  void setSmell(int level);

  /**
   * The result after the player shoots an arrow.
   *
   * @param success  shoot successfully
   * @param arrowCnt the numbers of arrows
   */
  void shootResult(boolean success, int arrowCnt);

  /**
   * The result after the player pick up the treasure.
   *
   * @param treasure treasure in the dungeon
   * @param success  pick up successfully
   */
  void pickUpResult(String treasure, boolean success);

  /**
   * Show the GUI in the dungeon board.
   */
  void showInfo();

  /**
   * Show the result of the game.
   *
   * @param win game is over
   */
  void result(boolean win);
}
