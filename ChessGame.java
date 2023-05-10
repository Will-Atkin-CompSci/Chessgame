//imports
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

//Class Declaration
public class ChessGame implements ActionListener {
    private JFrame frame;
    private JButton[][] chessBoard;
    private int[][] board;
    private int selectedX, selectedY;

    public ChessGame() {
        frame = new JFrame("Chess");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8, 8));
        frame.add(panel);

        chessBoard = new JButton[8][8];
        board = new int[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard[i][j] = new JButton();
                chessBoard[i][j].addActionListener(this);
                panel.add(chessBoard[i][j]);

                if ((i + j) % 2 == 0) {
                    chessBoard[i][j].setBackground(Color.WHITE);
                } else {
                    chessBoard[i][j].setBackground(Color.GRAY);
                }

                if (i == 0 || i == 7) {
                    if (j == 0 || j == 7) {
                        board[i][j] = 4;
                        setPieceIcon(chessBoard[i][j], "rook");
                    } else if (j == 1 || j == 6) {
                        board[i][j] = 3;
                        setPieceIcon(chessBoard[i][j], "knight");
                    } else if (j == 2 || j == 5) {
                        board[i][j] = 2;
                        setPieceIcon(chessBoard[i][j], "bishop");
                    } else if (j == 3) {
                        board[i][j] = 5;
                        setPieceIcon(chessBoard[i][j], "queen");
                    } else {
                        board[i][j] = 6;
                        setPieceIcon(chessBoard[i][j], "king");
                    }
                } else if (i == 1 || i == 6) {
                    board[i][j] = 1;
                    setPieceIcon(chessBoard[i][j], "pawn");
                }
            }
        }

        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        int x = -1, y = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (btn == chessBoard[i][j]) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }

        if (x == -1 || y == -1) {
            return;
        }

        if (selectedX == -1 && selectedY == -1) {
            if (board[x][y] == 0) {
                return;
            }

            selectedX = x;
            selectedY = y;
            btn.setBackground(Color.YELLOW);
        } else {
            if (x == selectedX && y == selectedY) {
                btn.setBackground((x + y) % 2 == 0 ? Color.WHITE : Color.GRAY);
                
            } else if (isValidMove(selectedX, selectedY, x, y)) {
                board[x][y] = board[selectedX][selectedY];
                board[selectedX][selectedY] = 0;
                setPieceIcon(chessBoard[x][y], getPieceName(board[x][y]));
                setPieceIcon(chessBoard[selectedX][selectedY], null);

                btn.setBackground((x + y) % 2 == 0 ? Color.WHITE : Color.GRAY);
                selectedX = -1;
                selectedY = -1;
            }
        }
    }

