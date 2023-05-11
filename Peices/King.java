package Peices;

/*import javax.imageio.ImageIO;
import java.io.IOException;*/
import java.awt.image.BufferedImage;

import Main.Board;

public class King extends Peice{
  public King(Board board, int col, int row, boolean isWhite) {
    super(board);
    this.col = col;
    this.row = row;
    this.xPos = col * board.tileSize;
    this.yPos = row * board.tileSize;

    this.isWhite = isWhite;
    this.name = "King";

    this.sprite = sheet.getSubimage(0 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
  }

  public boolean isValidMovement(int col, int row) {
    return Math.abs((col - this.col) * (row - this.row)) == 1 || Math.abs(col - this.col) + Math.abs(row - this.row) == 1;
  }
}
