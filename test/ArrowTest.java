import model.Arrow;
import model.ArrowImpl;
import org.junit.Test;
import navigation.Position;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for the arrow.
 *
 * @author Pengbo Wang
 */
public class ArrowTest {

  @Test
  public void take() {
    Arrow arrow = new ArrowImpl(new Position(0, 0));
    assertFalse(arrow.isTaken());
    arrow.take();
    assertTrue(arrow.isTaken());
  }

}