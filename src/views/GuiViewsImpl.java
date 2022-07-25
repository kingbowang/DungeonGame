package views;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import navigation.Constant;
import navigation.Direction;
import navigation.Position;

/**
 * GUI view, this view is the general view of the user GUI interface.
 *
 * @author Pengbo Wang
 */
public class GuiViewsImpl extends JFrame implements Views {
  private Position playerPosition;
  private int row;
  private int col;
  private DungeonPanel dungeon;
  private InfoPanel info;
  private boolean[][] visible;
  private String[][] cellFile;
  private boolean shoot;

  /**
   * Create a GUI, this GUI is responsible for displaying all the details of a game.
   *
   * @param blockingCommandQueue storing user input in the queue and parsing in the controller,
   *                             providing asynchronous call user input processing
   * @param row       the row number of dungeon
   * @param col       the column number of dungeon
   * @param cellFile  corresponding to the image file name on each map position
   */
  public GuiViewsImpl(BlockingQueue<String> blockingCommandQueue, int row,
                      int col, String[][] cellFile) {
    if (blockingCommandQueue == null) {
      throw new IllegalArgumentException("blocking command queue cannot be empty");
    }
    if (row <= 0) {
      throw new IllegalArgumentException("Negative or zero row is not supported.");
    }
    if (col <= 0) {
      throw new IllegalArgumentException("Negative or zero column is not supported.");
    }
    if (cellFile == null) {
      throw new IllegalArgumentException("cell file cannot be empty");
    }
    this.setLayout(null);
    this.setBounds(0, 0, 940, 600);
    this.initGame(blockingCommandQueue, row, col, cellFile);
  }

  private void initGame(BlockingQueue<String> blockingCommandQueue,
                        int row, int col, String[][] cellFile) {
    this.row = row;
    this.col = col;
    this.dungeon = new DungeonPanel(blockingCommandQueue, row, col);
    this.info = new InfoPanel(blockingCommandQueue);
    this.setJMenuBar(new DungeonMenuView(blockingCommandQueue));
    this.cellFile = cellFile;
    this.visible = new boolean[row][col];
    JLabel clickHere = new JLabel("click here");
    clickHere.setBounds(0, 0, 60, 15);
    clickHere.setVisible(true);
    this.add(clickHere);
    // create a dungeon map
    dungeon.showDungeon(this.cellFile, this.visible);
    this.add(dungeon.getPanel());
    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
      }

