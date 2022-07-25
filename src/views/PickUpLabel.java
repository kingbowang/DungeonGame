package views;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Encapsulate the treasure and arrow displayed on the GUI that can be picked up by the user.
 *
 * @author Pengbo Wang
 */
public class PickUpLabel extends JLabel {
  private final String name;

  /**
   * Initialize this object.
   *
   * @param icon the picture that needs to be shown to the user
   * @param name the type name of this treasure or arrow
   */
  public PickUpLabel(Icon icon, String name) {
    super(icon);
    this.name = name;
  }

  /**
   * Get the type name of this treasure or arrow.
   *
   * @return type name (Ruby/Diamond/Sapphire/Arrow)
   */
  public String getTreasureName() {
    return name;
  }
}
