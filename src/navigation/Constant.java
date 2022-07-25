package navigation;

import java.util.HashMap;
import java.util.Map;

/**
 * Implement a constant class that match the picture
 * to the view of the dungeon one by one.
 *
 * @author Pengbo Wang
 */
public class Constant {

  public static final String RESTART = "RESTART";

  public static final String SETTING = "SETTING";

  public static final String REUSE = "REUSE";

  public static final String PLAYER_CELL = "BW_CELL/";

  public static final String CELL = "COLOR_CELL/";

  public static final String BLANK = "blank.png";

  public static final String E = "E.png";

  public static final String ES = "ES.png";

  public static final String ESW = "ESW.png";

  public static final String EW = "EW.png";

  public static final String N = "N.png";

  public static final String NE = "NE.png";

  public static final String NES = "NES.png";

  public static final String NESW = "NESW.png";

  public static final String NEW = "NEW.png";

  public static final String NS = "NS.png";

  public static final String S = "S.png";

  public static final String SW = "SW.png";

  public static final String SWN = "SWN.png";

  public static final String W = "W.png";

  public static final String WN = "WN.png";

  public static final String MOVE = "M";

  public static final String SHOOT = "S";

  public static final String PICK = "P";

  public static int TERRIBLESMELL = 2;

  public static int SLIGHTSMELL = 1;

  public static int NOSMELL = 0;

  public static String TERRIBLESMELL_FILE = "stench02.png";

  public static String SLIGHTSMELL_FILE = "stench01.png";

  public static Map<String, String> TREASURE_PATH = new HashMap<>() {{
      put("Ruby", "ruby.png");
      put("Arrow", "arrow-black.png");
      put("Sapphire", "emerald.png");
      put("Diamond", "diamond.png");
    }};

}
