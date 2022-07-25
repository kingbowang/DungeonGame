package model;

import java.util.ArrayList;
import java.util.List;
import navigation.Position;

/**
 * Implement a CaveImpl class which implements the Cave interface.
 * This is a cave which has 1, 3 or 4 entrances.
 *
 * @author Pengbo Wang
 */
public class CaveImpl extends PositionImpl implements Cave {
  private final List<Treasure> treasures;
  List<ArrowImpl> arrows;
  List<OtyughsImpl> otyughs;

  /**
   * The constructor of the CaveImpl class.
   *
   * @param row rows in the dungeon
   * @param col columns in the dungeon
   */
  public CaveImpl(int row, int col) {
    this.position = new Position(row, col);
    this.treasures = new ArrayList<>();
    this.arrows = new ArrayList<>();
    this.otyughs = new ArrayList<>();
  }

  @Override
  public void setTreasure(Treasure treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("treasure cannot be empty");
    }
    this.treasures.add(treasure);
  }

  @Override
  public List<Treasure> getTreasures() {
    return this.treasures;
  }

  @Override
  public Position getPosition() {
    return this.position;
  }

  @Override
  public List<OtyughsImpl> getOtyughs() {
    return this.otyughs;
  }

  @Override
  public void setArrows(ArrowImpl arrow) {
    if (arrow == null) {
      throw new IllegalArgumentException("arrow cannot be empty");
    }
    this.arrows.add(arrow);
  }

  @Override
  public List<ArrowImpl> getArrows() {
    return this.arrows;
  }

  @Override
  public void addOtyughs(OtyughsImpl otyughs) {
    if (otyughs == null) {
      throw new IllegalArgumentException("otyughs cannot be empty");
    }
    this.otyughs.add(otyughs);
  }

  @Override
  public void reuse() {
    for (OtyughsImpl o : this.otyughs) {
      o.reuse();
    }
    for (ArrowImpl a : this.arrows) {
      a.reuse();
    }
    for (Treasure t : this.treasures) {
      t.reuse();
    }
  }

}
