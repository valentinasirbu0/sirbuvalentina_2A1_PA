package org.example.Game;

public class Board {
    public static Piece[][] grid;
    private static int numRows;
    private static int numCols;

    public Board(int numRows, int numCols) {
        Board.numRows = numRows;
        Board.numCols = numCols;
        grid = new Piece[numRows][numCols];
    }

    public boolean isValidMove(int row, int col) {
        if (row < 0 || row >= numRows || col < 0 || col >= numCols) {
            return false;
        }
        return grid[row][col] == null;
    }

    public void placePiece(Piece piece) {
        grid[piece.row()][piece.col()] = piece;
    }

    public boolean hasWon(Player player) {
        // check for five in a row horizontally
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col <= numCols - 5; col++) {
                if (checkSequence(player, row, col, 1, 0)) {
                    return true;
                }
            }
        }

        // check for five in a row vertically
        for (int row = 0; row <= numRows - 5; row++) {
            for (int col = 0; col < numCols; col++) {
                if (checkSequence(player, row, col, 0, 1)) {
                    return true;
                }
            }
        }

        // check for five in a row diagonally (top-left to bottom-right)
        for (int row = 0; row <= numRows - 5; row++) {
            for (int col = 0; col <= numCols - 5; col++) {
                if (checkSequence(player, row, col, 1, 1)) {
                    return true;
                }
            }
        }

        // check for five in a row diagonally (top-right to bottom-left)
        for (int row = 0; row <= numRows - 5; row++) {
            for (int col = 4; col < numCols; col++) {
                if (checkSequence(player, row, col, 1, -1)) {
                    return true;
                }
            }
        }

        // no winning sequence found
        return false;
    }

    private boolean checkSequence(Player player, int startRow, int startCol, int rowIncrement, int colIncrement) {
        for (int i = 0; i < 5; i++) {
            int row = startRow + i * rowIncrement;
            int col = startCol + i * colIncrement;
            if (grid[row][col] == null || grid[row][col].owner() != player) {
                return false;
            }
        }
        return true;
    }

    public boolean isFull() {
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (grid[row][col] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void printBoard() {
        System.out.print("   ");
        for (int col = 0; col < numCols; col++) {
            System.out.print((col < 10 ? " " : "") + col + " ");
        }
        System.out.println();
        for (int row = 0; row < numRows; row++) {
            System.out.print((row < 10 ? " " : "") + row + " ");
            for (int col = 0; col < numCols; col++) {
                Piece piece = grid[row][col];
                if (piece == null) {
                    System.out.print(" . ");
                } else {
                    System.out.print(" " + piece.owner().getColor() + " ");
                }
            }
            System.out.println();
        }
    }
}
