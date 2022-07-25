package model;

import navigation.Position;

/**
 * Implement a Tunnel class.
 * Represents a cave with only 2 directions.
 *
 * @author Pengbo Wang
 */
public class Tunnel extends PositionImpl {

  /**
   * The constructor of the Tunnel class.
   *
   * @param row row of dungeon
   * @param col columns of dungeon
   */
  public Tunnel(int row, int col) {
    this.position = new Position(row, col);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Position)) {
      return false;
    }
    Tunnel op = (Tunnel) o;
    return this.position == op.position;
  }

  @Override
  public int hashCode() {
    return this.position.hashCode();
  }
}
