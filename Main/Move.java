package Main;

import Peices.Peice;

public class Move {
  
  int oldCol;
  int oldRow;
  int newCol;
  int newRow;

  Peice piece;
  Peice capture;

  public Move(Board board, Peice piece, int newCol, int newRow) {

    this.oldCol = piece.col;
    this.oldRow = piece.row;
    this.newCol = newCol;
    this.newRow = newRow;

    this.piece = piece;
    this.capture = board.getPeice(newCol, newRow);
  }
}
