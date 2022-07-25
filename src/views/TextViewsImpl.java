package views;

import controller.DungeonControllerImpl;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import navigation.Constant;
import navigation.Direction;
import navigation.Position;

/**
 * Implement a class that the dungeon game
 * can appear on the console in the form of text.
 *
 * @author Pengbo Wang
 */
public class TextViewsImpl implements Views {
  private final Appendable out;

  /**
   * The constructor of TextViewsImpl class.
   *
   * @param out the output
   */
  public TextViewsImpl(Appendable out) {
    if (out == null) {
      throw new IllegalArgumentException("Appendable out cannot be empty");
    }
    this.out = out;
  }

  @Override
  public void setBeginAndEnd(Position begin, Position end) {
    if (begin == null) {
      throw new IllegalArgumentException("begin position cannot be empty");
    }
    if (end == null) {
      throw new IllegalArgumentException("end position cannot be empty");
    }
    try {
      out.append("The initial position: (").append(String
              .valueOf(begin.row)).append(",").append(String.valueOf(begin.col)).append(")\n");
      out.append("End position: (").append(String.valueOf(end.row))
              .append(",").append(String.valueOf(end.col)).append(")\n");
      out.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setPlayerPosition(Position position) {
    if (position == null) {
      throw new IllegalArgumentException("position cannot be empty");
    }
  }

  @Override
  public void setTreasure(Map<String, Integer> treasure) {
    if (treasure == null) {
      throw new IllegalArgumentException("treasure cannot be empty");
    }
    try {
      if (treasure.size() != 0) {
        out.append("Now you have: ");
        for (String s : treasure.keySet()) {
          out.append(s).append(":").append(String.valueOf(treasure.get(s))).append(" ");
        }
      }
      out.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setAvailableMove(Set<Direction> directionSet) {
    if (directionSet == null) {
      throw new IllegalArgumentException("direction set cannot be empty");
    }
    try {
      out.append("Doors lead to the: ");
      for (Direction direction : directionSet) {
        out.append(direction.getName()).append(" ");
      }
      out.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setPickUp(Map<String, Integer> treasures) {
    if (treasures == null) {
      throw new IllegalArgumentException("treasures cannot be empty");
    }
    try {
      if (treasures.size() != 0) {
        out.append("You find: ");
        for (String key : treasures.keySet()) {
          out.append(key).append(": ").append(String.valueOf(treasures.get(key))).append("\n");
        }
      }
      out.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setSmell(int level) {
    try {
      if (level == Constant.SLIGHTSMELL) {
        out.append("You smell something slight nearby\n");
      } else if (level == Constant.TERRIBLESMELL) {
        out.append("You smell something terrible nearby\n");
      }
      out.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void shootResult(boolean success, int arrowCnt) {
    try {
      if (success) {
        out.append("You hear a great howl in the distance\n");
      } else {
        out.append("You shoot an arrow into the darkness\n");
      }
      if (DungeonControllerImpl.getInstance().getArrowsCnt() == 0) {
        out.append("You are out of arrows, explore to find more\n");
      }
      out.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void pickUpResult(String treasure, boolean success) {
    try {
      if (success) {
        out.append("You pick up a(n) ").append(treasure);
      } else {
        out.append("You pick up nothing!");
      }
      out.append("\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void showInfo() {
    // show the information of the dungeon game
  }

  @Override
  public void result(boolean win) {
    try {
      if (win) {
        out.append("Great! You made it through the map!\n");
      } else {
        out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!\n");
        out.append("Better luck next time\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
