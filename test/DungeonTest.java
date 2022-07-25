import model.Dungeon;
import model.DungeonImpl;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Test for the dungeon.
 *
 * @author Pengbo Wang
 */
public class DungeonTest {

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidInterconnectivity() {
    Dungeon dungeon = new DungeonImpl(4, 6, true,
            -8, 0.2,1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTreasureRandom() {
    Dungeon dungeon = new DungeonImpl(4, 6, true,
            4, 0.19,1);
  }

  @Test
  public void isGameOver() {
    Dungeon dungeon = new DungeonImpl();
    assertFalse(dungeon.isGameOver());
  }

  @Test
  public void isWin() {
    Dungeon dungeon = new DungeonImpl();
    assertFalse(dungeon.isWin());
  }

}