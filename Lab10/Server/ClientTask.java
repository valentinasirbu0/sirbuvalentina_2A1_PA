package org.example;

import org.example.Game.Board;
import org.example.Game.Game;
import org.example.Game.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTask implements Runnable {
    private final Socket clientSocket;
    private Player player;

    public ClientTask(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.player = null;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                if (inputLine.equals("stop")) {
                    out.println("Server stopped");
                    break;
                } else if (inputLine.equals("create game")) {
                    createGame(out);
                } else if (inputLine.equals("join game")) {
                    joinGame(out);
                } else if (inputLine.startsWith("submit move ")) {
                    submitMove(out, inputLine);
                } else {
                    out.println("Error: unknown command");
                }
            }
            System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            System.out.println("Error handling client connection: " + e.getMessage());
        }
    }

    private void createGame(PrintWriter out) {
        if (GameServer.game == null) {
            GameServer.game = new Game();
            out.println("Game created");
            Board.printBoard();
        } else {
            out.println("Error: game already exists");
        }
    }

    private void joinGame(PrintWriter out) {
        if (GameServer.game == null) {
            out.println("Error: game does not exist");
        } else {
            if (GameServer.game.getNumPlayers() < 1) {
                player = new Player("Player 1", out);
                GameServer.game.addPlayer(player);
                out.println("Game joined , Welcome " + player.getName());
            } else if (GameServer.game.getNumPlayers() == 1) {
                player = new Player("Player 2", out);
                GameServer.game.addPlayer(player);
                out.println("Game joined , Welcome " + player.getName());
            } else {
                out.println("Error: game is full");
            }
        }
    }

    private void submitMove(PrintWriter out, String inputLine) {
        if (GameServer.game == null) {
            out.println("Error: game does not exist");
        } else if (GameServer.game.getNumPlayers() != 2) {
            out.println("Error: you are the only player");
        } else if (player == null) {
            out.println("Error: you have not joined the game");
        } else if (Game.currentPlayer != player) {
            out.println("Error: Not your turn");
        } else if (player.getTime() < 0) {
            out.println("Error: Out of time");
            // Stop the game here
            GameServer.game = null;
        } else {
            String[] tokens = inputLine.split(" ");
            if (tokens.length != 4) {
                out.println("Error: invalid move format");
            } else {
                try {
                    int x = Integer.parseInt(tokens[2]);
                    int y = Integer.parseInt(tokens[3]);
                    if (GameServer.game.makeMove(player, x, y)) {
                        player.countTime();
                        Game.otherPlayer().startTime();
                        Game.currentPlayer = Game.otherPlayer();
                        Board.printBoard();
                        if (GameServer.game.getBoard().hasWon(player)) {
                            announceWinner(player);
                        } else {
                            out.println("Move submitted");
                        }
                    } else {
                        out.println("Error: invalid move");
                    }
                } catch (NumberFormatException e) {
                    out.println("Error: invalid move format");
                }
            }
        }
    }


    private void announceWinner(Player winner) {
        String message = winner.getName() + " has won!";
        Game.player1.getOut().println(message);
        Game.player2.getOut().println(message);
    }
}
