package model;

import java.util.Random;
import navigation.Position;

/**
 * Otyughs implementation class extends PositionImpl and implements OtyughsInterface.
 * It mainly stores the blood volume of Otyughs and its position on the map.
 *
 * @author Pengbo Wang
 */
public class OtyughsImpl extends PositionImpl implements Otyughs {
  private int blood;

  /**
   * The constructor of the OtyughsImpl class.
   *
   * @param position the position of Otyughs
   */
  public OtyughsImpl(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("position cannot be empty");
    }
    this.position = position;
    this.blood = 2;
  }

  @Override
  public void underAttack() {
    if (this.isDead()) {
      return;
    }
    --this.blood;
  }

  @Override
  public boolean attack() {
    if (this.blood == 2) {
      return true;
    }
    if (this.blood == 0) {
      return false;
    }
    return new Random().nextBoolean();
  }

  @Override
  public boolean isDead() {
    return this.blood == 0;
  }

  @Override
  public Position getPosition() {
    return this.position;
  }

  @Override
  public void reuse() {
    this.blood = 2;
  }

}
