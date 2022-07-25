package views;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import navigation.Position;

/**
 * Encapsulates the position displayed on the GUI that can be reached by the player.
 *
 * @author Pengbo Wang
 */
public class PlaceJlabel extends JLabel {
  private final Position position;
  private boolean visible;

  /**
   * The constructor of this object.
   *
   * @param image the picture that needs to be shown to the user
   * @param row row of dungeon
   * @param col column of dungeon
   */
  public PlaceJlabel(ImageIcon image, int row, int col) {
    super(image);
    this.visible = false;
    this.position = new Position(row, col);
  }

  /**
   * Get the current position of the player.
   *
   * @return current position
   */
  public Position getPosition() {
    return this.position;
  }

  /**
   * Visualize the position on the dungeon.
   */
  public void visible() {
    this.visible = true;
  }

  /**
   * Check if the position is visible.
   *
   * @return whether it is visible
   */
  public boolean isItVisible() {
    return this.visible;
  }
}
