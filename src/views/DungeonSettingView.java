package views;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import navigation.Constant;

/**
 * Implement a class that get the setting dungeon GUI.
 *
 * @author Pengbo Wang
 */
public class DungeonSettingView extends JFrame {
  private final JTextField row;
  private final JTextField col;
  private final JTextField interconnectivity;
  private final JRadioButton wrapping;
  private final JRadioButton notWrapping;
  private boolean wrap;
  private final JTextField treasure;
  private final JTextField otyughs;
  private final JButton start;
  private final JButton cancel;
  private final BlockingQueue<String> blockingCommandQueue;

  /**
   * The constructor of set the setting view.
   *
   * @param blockingCommandQueue blocking command queue
   */
  public DungeonSettingView(BlockingQueue<String> blockingCommandQueue) {
    if (blockingCommandQueue == null) {
      throw new IllegalArgumentException("blocking command queue cannot be empty");
    }
    this.blockingCommandQueue = blockingCommandQueue;
    JLabel rowLabel;
    rowLabel = new JLabel("row");
    JLabel colLabel;
    colLabel = new JLabel("col");
    JLabel wrappingLabel;
    wrappingLabel = new JLabel("wrapping");
    JLabel treasureLabel;
    treasureLabel = new JLabel("treasure percentage");
    JLabel connectLabel;
    connectLabel = new JLabel("interconnectivity");
    JLabel otyughsLabel;
    otyughsLabel = new JLabel("number of otyughs");
    this.row = new JTextField();
    this.row.setColumns(5);
    this.col = new JTextField();
    this.col.setColumns(5);
    this.wrapping = new JRadioButton("true");
    this.notWrapping = new JRadioButton("false");
    this.interconnectivity = new JTextField();
    this.interconnectivity.setColumns(5);
    this.treasure = new JTextField();
    this.treasure.setColumns(5);
    this.otyughs = new JTextField();
    this.otyughs.setColumns(5);
    this.start = new JButton("Start");
    this.cancel = new JButton("Cancel");
    ButtonGroup wrappingGroup = new ButtonGroup();
    wrappingGroup.add(wrapping);
    wrappingGroup.add(notWrapping);
    this.add(rowLabel);
    this.add(row);
    this.add(colLabel);
    this.add(col);
    this.add(wrappingLabel);
    this.add(wrapping);
    this.add(notWrapping);
    this.add(connectLabel);
    this.add(interconnectivity);
    this.add(treasureLabel);
    this.add(treasure);
    this.add(otyughsLabel);
    this.add(otyughs);
    this.add(start);
    this.add(cancel);
    this.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 20));
    this.setBounds(300, 300, 300, 300);
    this.setVisible(true);
    this.addListener();
  }

  private void addListener() {
    this.start.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // get parameters
        try {
          blockingCommandQueue.put(Constant.SETTING);
          blockingCommandQueue.put(row.getText());
          blockingCommandQueue.put(col.getText());
          blockingCommandQueue.put(String.valueOf(wrap));
          blockingCommandQueue.put(interconnectivity.getText());
          blockingCommandQueue.put(treasure.getText());
          blockingCommandQueue.put(otyughs.getText());
          dispose();
        } catch (InterruptedException interruptedException) {
          interruptedException.printStackTrace();
        }
      }
    });
    this.cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    this.wrapping.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        wrap = true;
      }
    });
    this.notWrapping.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        wrap = false;
      }
    });
  }

}
