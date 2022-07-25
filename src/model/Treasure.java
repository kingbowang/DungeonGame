package model;

import navigation.Position;

/**
 * This class implements the treasure abstract class that can be extended.
 * It represents the treasure in the dungeon.
 *
 * @author Pengbo Wang
 */
public abstract class Treasure extends PositionImpl {
  private boolean taken;

  /**
   * The constructor of the Treasure class.
   *
   * @param position position of the treasure
   */
  public Treasure(Position position) {
    this.position = position;
    this.taken = false;
  }

  /**
   * Construct a method to get the type of the treasure.
   *
   * @return null
   */
  public String getType() {
    return null;
  }

  /**
   * Construct a method to take the treasure.
   */
  public void takeTreasure() {
    this.taken = true;
  }

  /**
   * Construct a method to get the treasures obtained.
   *
   * @return treasure has been taken
   */
  public boolean isTaken() {
    return this.taken;
  }

  /**
   * Reuse the treasure and set the treasure to the initial state.
   */
  public void reuse() {
    this.taken = false;
  }

}
