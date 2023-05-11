package Peices;

import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import Main.Board;

public class Peice {
  
  public int col, row;
  public int xPos, yPos;

  public boolean isWhite;
  public String name;
  public int value;

public boolean IsFirstMove = true;

  BufferedImage sheet;
  {
    try{
      sheet = ImageIO.read(ClassLoader.getSystemResourceAsStream("Res/pieces.png"));
    } catch (IOException error) {
      error.printStackTrace();
    }
  }
  protected int sheetScale = sheet.getWidth() / 6;

  Image sprite;
  Board board;

  public Peice(Board board) {
    this.board = board;
  }

  public boolean isValidMovement(int col, int row) {
    return true;
  }

  public boolean peiceCollision (int col, int row) {
    return false;
  }

  public void paint(Graphics2D g2d) {

    g2d.drawImage(sprite, xPos, yPos, null);
  }
}
