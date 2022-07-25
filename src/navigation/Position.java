package navigation;

/**
 * Implement Position class to describe the position in the dungeon.
 *
 * @author Pengbo Wang
 */
public class Position {
  public int row;
  public int col;

  /**
   * The constructor of the Position class.
   *
   * @param row row of position
   * @param col columns of position
   */
  public Position(int row, int col) {
    this.row = row;
    this.col = col;
  }

  /**
   * Calculate the distance.
   *
   * @param position position
   * @return the distance
   */
  public int getDistance(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("position cannot be empty");
    }
    return (int) Math.abs((double) ((this.col - position.col) + (this.row - position.row)));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Position)) {
      return false;
    }
    Position op = (Position) o;
    return op.row == row && op.col == col;
  }

  @Override
  public int hashCode() {
    return (row * 31 + col) * 31;
  }

}
