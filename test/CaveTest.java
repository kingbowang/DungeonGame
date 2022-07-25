import model.ArrowImpl;
import model.Cave;
import model.CaveImpl;
import model.Treasure;
import model.OtyughsImpl;
import model.Diamonds;
import org.junit.Test;
import navigation.Position;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Test for the cave.
 *
 * @author Pengbo Wang
 */
public class CaveTest {

  @Test
  public void SetGetTreasure() {
    Cave cave = new CaveImpl(1, 1);
    Position position = new Position(1, 1);
    List<Treasure> treasureList = new ArrayList<>();
    Treasure treasure = new Diamonds(position);
    treasureList.add(treasure);
    cave.setTreasure(treasure);
    assertEquals(cave.getTreasures(), treasureList);
  }

  @Test
  public void getPosition() {
    int x = 10;
    int y = 20;
    Position position = new Position(x, y);
    Cave cave = new CaveImpl(x, y);
    assertEquals(cave.getPosition().col, position.col);
    assertEquals(cave.getPosition().row, position.row);
  }

  @Test
  public void ArrowTest() {
    Cave cave = new CaveImpl(1, 1);
    Position position = new Position(1, 1);
    for (int i = 0; i < 1000; ++i) {
      ArrowImpl arrow = new ArrowImpl(position);
      cave.setArrows(arrow);
      assertEquals(cave.getArrows().size(), i + 1);
    }
  }

  @Test
  public void OtyughTest() {
    Cave cave = new CaveImpl(1, 1);
    Position position = new Position(1, 1);
    for (int i = 0; i < 1000; ++i) {
      OtyughsImpl otyughs = new OtyughsImpl(position);
      cave.addOtyughs(otyughs);
      assertEquals(cave.getOtyughs().size(), i + 1);
    }
  }

}