package org.example.Game;

public class Game {
    private final Board board;
    public static Player player1 = null;
    public static Player player2 = null;
    public static Player currentPlayer;

    public Game() {
        board = new Board(7, 7);
    }

    public static Player otherPlayer() {
        if(currentPlayer == player1) return player2;
        else return player1;
    }

    public boolean makeMove(Player player, int x, int y) {
        boolean value = false;
        if (board.isValidMove(x, y)) {
            board.placePiece(player.getPiece(x, y));
            value = true;
            if (board.isFull()) {
                System.out.println("The game is a draw!");
            }
        }
        return value;
    }

    public int getNumPlayers() {
        if (player1 == null && player2 == null) return 0;
        else if (player2 != null) return 2;
        else return 1;
    }

    public void addPlayer(Player player) {
        if (player1 == null) {
            player1 = player;
            player1.setColor(PieceColor.B);
            currentPlayer = player;
        } else if (player2 == null) {
            player2 = player;
            player2.setColor(PieceColor.A);
            player1.startTime();
        }
    }

    public Board getBoard() {
        return board;
    }

}
