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
    private final PrintWriter out;
    private Player player;

    public ClientTask(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.player = null;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            Thread listenerThread = new Thread(() -> {
                try {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        processInput(inputLine);
                    }
                } catch (IOException e) {
                    System.out.println("Error reading input from client: " + e.getMessage());
                }
            });

            listenerThread.start();

            String userInput;
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
            }

            System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
            listenerThread.join(); // Wait for the listener thread to finish
        } catch (IOException | InterruptedException e) {
            System.out.println("Error handling client connection: " + e.getMessage());
        }
    }

    private void processInput(String inputLine) {
        if (inputLine.equals("stop")) {
            out.println("Server stopped");
            sendToAll("Server stopped");
            GameServer.game = null; // Stop the game
        } else if (inputLine.equals("create game")) {
            createGame();
        } else if (inputLine.equals("join game")) {
            joinGame();
        } else if (inputLine.startsWith("submit move ")) {
            submitMove(inputLine);
        } else {
            out.println("Error: unknown command");
        }
    }

    private void createGame() {
        if (GameServer.game == null) {
            GameServer.game = new Game();
            out.println("Game created");
            sendToAll("Game created");
            Board.printBoard();
        } else {
            out.println("Error: game already exists");
        }
    }

    private PrintWriter getOut() {
        return out;
    }

    private void sendToAll(String message) {
        for (ClientTask client : GameServer.connections) {
            client.getOut().println(message);
        }
    }

    private void joinGame() {
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

    private void submitMove(String inputLine) {
        if (GameServer.game == null) {
            out.println("Error: game does not exist");
        } else if (GameServer.game.getNumPlayers() != 2) {
            out.println("Error: you are the only player");
        } else if (player == null) {
            out.println("Error: you have not joined the game");
        } else if (GameServer.game.currentPlayer != player) {
            out.println("Error: Not your turn");
        } else if (player.getTime() < 0) {
            sendToAll("Error: " + player.getName() + " is out of time, game ends");
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
                        GameServer.game.otherPlayer().startTime();
                        Board.printBoard();
                        if (GameServer.game.getBoard().hasWon(player)) {
                            GameServer.game = null;
                            sendToAll(player.getName() + " won !!!");
                        } else {
                            out.println("Move submitted");
                            GameServer.game.otherPlayer().getOut().println("Opponent submitted " + tokens[2] + " " + tokens[3]);
                            GameServer.game.otherPlayer().getOut().println("Your turn :");
                            GameServer.game.currentPlayer = GameServer.game.otherPlayer();
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
}
