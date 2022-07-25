package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import model.ArrowImpl;
import model.DungeonImpl;
import model.Treasure;
import navigation.Constant;
import navigation.Direction;
import navigation.Position;
import views.GuiViewsImpl;
import views.TextViewsImpl;
import views.Views;

/**
 * Implement a DungeonControllerImpl class which implements DungeonController interface.
 * Add one more parameter to indicate whether to enable GUI.
 * Restart: call InitGame to restart the game.
 *
 * @author Pengbo Wang
 */
public class DungeonControllerImpl implements DungeonController {
  private DungeonImpl dungeon;
  public String[][] cellFilePath;
  private int row;
  private int col;
  private Views guiViews;
  private Views textViews;
  private final BlockingQueue<String> blockingCommandQueue;
  private static volatile DungeonControllerImpl instance;

  /**
   * The constructor of DungeonControllerImpl class.
   */
  public DungeonControllerImpl() {
    this.dungeon = null;
    this.blockingCommandQueue = new LinkedBlockingDeque<>();
  }

  /**
   * The constructor of DungeonControllerImpl getInstance.
   *
   * @return instance
   */
  public static DungeonControllerImpl getInstance() {
    if (instance == null) {
      synchronized (DungeonControllerImpl.class) {
        instance = new DungeonControllerImpl();
      }
    }
    return instance;
  }

  @Override
  public void parseCommonLine(String[] userInput) {
    if (userInput.length == 0) {
      this.dungeon = new DungeonImpl();
      this.row = 6;
      this.col = 6;
      this.cellFilePath = dungeon.getCell();
      return;
    }
    int row = Integer.parseInt(userInput[0]);
    int col = Integer.parseInt(userInput[1]);
    boolean wrapping = Boolean.parseBoolean(userInput[2]);
    int interconnectivity = Integer.parseInt(userInput[3]);
    double treasureRandom = Double.parseDouble(userInput[4]);
    int otyughsNum = Integer.parseInt(userInput[5]);
    this.row = row;
    this.col = col;
    this.dungeon = new DungeonImpl(row, col, wrapping,
            interconnectivity, treasureRandom, otyughsNum);
    this.cellFilePath = dungeon.getCell();
  }

