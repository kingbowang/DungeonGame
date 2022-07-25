package views;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import navigation.Constant;
import navigation.Position;

/**
 * Responsible for showing players information during the game, such as current coordinates,
 * whether they can smell, whether there is treasure/arrow, etc.
 *
 * @author Pengbo Wang
 */
public class InfoPanel extends JPanel {
  private static final String filePath = "/dungeon-images-bw/";
  private JLabel nowPosition;
  private final List<JLabel> treasure;
  private JLabel smell;
  private final List<JLabel> nowHaveTreasure;
  private final BlockingQueue<String> blockingCommandQueue;


  /**
   * The constructor of info panel.
   * Current position, start position (initialization), end position (initialization)
   *
   * @param blockingCommandQueue blocking command queue
   */
  public InfoPanel(BlockingQueue<String> blockingCommandQueue) {
    if (blockingCommandQueue == null) {
      throw new IllegalArgumentException("blocking command queue cannot be empty");
    }
    this.blockingCommandQueue = blockingCommandQueue;
    // now position
    this.setBounds(640, 0, 300, 640);
    this.setLayout(null);
    this.setVisible(true);
    this.treasure = new ArrayList<>();
    this.nowHaveTreasure = new ArrayList<>();
    // begin position
    JLabel beginLabel = new JLabel("Begin position:");
    JLabel endLabel = new JLabel("End position:");
    beginLabel.setVisible(true);
    endLabel.setVisible(true);
    beginLabel.setBounds(0, 10, 100, 20);
    endLabel.setBounds(0, 40, 100, 20);
    this.add(beginLabel);
    this.add(endLabel);
    JLabel nowPositionLabel = new JLabel("Now position: ");
    nowPositionLabel.setVisible(true);
    nowPositionLabel.setBounds(0, 70, 100, 20);
    this.add(nowPositionLabel);
    // treasure
    JLabel treasureLabel = new JLabel("Pick Up: ");
    treasureLabel.setVisible(true);
    treasureLabel.setBounds(0, 100, 200, 20);
    this.add(treasureLabel);
    // smell
    JLabel smellLabel = new JLabel("Smell: ");
    smellLabel.setVisible(true);
    smellLabel.setBounds(0, 180, 100, 20);
    this.add(smellLabel);
    JLabel playerInfoLabel = new JLabel("Now you have: ");
    playerInfoLabel.setVisible(true);
    playerInfoLabel.setBounds(0, 280, 100, 20);
    this.add(playerInfoLabel);
  }

  /**
   * Show the player's position to the user.
   *
   * @param position position object representing the coordinates of the player
   */
  public void setPosition(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("position cannot be empty");
    }
    if (this.nowPosition != null) {
      this.remove(nowPosition);
    }
    this.nowPosition = new JLabel("(" + position.row + ", " + position.col + ")");
    this.nowPosition.setVisible(true);
    this.nowPosition.setBounds(110, 70, 100, 20);
    this.add(nowPosition);
    this.repaint();
  }

  /**
   * Display the beginning and end position information of this game to the user.
   *
   * @param begin begin position
   * @param end   end position
   */
  public void setBeginAndEndPosition(Position begin, Position end) {
    if (begin == null) {
      throw new IllegalArgumentException("begin position cannot be empty");
    }
    if (end == null) {
      throw new IllegalArgumentException("end position cannot be empty");
    }
    JLabel beginPosition = new JLabel("(" + begin.row + ", " + begin.col + ")");
    beginPosition.setVisible(true);
    beginPosition.setBounds(110, 10, 100, 20);
    this.add(beginPosition);
    JLabel endPosition = new JLabel("(" + end.row + ", " + end.col + ")");
    endPosition.setVisible(true);
    endPosition.setBounds(110, 40, 100, 20);
    this.add(endPosition);
  }

  /**
   * Display the Treasure and Arrow owned by the player on the screen.
   *
   * @param treasure a map, the key represents the type name of treasure/arrow,
   *                 and the value represents the number of possessions
   */
  public void setTreasure(Map<String, Integer> treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("treasure cannot be empty");
    }
    for (JLabel jlabel : this.nowHaveTreasure) {
      this.remove(jlabel);
    }
    int beginY = 310;
    for (String key : treasure.keySet()) {
      int cnt = treasure.get(key);
      if (cnt > 0) {
        URL fileUrl = this.getClass().getResource(filePath + Constant.TREASURE_PATH.get(key));
        assert fileUrl != null;
        JLabel label = new JLabel(new ImageIcon(fileUrl));
        label.setVisible(true);
        label.setBounds(0, beginY, 48, 48);
        this.add(label);
        JLabel cntLabel = new JLabel(" X " + cnt);
        cntLabel.setVisible(true);
        cntLabel.setBounds(50, beginY, 40, 48);
        this.add(cntLabel);
        beginY += 50;
        this.nowHaveTreasure.add(label);
        this.nowHaveTreasure.add(cntLabel);
      }
    }
    this.repaint();
  }

  /**
   * Treasure and arrow that can be picked up are displayed on the screen.
   *
   * @param treasure a map, the key represents the type name of treasure/arrow,
   *                 and the value represents the number of possessions
   */
  public void setPickUp(Map<String, Integer> treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("treasure cannot be empty");
    }
    for (JLabel jlabel : this.treasure) {
      this.remove(jlabel);
    }
    int beginX = 0;
    for (String key : treasure.keySet()) {
      int cnt = treasure.get(key);
      if (cnt > 0) {
        URL fileUrl = this.getClass().getResource(filePath + Constant.TREASURE_PATH.get(key));
        assert fileUrl != null;
        PickUpLabel label = new PickUpLabel(new ImageIcon(fileUrl), key);
        label.setVisible(true);
        label.setBounds(beginX, 130, 48, 48);
        label.addMouseListener(new MouseListener() {
          @Override
          public void mouseClicked(MouseEvent e) {
            // mouse clicked
          }

          @Override
          public void mousePressed(MouseEvent e) {
            // mouse pressed
          }

          @Override
          public void mouseReleased(MouseEvent e) {
            try {
              blockingCommandQueue.put(Constant.PICK);
              blockingCommandQueue.put(label.getTreasureName());
            } catch (InterruptedException interruptedException) {
              interruptedException.printStackTrace();
            }
          }

          @Override
          public void mouseEntered(MouseEvent e) {
            // mouse entered
          }

          @Override
          public void mouseExited(MouseEvent e) {
            // mouse exited
          }
        });
        this.add(label);
        beginX += 50;
        this.treasure.add(label);
      }
    }
    this.repaint();
  }

  /**
   * Display the monster smell on the screen.
   *
   * @param level smell level, 0 means no smell 1 means slight smell,
   *              2 means terrible smell
   */
  public void setSmell(int level) {
    if (this.smell != null) {
      this.remove(smell);
    }
    if (level == 0) {
      this.repaint();
      return;
    }
    if (level == 1) {
      URL fileUrl = this.getClass().getResource(filePath + Constant.SLIGHTSMELL_FILE);
      assert fileUrl != null;
      this.smell = new JLabel(new ImageIcon(fileUrl));
    } else if (level == 2) {
      URL fileUrl = this.getClass().getResource(filePath + Constant.TERRIBLESMELL_FILE);
      assert fileUrl != null;
      this.smell = new JLabel(new ImageIcon(fileUrl));
    }
    assert this.smell != null;
    this.smell.setVisible(true);
    this.smell.setBounds(0, 210, 64, 64);
    this.add(smell);
    this.repaint();
  }
}