private boolean isValidMove(int startX, int startY, int endX, int endY) {
  // check if the destination is out of bounds
  if (endX < 0 || endX > 7 || endY < 0 || endY > 7) {
      return false;
  }

  int pieceType = board[startX][startY];

  // check if the piece is a pawn
  if (pieceType == 1) {
      // check if the pawn is moving forward
      if (startY == endY) {
          int direction = (pieceType > 0) ? -1 : 1;
          int diff = Math.abs(endX - startX);
          if (diff == 1 && board[endX][endY] * pieceType < 0) {
              return true;
          } else if (diff == 2 && startX == (pieceType > 0 ? 6 : 1) && board[startX + direction][startY] == 0 && board[endX][endY] == 0) {
              return true;
          } else if (diff != 1) {
              return false;
          }
          return board[endX][endY] == 0;
      } else { // check if the pawn is capturing a piece diagonally
          int direction = (pieceType > 0) ? -1 : 1;
          int diffX = Math.abs(endX - startX);
          int diffY = Math.abs(endY - startY);
          if (diffX == 1 && diffY == 1 && board[endX][endY] * pieceType < 0) {
              return true;
          } else {
              return false;
          }
      }
  }

  // check if the piece is a knight
  if (pieceType == 3) {
      int diffX = Math.abs(endX - startX);
      int diffY = Math.abs(endY - startY);
      if ((diffX == 2 && diffY == 1) || (diffX == 1 && diffY == 2)) {
          return board[endX][endY] * pieceType <= 0;
      } else {
          return false;
      }
  }

  // check if the piece is a bishop
  if (pieceType == 2) {
      int diffX = Math.abs(endX - startX);
      int diffY = Math.abs(endY - startY);
      if (diffX != diffY) {
          return false;
      }
      int xDir = (endX > startX) ? 1 : -1;
      int yDir = (endY > startY) ? 1 : -1;
      for (int i = startX + xDir, j = startY + yDir; i != endX; i += xDir, j += yDir) {
          if (board[i][j] != 0) {
              return false;
          }
      }
      return board[endX][endY] * pieceType <= 0;
  }

  // check if the piece is a rook
  if (pieceType == 4) {
    int diffX = Math.abs(endX - startX);
    int diffY = Math.abs(endY - startY);
    if (diffX != 0 && diffY != 0) {
        return false;
    }
    if (diffX > 0) {
        int xDir = (endX > startX) ? 1 : -1;
        for (int i = startX + xDir; i != endX; i += xDir) {
            if (board[i][startY] != 0) {
                return false;
            }
        }
    } else {
        int yDir = (endY > startY) ? 1 : -1;
        for (int j = startY + yDir; j != endY; j += yDir) {
            if (board[startX][j] != 0) {
                return false;
            }
        }
    }
    return board[endX][endY] * pieceType <= 0;
}

// check if the piece is a queen
if (pieceType == 5) {
    int diffX = Math.abs(endX - startX);
    int diffY = Math.abs(endY - startY);
    if (diffX != diffY && diffX != 0 && diffY != 0) {
        return false;
    }
    if (diffX == diffY) {
        int xDir = (endX > startX) ? 1 : -1;
        int yDir = (endY > startY) ? 1 : -1;
        for (int i = startX + xDir, j = startY + yDir; i != endX; i += xDir, j += yDir) {
            if (board[i][j] != 0) {
                return false;
            }
        }
    } else if (diffX == 0) {
        int yDir = (endY > startY) ? 1 : -1;
        for (int j = startY + yDir; j != endY; j += yDir) {
            if (board[startX][j] != 0) {
                return false;
            }
        }
    } else {
        int xDir = (endX > startX) ? 1 : -1;
        for (int i = startX + xDir; i != endX; i += xDir) {
            if (board[i][startY] != 0) {
                return false;
            }
        }
    }
    return board[endX][endY] * pieceType <= 0;
}

// check if the piece is a king
if (pieceType == 6) {
    int diffX = Math.abs(endX - startX);
    int diffY = Math.abs(endY - startY);
    if (diffX <= 1 && diffY <= 1) {
        return board[endX][endY] * pieceType <= 0;
    } else {
        return false;
    }
}

return false;
}

private void setPieceIcon(JButton btn, String pieceName) {
try {
if (pieceName == null) {
    btn.setIcon(null);
    return;
}

String home = System.getProperty("user.home");
String filename = home + "\\Downloads\\" + pieceName + ".png";
BufferedImage image = ImageIO.read(new File(filename));
Image scaledImage = image.getScaledInstance(95, 95, Image.SCALE_SMOOTH);
btn.setIcon(new ImageIcon(scaledImage));
} catch (IOException ex) {
System.err.println("Failed to load piece image: " + ex.getMessage());
}
}

private String getPieceName(int pieceType) {
switch (pieceType) {
case 1:
    return "pawn";
case 2:
    return "bishop";
case 3:
    return "knight";
case 4:
    return "rook";
case 5:
    return "queen";
case 6:
    return "king";
default:
    return null;
}
}

public static void main(String[] args) {
new ChessGame();
}
}