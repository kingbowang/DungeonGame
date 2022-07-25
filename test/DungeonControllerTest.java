import controller.DungeonController;
import controller.DungeonControllerImpl;
import org.easymock.EasyMock;
import org.junit.Test;
import navigation.Direction;

import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for the dungeon controller.
 *
 * @author Pengbo Wang
 */
public class DungeonControllerTest {

  @Test
  public void gameOver() {
    DungeonController controller = EasyMock.createMock(DungeonControllerImpl.class);
    EasyMock.expect(controller.gameOver()).andReturn(false);
    controller.parseCommonLine(new String[0]);
    assertFalse(controller.gameOver());
  }

  @Test
  public void isWin() {
    DungeonController controller = EasyMock.createMock(DungeonControllerImpl.class);
    EasyMock.expect(controller.isWin()).andReturn(false);
    controller.parseCommonLine(new String[0]);
    assertFalse(controller.isWin());
  }


  @Test
  public void inCave() {
    DungeonController controller = EasyMock.createMock(DungeonControllerImpl.class);
    EasyMock.expect(controller.inCave()).andReturn(true);
    controller.parseCommonLine(new String[0]);
    assertTrue(controller.inCave());
  }

  @Test
  public void testGameNotOver() {
    DungeonControllerImpl controller = new DungeonControllerImpl();
    EasyMock.expect(controller.gameOver()).andReturn(false);
    controller.parseCommonLine(new String[0]);
    assertFalse(controller.gameOver());
  }

  @Test
  public void testNotWin() {
    DungeonControllerImpl controller = new DungeonControllerImpl();
    EasyMock.expect(controller.gameOver()).andReturn(false);
    controller.parseCommonLine(new String[0]);
    assertFalse(controller.isWin());
  }

  @Test
  public void testInCave() {
    DungeonControllerImpl controller = new DungeonControllerImpl();
    EasyMock.expect(controller.gameOver()).andReturn(false);
    controller.parseCommonLine(new String[0]);
    assertTrue(controller.inCave());
  }

  @Test
  public void testDirection() {
    DungeonControllerImpl controller = new DungeonControllerImpl();
    EasyMock.expect(controller.gameOver()).andReturn(false);
    controller.parseCommonLine(new String[0]);
    Direction directionN = Direction.valueOf("N");
    assertFalse(controller.playerShoot(0, directionN));
  }

  @Test
  public void testGetArrowsCnt() {
    DungeonControllerImpl controller = new DungeonControllerImpl();
    EasyMock.expect(controller.gameOver()).andReturn(false);
    controller.parseCommonLine(new String[0]);
    assertEquals(controller.getArrowsCnt(), 3);
    for (int i = 0; i < 3; ++i) {
      controller.playerShoot(1, Direction.N);
      assertEquals(controller.getArrowsCnt(), 3 - i - 1);
    }
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidMove() throws Exception {
    Scanner input = new Scanner("M\nN\n");
    Appendable output = System.out;
    String[] args = new String[0];
    DungeonControllerImpl.getInstance().startGame(input, output, args);
  }

  @Test
  public void testAllCaves() throws Exception {
    Scanner input = new Scanner("M\nE\nM\nS\nM\nW\nM\nW\nM\nS\nM\nE\nM\nS\nM\n"
            + "E\nM\nS\nM\nN\nS\n1\nE\nS\n1\nE\nM\nE\nM\nW\nM\nN\n");
    Appendable output = System.out;
    String[] args = new String[0];
    DungeonControllerImpl.getInstance().startGame(input, output, args);
    assertTrue(DungeonControllerImpl.getInstance().gameOver());
  }

  @Test
  public void testGetAvailableDirection() throws Exception {
    Appendable output = System.out;
    String[] args = new String[0];
    Scanner input = new Scanner("M\nW\nM\nW\nM\nW\nM\nN\nS\n1\nE\nS\n1\nE\nM\nE\nM\nW\nM\nN");
    DungeonControllerImpl.getInstance().startGame(input, output, args);
    assertEquals("[S, W, E, N]", DungeonControllerImpl.getInstance().getAvailableDirection());
  }

  @Test
  public void testNavigation() throws Exception {
    Scanner input = new Scanner("M\nS\nM\nS\nM\nW\nM\nW\nM\nW");
    Appendable output = System.out;
    String[] args = new String[0];
    DungeonControllerImpl.getInstance().startGame(input, output, args);
    assertTrue(DungeonControllerImpl.getInstance().gameOver());
  }

  @Test
  public void testPickUpTreasure() throws Exception {
    Scanner input = new Scanner("M\nW\nM\nW\nM\nW\nP\nruby\nM\nN\nM\nN");
    Appendable output = System.out;
    String[] args = new String[0];
    DungeonControllerImpl.getInstance().startGame(input, output, args);
    assertTrue(DungeonControllerImpl.getInstance().gameOver());
  }

  @Test
  public void testPickUpArrow() throws Exception {
    Scanner input = new Scanner("M\nS\nM\nS\nM\nS\nM\nW\nP\narrow\nM\nE\nM\nN\nM\nW\nM\nW\nM\nW");
    Appendable output = System.out;
    String[] args = new String[0];
    DungeonControllerImpl.getInstance().startGame(input, output, args);
    assertTrue(DungeonControllerImpl.getInstance().gameOver());
  }

  @Test
  public void testEatenByOtyugh() throws Exception {
    Scanner input = new Scanner("M\nW\nM\nW\nM\nW\nM\nN\nM\nE");
    Appendable output = System.out;
    String[] args = new String[0];
    DungeonControllerImpl.getInstance().startGame(input, output, args);
    assertTrue(DungeonControllerImpl.getInstance().gameOver());
    assertFalse(DungeonControllerImpl.getInstance().isWin());
  }

  @Test
  public void testKillingOtyugh() throws Exception {
    Scanner input = new Scanner("M\nW\nM\nW\nM\nW\nM\nN\nS\n1\nE\nS\n1\nE\nM\nE\nM\nW\nM\nN");
    Appendable output = System.out;
    String[] args = new String[0];
    DungeonControllerImpl.getInstance().startGame(input, output, args);
    assertTrue(DungeonControllerImpl.getInstance().gameOver());
    assertTrue(DungeonControllerImpl.getInstance().isWin());
  }

  @Test
  public void testReachEndWinGame() throws Exception {
    Scanner input = new Scanner("M\nW\nM\nW\nM\nW\nM\nN\nS\n1\nE\nS\n1\nE\nM\nN");
    Appendable output = System.out;
    String[] args = new String[0];
    DungeonControllerImpl.getInstance().startGame(input, output, args);
    assertTrue(DungeonControllerImpl.getInstance().gameOver());
    assertTrue(DungeonControllerImpl.getInstance().isWin());
  }

}