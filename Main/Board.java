package Main;


import java.awt.*;
import javax.swing.*;

import Peices.Bishop;
import Peices.King;
import Peices.Knight;
import Peices.Pawn;
import Peices.Peice;
import Peices.Queen;
import Peices.Rook;

import java.util.ArrayList;

public class Board extends JPanel{
  
  public int tileSize = 85;

  int cols = 8;
  int rows = 8;

  ArrayList<Peice> peiceList = new ArrayList<>();

  public Peice selecetedPiece;

  Input input = new Input(this);

  public int enPasseantTile = -1;

  // make the board
  public Board() {
    this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));

    this.addMouseListener(input);
    this.addMouseMotionListener(input);

    addPeices();
  }

  // select peice
  public Peice getPeice(int col, int row) {

    for (Peice piece : peiceList) {
      if(piece.col == col && piece.row == row) {
        return piece;
      }
    }
    return null;
  }

  // move a peice
  public void makeMove(Move move) {

    if(move.piece.name.equals("Pawn")) {
      movePawn(move);
    } else {
    move.piece.col = move.newCol;
    move.piece.row = move.newRow;
    move.piece.xPos = move.newCol * tileSize;
    move.piece.yPos = move.newRow * tileSize;

    move.piece.IsFirstMove = false;

    capture(move.capture);
    }
  }

  // move pawn method
  private void movePawn(Move move) {

    // enPassant
    int colorIndex = move.piece.isWhite ? 1 : -1;

    if(getTileNum(move.newCol, move.newRow) == enPasseantTile) {
      move.capture = getPeice(move.newCol, move.newRow + colorIndex);
    }

    if(Math.abs(move.piece.row - move.newRow) == 2) {
      enPasseantTile = getTileNum(move.newCol, move.newRow + colorIndex);
    } else {
      enPasseantTile = -1;
    }

    // Promotions
    colorIndex = move.piece.isWhite ? 0 : 7;
    if (move.newRow == colorIndex) {
      promotePawn(move);
    }

    move.piece.col = move.newCol;
    move.piece.row = move.newRow;
    move.piece.xPos = move.newCol * tileSize;
    move.piece.yPos = move.newRow * tileSize;

    move.piece.IsFirstMove = false;

    capture(move.capture);
  }

  // promote pawn method
  private void promotePawn(Move move) {
    peiceList.add(new Queen(this, move.newCol, move.newRow, move.piece.isWhite));
    capture(move.piece);
  }

  // capture peice
  public void capture(Peice peice) {
    peiceList.remove(peice);
  }


  // valid move checker
  public boolean isValidMove(Move move) {

    if (sameTeam(move.piece, move.capture)) {
      return false;
    }

    if (!move.piece.isValidMovement(move.newCol, move.newRow)) {
      return false;
    }
    if (move.piece.peiceCollision(move.newCol, move.newRow)) {
      return false;
    }

    return true;
  }


  // same team checker
  public boolean sameTeam(Peice p1, Peice p2) {
    if (p1 == null || p2 == null) {
      return false;
    }
    return p1.isWhite == p2.isWhite;
  }

  // getting tile number
  public int getTileNum(int col, int row) {
    return row * rows + col;
  }

  // add the peices to the arraylist
  public void addPeices() {

    //Black Peices
    peiceList.add(new Rook( this, 0, 0, false));
    peiceList.add(new Knight(this, 1, 0, false));
    peiceList.add(new Bishop(this, 2, 0, false));
    peiceList.add(new Queen(this, 3, 0, false));
    peiceList.add(new King(this, 4, 0, false));
    peiceList.add(new Bishop(this, 5, 0, false));
    peiceList.add(new Knight(this, 6, 0, false));
    peiceList.add(new Rook(this, 7, 0, false));

    peiceList.add(new Pawn( this, 0, 1, false));
    peiceList.add(new Pawn( this, 1, 1, false));
    peiceList.add(new Pawn( this, 2, 1, false));
    peiceList.add(new Pawn( this, 3, 1, false));
    peiceList.add(new Pawn( this, 4, 1, false));
    peiceList.add(new Pawn( this, 5, 1, false));
    peiceList.add(new Pawn( this, 6, 1, false));
    peiceList.add(new Pawn( this, 7, 1, false));


    //White Peices
    peiceList.add(new Rook( this, 0, 7, true));
    peiceList.add(new Knight(this, 1, 7, true));
    peiceList.add(new Bishop(this, 2, 7, true));
    peiceList.add(new King(this, 3, 7, true));
    peiceList.add(new Queen(this, 4, 7, true));
    peiceList.add(new Bishop(this, 5, 7, true));
    peiceList.add(new Knight(this, 6, 7, true));
    peiceList.add(new Rook(this, 7, 7, true));

    peiceList.add(new Pawn( this, 0, 6, true));
    peiceList.add(new Pawn( this, 1, 6, true));
    peiceList.add(new Pawn( this, 2, 6, true));
    peiceList.add(new Pawn( this, 3, 6, true));
    peiceList.add(new Pawn( this, 4, 6, true));
    peiceList.add(new Pawn( this, 5, 6, true));
    peiceList.add(new Pawn( this, 6, 6, true));
    peiceList.add(new Pawn( this, 7, 6, true));
  }


  // paint components
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    // paint the board
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        g2d.setColor((c + r) % 2 == 0 ? new Color(227, 198, 181) : new Color(187, 105, 83));
        g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
      }
    }

 // paint valid moves
    if (selecetedPiece != null) {
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        
        if (isValidMove(new Move(this, selecetedPiece, c, r))) {

          g2d.setColor(new Color(68, 180, 57, 190));
          g2d.fillRect( c * tileSize, r * tileSize, tileSize, tileSize);

        }

      }
    }
  }

  // paint peices onto the board
    for (Peice peice : peiceList) {
        peice.paint(g2d);
    }

  }
}
