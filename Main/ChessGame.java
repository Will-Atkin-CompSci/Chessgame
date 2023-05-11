package Main;

import java.awt.*;
import javax.swing.*;

public class ChessGame {

  public static void main(String[] args) {

    JFrame frame = new JFrame("Two Player Chess");
    frame.getContentPane().setBackground(Color.black);
    frame.setLayout(new GridBagLayout());
    frame.setMinimumSize(new Dimension(1000, 1000));
    frame.setLocationRelativeTo(null);

    Board board = new Board();
    frame.add(board);

    frame.setVisible(true);
  }
}