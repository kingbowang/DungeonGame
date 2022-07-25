package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import navigation.Constant;
import navigation.Direction;
import navigation.Position;

/**
 * Public class that does the drawing of the game board and elements.
 *
 * @author Pengbo Wang
 */
public class DungeonPanel extends JPanel {
  private final int row;
  private final int col;
  private static final int size = 64;
  private static final String filePath = "/dungeon-images-bw/";
  private final List<List<JLabel>> placeList;
  private final BlockingQueue blockingCommandQueue;
  private Position playerPosition;
  private final JPanel centerPanel;

  /**
   * Create a panel, this panel is responsible for displaying the map details of dungeon.
   *
   * @param blockingCommandQueue blocking command queue and store user input
   *                             in the queue and parse it in the controller
   * @param row row in dungeon
   * @param col column in dungeon
   */
  public DungeonPanel(BlockingQueue blockingCommandQueue, int row, int col) {
    if (blockingCommandQueue == null) {
      throw new IllegalArgumentException("blocking command queue cannot be empty");
    }
    if (row <= 0) {
      throw new IllegalArgumentException("Negative or zero row is not supported.");
    }
    if (col <= 0) {
      throw new IllegalArgumentException("Negative or zero column is not supported.");
    }
    this.blockingCommandQueue = blockingCommandQueue;
    this.centerPanel = new JPanel();
    this.row = row;
    this.col = col;
    this.setPreferredSize(new Dimension(col * size, row * size));
    JScrollPane scrollPane = new JScrollPane(this);
    int centerSize = 640;
    centerPanel.setBounds(0, 0, centerSize, centerSize);
    centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    centerPanel.setVisible(true);
    centerPanel.add(scrollPane);
    int scrollSize = 500;
    if (row * size > scrollSize && col * size > scrollSize) {
      scrollPane.setPreferredSize(new Dimension(scrollSize, scrollSize));
    } else if (row * size > scrollSize && col * size <= scrollSize) {
      scrollPane.setPreferredSize(new Dimension(col * size + 8, scrollSize));
    } else if (row * size <= scrollSize && col * size > scrollSize) {
      scrollPane.setPreferredSize(new Dimension(scrollSize, row * size + 8));
    } else {
      scrollPane.setPreferredSize(new Dimension(col * size + 8, row * size + 8));
    }
    this.placeList = new ArrayList<>();
    for (int i = 0; i < row; ++i) {
      List<JLabel> rowLabel = new ArrayList<>();
      for (int j = 0; j < col; ++j) {
        JLabel label = new PlaceJlabel(null, i, j);
        rowLabel.add(label);
        this.addCaveMouseListener(label);
        this.add(label);
      }
      this.placeList.add(rowLabel);
    }
    this.setLayout(new GridLayout(row, col, 0, 0));
    this.setVisible(true);
  }

  private void addCaveMouseListener(JLabel label) {
    label.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        try {
          // determine if it is visible
          if (((PlaceJlabel) label).isItVisible()) {
            // get direction and save the position of the player
            Position labelPosition = ((PlaceJlabel) label).getPosition();
            int north = ((playerPosition.row - 1 + row) % row) * col + playerPosition.col;
            int east = playerPosition.row * col + (playerPosition.col + 1) % col;
            int west = playerPosition.row * col + (playerPosition.col - 1 + col) % col;
            int south = ((playerPosition.row + 1) % row) * col + playerPosition.col;
            if (labelPosition.row * col + labelPosition.col == north) {
              blockingCommandQueue.put(Constant.MOVE);
              blockingCommandQueue.put(Direction.N.getName());
            } else if (labelPosition.row * col + labelPosition.col == west) {
              blockingCommandQueue.put(Constant.MOVE);
              blockingCommandQueue.put(Direction.W.getName());
            } else if (labelPosition.row * col + labelPosition.col == south) {
              blockingCommandQueue.put(Constant.MOVE);
              blockingCommandQueue.put(Direction.S.getName());
            } else if (labelPosition.row * col + labelPosition.col == east) {
              blockingCommandQueue.put(Constant.MOVE);
              blockingCommandQueue.put(Direction.E.getName());
            } else {
              System.out.println("Can not move");
            }
          }
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
        }
      }

      @Override
      public void mousePressed(MouseEvent e) {
        // mouse pressed
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        // mouse released
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
  }

  /**
   * Display the current dungeon status on the screen.
   *
   * @param imagePath store the name of the picture corresponding to the cave or tunnel
   * @param visible store which caves or tunnels are currently visible to users
   */
  public void showDungeon(String[][] imagePath, boolean[][] visible) {
    if (imagePath == null) {
      throw new IllegalArgumentException("image path cannot be empty");
    }
    if (visible == null) {
      throw new IllegalArgumentException("visible cannot be empty");
    }
    for (int i = 0; i < imagePath.length; ++i) {
      for (int j = 0; j < imagePath[i].length; ++j) {
        URL fileUrl = this.getClass().getResource(filePath + Constant.BLANK);
        assert fileUrl != null;
        ImageIcon placeImage = new ImageIcon(fileUrl);
        JLabel place = this.placeList.get(i).get(j);
        if (visible[i][j]) {
          ((PlaceJlabel) place).visible();
          fileUrl = this.getClass().getResource(filePath + Constant.CELL + imagePath[i][j]);
          assert fileUrl != null;
          placeImage = new ImageIcon(fileUrl);
        }
        place.setVisible(true);
        place.setIcon(placeImage);
      }
    }
    this.validate();
  }

  /**
   * Set the picture where the player is located.
   *
   * @param i the row where the player is located
   * @param j the column where the player is located
   * @param file represents the file path of the player
   */
  public void setPlayer(int i, int j, String file) {
    if (file == null) {
      throw new IllegalArgumentException("file cannot be empty");
    }
    JLabel place = this.placeList.get(i).get(j);
    ((PlaceJlabel) place).visible();
    this.playerPosition = new Position(i, j);

    URL fileUrl = this.getClass().getResource(filePath + Constant.PLAYER_CELL + file);
    assert fileUrl != null;
    ImageIcon image = new ImageIcon(fileUrl);
    image.getImage().flush();
    place.setIcon(image);
    this.validate();
  }

  /**
   * Get the panel to add it to the upper container.
   *
   * @return added to the panel of the upper container
   */
  public JPanel getPanel() {
    return this.centerPanel;
  }

}
