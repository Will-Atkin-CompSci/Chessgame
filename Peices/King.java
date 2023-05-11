package Peices;

/*import javax.imageio.ImageIO;
import java.io.IOException;*/
import java.awt.image.BufferedImage;

import Main.Board;
import Main.Move;

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
    return Math.abs((col - this.col) * (row - this.row)) == 1 || Math.abs(col - this.col) + Math.abs(row - this.row) == 1 || CanCastle(col, row);
  }

  private boolean CanCastle(int col, int row) {

    if (this.row == row) {
      if (col == 6) {
        Peice rook = board.getPeice(7, row);
        if (rook != null && rook.IsFirstMove && IsFirstMove) {
            return board.getPeice(5, row) == null &&
                   board.getPeice(6, row) == null &&
                   !board.checkScanner.isKingChecked(new Move(board, this, 5, row));
        }
      } else if (col == 2) {
        Peice rook = board.getPeice(0, row);
        if (rook != null && rook.IsFirstMove && IsFirstMove) {
            return board.getPeice(3, row) == null &&
                   board.getPeice(2, row) == null &&
                   board.getPeice(1, row) == null &&
                   !board.checkScanner.isKingChecked(new Move(board, this, 3, row));
        }
      }
    }

    return false;
  }

}
