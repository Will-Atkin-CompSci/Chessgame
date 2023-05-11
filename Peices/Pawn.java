package Peices;

/*import javax.imageio.ImageIO;
import java.io.IOException;*/
import java.awt.image.BufferedImage;

import Main.Board;

public class Pawn extends Peice{
  public Pawn(Board board, int col, int row, boolean isWhite) {
    super(board);
    this.col = col;
    this.row = row;
    this.xPos = col * board.tileSize;
    this.yPos = row * board.tileSize;

    this.isWhite = isWhite;
    this.name = "Pawn";

    this.sprite = sheet.getSubimage(5 * sheetScale, isWhite ? 0 : sheetScale, sheetScale, sheetScale).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
  }

  public boolean isValidMovement(int col, int row) {
    
    int colorIndex = isWhite ? 1 : -1;

    //push pawn 1
    if(this.col == col && row == this.row - colorIndex && board.getPeice(col, row) == null) {
      return true;
    }
    //push pawn 2
    if(IsFirstMove && this.col == col && row == this.row - colorIndex * 2 && board.getPeice(col, row) == null && board.getPeice(col, row + colorIndex) == null) {
      return true;
    }
    //capture left
    if(col == this.col - 1 && row == this.row - colorIndex && board.getPeice(col, row) != null) {
      return true;
    }
    //capture right
    if(col == this.col + 1 && row == this.row - colorIndex && board.getPeice(col, row) != null) {
      return true;
    }
    //en Passant left
    if(board.getTileNum(col, row) == board.enPasseantTile && col == this.col - 1 && row == this.row - colorIndex && board.getPeice(col, row + colorIndex) != null) {
      return true;
    }
    //en Passant right
    if(board.getTileNum(col, row) == board.enPasseantTile && col == this.col + 1 && row == this.row - colorIndex && board.getPeice(col, row + colorIndex) != null) {
      return true;
    }



    return false;
  }
}
