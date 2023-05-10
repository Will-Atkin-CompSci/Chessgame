import java.awt.*;
import javax.swing.*;

public class ChessGame {
    
    private JFrame frame;
    private JPanel chessBoard;
    private JLabel[][] chessSquares = new JLabel[8][8];
    private ImageIcon[] pieceImageIcons = new ImageIcon[12];
    private int[][] chessBoardArray = {
        {2, 3, 4, 5, 6, 4, 3, 2},
        {1, 1, 1, 1, 1, 1, 1, 1},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0},
        {-1, -1, -1, -1, -1, -1, -1, -1},
        {-2, -3, -4, -5, -6, -4, -3, -2}
    };
    private int currentPlayer = 1; // 1 for white, -1 for black
    
    public ChessGame() {
        frame = new JFrame("Chess Game");
        chessBoard = new JPanel(new GridLayout(8, 8));
        
        // load piece images
        pieceImageIcons[0] = new ImageIcon("images/w_pawn.png");
        pieceImageIcons[1] = new ImageIcon("images/w_rook.png");
        pieceImageIcons[2] = new ImageIcon("images/w_knight.png");
        pieceImageIcons[3] = new ImageIcon("images/w_bishop.png");
        pieceImageIcons[4] = new ImageIcon("images/w_queen.png");
        pieceImageIcons[5] = new ImageIcon("images/w_king.png");
        pieceImageIcons[6] = new ImageIcon("images/b_pawn.png");
        pieceImageIcons[7] = new ImageIcon("images/b_rook.png");
        pieceImageIcons[8] = new ImageIcon("images/b_knight.png");
        pieceImageIcons[9] = new ImageIcon("images/b_bishop.png");
        pieceImageIcons[10] = new ImageIcon("images/b_queen.png");
        pieceImageIcons[11] = new ImageIcon("images/b_king.png");
        
        // create chess board
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JLabel square = new JLabel();
                chessSquares[row][col] = square;
                chessBoard.add(square);
                
                // set background color
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }
                
                // set piece image
                int piece = chessBoardArray[row][col];
                if (piece != 0) {
                    square.setIcon(pieceImageIcons[getPieceIndex(piece)]);
                }
                
                // add click listener
                square.addMouseListener(new ChessSquareClickListener(row, col));
            }
        }
        
        // add chess board to frame
        frame.add(chessBoard);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    private int getPieceIndex(int piece) {
        // returns the index of the piece image icon in the pieceImageIcons array
        switch (piece) {
            case 1:
                return 0; // white pawn
            case 2:
                return 1; // white rook
            case 3:
                return 2; // white knight
            case 4:
                return 3; // white bishop
            case 5:
                return 4; // white queen
            case 6:
                return 5; // white king
            case -1:
                return 6; // black pawn
            case -2:
                return 7; // black rook
            case -3:
                return 8; // black knight
            case -4:
                return 9; // black bishop
            case -5:
                return 10; // black queen
            case -6:
                return 11; // black king
            default:
                return -1;
        }
    }
    
    private void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        // move piece from (fromRow, fromCol) to (toRow, toCol)
        int piece = chessBoardArray[fromRow][fromCol];
        chessBoardArray[fromRow][fromCol] = 0;
        chessBoardArray[toRow][toCol] = piece;
        chessSquares[fromRow][fromCol].setIcon(null);
        chessSquares[toRow][toCol].setIcon(pieceImageIcons[getPieceIndex(piece)]);
    }
    
    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        // check if move from (fromRow, fromCol) to (toRow, toCol) is valid
        int piece = chessBoardArray[fromRow][fromCol];
        if (piece == 0) {
            return false;
        }
        if (chessBoardArray[toRow][toCol] * piece > 0) {
            return false; // can't take own piece
        }
        switch (Math.abs(piece)) {
            case 1: // pawn
                int direction = piece > 0 ? -1 : 1;
                if (fromCol == toCol) { // moving straight
                    if (chessBoardArray[toRow][toCol] != 0) {
                        return false; // can't move forward if square occupied
                    }
                    if (fromRow + direction == toRow) {
                        return true; // can move one square forward
                    }
                    if (fromRow + 2 * direction == toRow && fromRow == (piece > 0 ? 6 : 1) && chessBoardArray[fromRow + direction][toCol] == 0) {
                        return true; // can move two squares forward if on starting rank and square in between is empty
                    }
                } else if (Math.abs(toCol - fromCol) == 1 && fromRow + direction == toRow && chessBoardArray[toRow][toCol] * piece < 0) {
                    return true; // can take diagonally
                }
                return false;
            case 2: // rook
                if (fromRow != toRow && fromCol != toCol) {
                    return false; // can only move horizontally or vertically
                }
                if (fromRow == toRow) { // moving horizontally
                    int startCol = Math.min(fromCol, toCol);
                    int endCol = Math.max(fromCol, toCol);
                    for (int col =
    