  @Override
  public boolean playerShoot(int caveDistance, Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("direction cannot be empty");
    }
    return this.dungeon.shoot(caveDistance, direction);
  }

  @Override
  public void playerMove(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("direction cannot be empty");
    }
    this.dungeon.playerMove(direction);
  }

  @Override
  public boolean playerPickUp(String treasureType) {
    if (treasureType == null) {
      throw new IllegalArgumentException("treasureType cannot be empty");
    }
    if (treasureType.equals("Arrow")) {
      return this.dungeon.playerTakeArrow();
    }
    return this.dungeon.playerTakeTreasure(treasureType);
  }

  @Override
  public Map<String, Integer> getPickUp() {
    List<Treasure> treasures = this.dungeon.getTreasure();
    Map<String, Integer> ans = new HashMap<>();
    for (Treasure treasure : treasures) {
      if (ans.containsKey(treasure.getType())) {
        ans.put(treasure.getType(), ans.get(treasure.getType()) + 1);
      } else {
        ans.put(treasure.getType(), 1);
      }
    }
    List<ArrowImpl> arrows = this.dungeon.getArrow();
    if (arrows.size() != 0) {
      ans.put("Arrow", arrows.size());
    }
    return ans;
  }

  @Override
  public boolean gameOver() {
    return this.dungeon.isGameOver();
  }

  @Override
  public boolean isWin() {
    return this.dungeon.isWin();
  }

  @Override
  public Set<Direction> getAvailableDirection() {
    return this.dungeon.getAvailableMove();
  }

  @Override
  public int getOtyughs() {
    int otyughsCnt = 0;
    otyughsCnt += this.dungeon.otyughsDistance(1);
    if (otyughsCnt > 0) {
      return Constant.TERRIBLESMELL;
    }
    otyughsCnt += this.dungeon.otyughsDistance(2);
    if (otyughsCnt >= 2) {
      return Constant.TERRIBLESMELL;
    }
    if (otyughsCnt == 1) {
      return Constant.SLIGHTSMELL;
    }
    return Constant.NOSMELL;
  }

  @Override
  public boolean inCave() {
    return true;
  }

  @Override
  public int getArrowsCnt() {
    return this.dungeon.getPlayer().getArrowCnt();
  }

  @Override
  public Position getBegin() {
    return this.dungeon.getBegin().getPosition();
  }

  @Override
  public Position getEnd() {
    return this.dungeon.getEnd().getPosition();
  }

  @Override
  public Map<String, Object> getPlayerInformation() {
    Map<String, Object> info = new HashMap<>();
    Position position = this.dungeon.getPlayer().getPosition();
    info.put("position", position);
    Map<String, Integer> treasureInfo = new HashMap<>();
    List<Treasure> treasures = this.dungeon.getPlayer().getTreasures();
    for (Treasure treasure : treasures) {
      if (treasureInfo.containsKey(treasure.getType())) {
        treasureInfo.put(treasure.getType(), treasureInfo.get(treasure.getType()) + 1);
      } else {
        treasureInfo.put(treasure.getType(), 1);
      }
    }
    int arrows = this.dungeon.getPlayer().getArrowCnt();
    treasureInfo.put("Arrow", arrows);
    info.put("treasure", treasureInfo);
    return info;
  }

  @Override
  public void startGame(Scanner in, Appendable out, String[] args) throws Exception {
    DungeonControllerImpl.getInstance().parseCommonLine(args);
    this.guiViews = new GuiViewsImpl(blockingCommandQueue, row, col, cellFilePath);
    this.textViews = new TextViewsImpl(out);
    this.guiViews.setBeginAndEnd(getBegin(), getEnd());
    this.textViews.setBeginAndEnd(getBegin(), getEnd());
    new Thread(new Runnable() {
      @Override
      public void run() {
        label:
        while (true) {
          boolean gameover = false;
          while (!gameover) {
            Map<String, Object> playerInfo = DungeonControllerImpl
                    .getInstance().getPlayerInformation();
            guiViews.setPlayerPosition((Position) playerInfo.get("position"));
            textViews.setPlayerPosition((Position) playerInfo.get("position"));
            Map<String, Integer> treasureInfo = (Map<String, Integer>) playerInfo.get("treasure");
            guiViews.setTreasure(treasureInfo);
            textViews.setTreasure(treasureInfo);
            Set<Direction> availableDirection = DungeonControllerImpl
                    .getInstance().getAvailableDirection();
            guiViews.setAvailableMove(availableDirection);
            textViews.setAvailableMove(availableDirection);
            Map<String, Integer> treasures = DungeonControllerImpl.getInstance().getPickUp();
            guiViews.setPickUp(treasures);
            textViews.setPickUp(treasures);
            int smellLevel = DungeonControllerImpl.getInstance().getOtyughs();
            guiViews.setSmell(smellLevel);
            guiViews.showInfo();
            textViews.setSmell(smellLevel);
            textViews.showInfo();
            try {
              String command = blockingCommandQueue.take();
              switch (command) {
                case Constant.MOVE:
                  String dir = blockingCommandQueue.take();
                  Direction direction = null;
                  try {
                    direction = Direction.valueOf(dir);
                  } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                  }
                  DungeonControllerImpl.getInstance().playerMove(direction);
                  break;
                case Constant.SHOOT:
                  int caveCnt = 1;
                  try {
                    caveCnt = Integer.parseInt(blockingCommandQueue.take());
                  } catch (NumberFormatException numberFormatException) {
                    numberFormatException.printStackTrace();
                  }
                  String shootDir = blockingCommandQueue.take();
                  Direction shootDirection = null;
                  try {
                    shootDirection = Direction.valueOf(shootDir);
                  } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                  }
                  boolean shoot = DungeonControllerImpl.getInstance()
                          .playerShoot(caveCnt, shootDirection);
                  guiViews.shootResult(shoot, DungeonControllerImpl.getInstance().getArrowsCnt());
                  textViews.shootResult(shoot, DungeonControllerImpl.getInstance().getArrowsCnt());
                  break;
                case Constant.PICK:
                  String treasure = blockingCommandQueue.take();
                  DungeonControllerImpl.getInstance().playerPickUp(treasure);
                  guiViews.pickUpResult(treasure, DungeonControllerImpl
                          .getInstance().playerPickUp(treasure));
                  textViews.pickUpResult(treasure, DungeonControllerImpl
                          .getInstance().playerPickUp(treasure));
                  break;
                // restart the game and call the corresponding InitGame()
                case Constant.RESTART:
                  // the parameters are unchanged
                  DungeonControllerImpl.getInstance().parseCommonLine(args);
                  ((GuiViewsImpl) guiViews).setVisible(false);
                  guiViews = new GuiViewsImpl(blockingCommandQueue, row, col, cellFilePath);
                  textViews = new TextViewsImpl(out);
                  guiViews.setBeginAndEnd(getBegin(), getEnd());
                  textViews.setBeginAndEnd(getBegin(), getEnd());
                  break;
                case Constant.SETTING:
                  String[] newArgs = new String[7];
                  for (int i = 0; i < newArgs.length; ++i) {
                    newArgs[i] = blockingCommandQueue.take();
                  }
                  DungeonControllerImpl.getInstance().parseCommonLine(newArgs);

                  ((GuiViewsImpl) guiViews).setVisible(false);
                  guiViews = new GuiViewsImpl(blockingCommandQueue, row, col, cellFilePath);
                  textViews = new TextViewsImpl(out);
                  guiViews.setBeginAndEnd(getBegin(), getEnd());
                  textViews.setBeginAndEnd(getBegin(), getEnd());
                  break;
                case Constant.REUSE:
                  dungeon.reuse();
                  ((GuiViewsImpl) guiViews).setVisible(false);
                  guiViews = new GuiViewsImpl(blockingCommandQueue, row, col, cellFilePath);
                  textViews = new TextViewsImpl(out);
                  guiViews.setBeginAndEnd(getBegin(), getEnd());
                  textViews.setBeginAndEnd(getBegin(), getEnd());
                  break;
                default:
                  break;
              }
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            gameover = DungeonControllerImpl.getInstance().gameOver();
          }
          // output if the player is winning the game
          guiViews.result(DungeonControllerImpl.getInstance().isWin());
          textViews.result(DungeonControllerImpl.getInstance().isWin());
          try {
            String command = blockingCommandQueue.take();
            switch (command) {
              // restart the game and call the corresponding InitGame()
              case Constant.RESTART:
                // the parameters are unchanged
                DungeonControllerImpl.getInstance().parseCommonLine(args);
                ((GuiViewsImpl) guiViews).setVisible(false);
                guiViews = new GuiViewsImpl(blockingCommandQueue, row, col, cellFilePath);
                textViews = new TextViewsImpl(out);
                guiViews.setBeginAndEnd(getBegin(), getEnd());
                textViews.setBeginAndEnd(getBegin(), getEnd());
                break;
              case Constant.SETTING:
                String[] newArgs = new String[6];
                for (int i = 0; i < newArgs.length; ++i) {
                  newArgs[i] = blockingCommandQueue.take();
                }
                DungeonControllerImpl.getInstance().parseCommonLine(newArgs);
                ((GuiViewsImpl) guiViews).setVisible(false);
                guiViews = new GuiViewsImpl(blockingCommandQueue, row, col, cellFilePath);
                textViews = new TextViewsImpl(out);
                guiViews.setBeginAndEnd(getBegin(), getEnd());
                textViews.setBeginAndEnd(getBegin(), getEnd());
                break;
              case Constant.REUSE:
                dungeon.reuse();
                ((GuiViewsImpl) guiViews).setVisible(false);
                guiViews = new GuiViewsImpl(blockingCommandQueue, row, col, cellFilePath);
                textViews = new TextViewsImpl(out);
                textViews.setBeginAndEnd(getBegin(), getEnd());
                guiViews.setBeginAndEnd(getBegin(), getEnd());
                break label;
              default:
                throw new IllegalStateException("Unexpected value: " + command);
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }

}
