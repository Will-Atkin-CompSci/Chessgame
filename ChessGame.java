import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

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
// implement move validation logic 
return true;
}

private void setPieceIcon(JButton btn, String pieceName) {
try {
if (pieceName == null) {
    btn.setIcon(null);
    return;
}

String filename = "/images/" + pieceName + ".png";
BufferedImage image = ImageIO.read(new File(filename));
btn.setIcon(new ImageIcon(image));
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