      @Override
      public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
      }

      @Override
      public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        try {
          if (e.getKeyCode() == 37) {
            blockingCommandQueue.put(Constant.MOVE);
            blockingCommandQueue.put(Direction.W.getName());
          } else if (e.getKeyCode() == 38) {
            blockingCommandQueue.put(Constant.MOVE);
            blockingCommandQueue.put(Direction.N.getName());
          } else if (e.getKeyCode() == 39) {
            blockingCommandQueue.put(Constant.MOVE);
            blockingCommandQueue.put(Direction.E.getName());
          } else if (e.getKeyCode() == 40) {
            blockingCommandQueue.put(Constant.MOVE);
            blockingCommandQueue.put(Direction.S.getName());
          } else if (e.getKeyCode() == 83) {
            // shoot, distance, direction
            if (shoot) {
              blockingCommandQueue.put(Direction.S.getName());
              shoot = false;
            } else {
              blockingCommandQueue.put(Constant.SHOOT);
              shoot = true;
            }
          } else if (e.getKeyCode() == 87) {
            if (shoot) {
              blockingCommandQueue.put(Direction.W.getName());
              shoot = false;
            }
          } else if (e.getKeyCode() == 69) {
            if (shoot) {
              blockingCommandQueue.put(Direction.E.getName());
              shoot = false;
            }
          } else if (e.getKeyCode() == 78) {
            if (shoot) {
              blockingCommandQueue.put(Direction.N.getName());
              shoot = false;
            }
          } else if (e.getKeyCode() == 49) {
            if (shoot) {
              blockingCommandQueue.put("1");
            }
          } else if (e.getKeyCode() == 50) {
            if (shoot) {
              blockingCommandQueue.put("2");
            }
          } else if (e.getKeyCode() == 51) {
            if (shoot) {
              blockingCommandQueue.put("3");
            }
          } else if (e.getKeyCode() == 52) {
            if (shoot) {
              blockingCommandQueue.put("4");
            }
          } else if (e.getKeyCode() == 53) {
            if (shoot) {
              blockingCommandQueue.put("5");
            }
          }
        } catch (InterruptedException ee) {
          ee.printStackTrace();
        }
      }
    });
    this.add(info);
    this.setVisible(true);
  }

  @Override
  public void setBeginAndEnd(Position begin, Position end) {
    if (begin == null) {
      throw new IllegalArgumentException("begin position cannot be empty");
    }
    if (end == null) {
      throw new IllegalArgumentException("end position cannot be empty");
    }
    this.visible[begin.row][begin.col] = true;
    this.info.setBeginAndEndPosition(begin, end);
  }

  @Override
  public void setPlayerPosition(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("position cannot be empty");
    }
    // change the position of the player to white color
    this.playerPosition = position;
    this.visible[position.row][position.col] = true;
    this.dungeon.setPlayer(position.row, position.col, this.cellFile[position.row][position.col]);
    this.info.setPosition(position);
  }

  @Override
  public void setTreasure(Map<String, Integer> treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("treasure cannot be empty");
    }
    this.info.setTreasure(treasure);
  }

  @Override
  public void setAvailableMove(Set<Direction> directionSet) {
    if (directionSet == null) {
      throw new IllegalArgumentException("direction set cannot be empty");
    }
    // make the cell in the movable direction visible
    for (Direction direction : directionSet) {
      switch (direction) {
        case N:
          if (this.playerPosition.row == 0) {
            this.visible[row - 1][playerPosition.col] = true;
          } else {
            this.visible[playerPosition.row - 1][playerPosition.col] = true;
          }
          break;
        case E:
          if (this.playerPosition.col == col - 1) {
            this.visible[playerPosition.row][0] = true;
          } else {
            this.visible[playerPosition.row][playerPosition.col + 1] = true;
          }
          break;
        case W:
          if (this.playerPosition.col == 0) {
            this.visible[playerPosition.row][col - 1] = true;
          } else {
            this.visible[playerPosition.row][playerPosition.col - 1] = true;
          }
          break;
        case S:
          if (this.playerPosition.row == row - 1) {
            this.visible[0][playerPosition.col] = true;
          } else {
            this.visible[playerPosition.row + 1][playerPosition.col] = true;
          }
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + direction);
      }
    }
    this.dungeon.showDungeon(cellFile, visible);
    this.dungeon.setPlayer(playerPosition.row, playerPosition.col,
            cellFile[playerPosition.row][playerPosition.col]);
  }

  @Override
  public void setPickUp(Map<String, Integer> treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("treasure cannot be empty");
    }
    this.info.setPickUp(treasure);
  }

  @Override
  public void setSmell(int level) {
    this.info.setSmell(level);
  }

  @Override
  public void shootResult(boolean success, int arrowCnt) {
    String message;
    if (success) {
      message = "You hear a great howl in the distance.";
    } else {
      message = "You shoot an arrow into the darkness";
    }
    if (arrowCnt == 0) {
      message += "You are out of arrows, explore to find more.";
    }
    JOptionPane.showMessageDialog(this, message, "Shoot Result", JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public void pickUpResult(String treasure, boolean success) {
    String message;
    if (success) {
      message = "You pick up a(n) " + treasure + ".";
    } else {
      message = "You pick up nothing!";
    }
    JOptionPane.showMessageDialog(this, message, "Pick Up Result", JOptionPane.WARNING_MESSAGE);
  }

  @Override
  public void showInfo() {
    // nothing in GUI
  }

  @Override
  public void result(boolean win) {
    // show result
    String message;
    if (win) {
      message = "Great! You made it through the map!";
    } else {
      message = "Chomp, chomp, chomp, you are eaten by an Otyugh. Better luck next time";
    }
    JOptionPane.showMessageDialog(this, message, "Result", JOptionPane.WARNING_MESSAGE);
  }

}