package Main;

import java.awt.event.*;
import Peices.Peice;

public class Input extends MouseAdapter{

  Board board;

  public Input(Board board) {
    this.board = board;
  }

  @Override
  public void mousePressed(MouseEvent e) {

    int col = e.getX() / board.tileSize;
    int row = e.getY() / board.tileSize;

    Peice pieceXY = board.getPeice(col, row);
    if ( pieceXY != null) {
      board.selecetedPiece = pieceXY;
    }

  }

  @Override
  public void mouseDragged(MouseEvent e) {
      if (board.selecetedPiece != null) {
        board.selecetedPiece.xPos = e.getX() - board.tileSize / 2;
        board.selecetedPiece.yPos = e.getY() - board.tileSize / 2;

        board.repaint();
      }
  }

  @Override
  public void mouseReleased(MouseEvent e) {

    int col = e.getX() / board.tileSize;
    int row = e.getY() / board.tileSize;

    if (board.selecetedPiece != null) {
      Move move = new Move (board, board.selecetedPiece, col, row);

      if (board.isValidMove(move)) {
        board.makeMove(move);
      } else {
        board.selecetedPiece.xPos = board.selecetedPiece.col * board.tileSize;
        board.selecetedPiece.yPos = board.selecetedPiece.row * board.tileSize;
      }
    }

    board.selecetedPiece = null;
    board.repaint();
    
  }
  
}
