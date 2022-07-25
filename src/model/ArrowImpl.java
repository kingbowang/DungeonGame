package model;

import navigation.Position;

/**
 * The Arrow class inherits PositionImpl and implements ArrowInterface.
 * The Arrow class stores the sign of whether it
 * has been picked up and the position of Arrow on the map.
 *
 * @author Pengbo Wang
 */
public class ArrowImpl extends PositionImpl implements Arrow {
  private boolean isTaken;

  /**
   * The constructor of the ArrowImpl class.
   *
   * @param position position of arrow
   */
  public ArrowImpl(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("position cannot be empty");
    }
    this.position = position;
    this.isTaken = false;
  }

  @Override
  public void take() {
    this.isTaken = true;
  }

  @Override
  public boolean isTaken() {
    return this.isTaken;
  }

  @Override
  public void reuse() {
    this.isTaken = false;
  }

}
