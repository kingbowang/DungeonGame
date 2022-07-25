package views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import navigation.Constant;

/**
 * Implement a class to realize the display of game settings.
 * User can restart the game, reuse the game, start a new game and exit the game.
 *
 * @author Pengbo Wang
 */
public class DungeonMenuView extends JMenuBar {
  private final JMenuItem exit;     // exit the game
  private final JMenuItem restart;  // restart the game
  private final JMenuItem setting;  // set up the game
  private final JMenuItem reuse;    // reuse the game
  private final BlockingQueue<String> blockingCommandQueue;

  /**
   * The constructor of setting views.
   *
   * @param blockingCommandQueue blocking command queue
   */
  public DungeonMenuView(BlockingQueue<String> blockingCommandQueue) {
    if (blockingCommandQueue == null) {
      throw new IllegalArgumentException("blocking command queue cannot be empty");
    }
    this.blockingCommandQueue = blockingCommandQueue;
    JMenu dungeonMenu = new JMenu("   Dungeon   ");
    dungeonMenu.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    dungeonMenu.setVisible(true);
    dungeonMenu.add((this.exit = new JMenuItem("Exit")));
    dungeonMenu.add((this.restart = new JMenuItem("Restart")));
    dungeonMenu.add((this.setting = new JMenuItem("New Game")));
    dungeonMenu.add((this.reuse = new JMenuItem("Reuse")));
    this.add(dungeonMenu);
    this.setVisible(true);
    this.addListener();
  }

  private void addListener() {
    this.exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // exit the game
        System.exit(0);
      }
    });
    this.restart.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // restart the game
        try {
          blockingCommandQueue.put(Constant.RESTART);
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
        }
      }
    });
    this.setting.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Set up and restart the game
        new DungeonSettingView(blockingCommandQueue);
      }
    });
    this.reuse.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // reuse the game
        try {
          blockingCommandQueue.put(Constant.REUSE);
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
        }
      }
    });
  }

}
