package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
    private final String serverHost;
    private final int serverPort;

    public GameClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void start() {
        try (Socket socket = new Socket(serverHost, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to server: " + socket.getInetAddress().getHostAddress());

            String inputLine;
            while (true) {
                System.out.print("> ");
                inputLine = scanner.nextLine();
                out.println(inputLine);

                if (inputLine.equals("exit")) {
                    break;
                }

                String response = in.readLine();
                System.out.println("Server response: " + response);
            }
        } catch (Exception e) {
            System.out.println("Error communicating with server: " + e.getMessage());
        }
    }

    public void createGame(String playerName) {
        try (Socket socket = new Socket(serverHost, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send the "create" command to the server with the player name
            out.println("create " + playerName);

            String response = in.readLine();
            System.out.println("Server response: " + response);
        } catch (Exception e) {
            System.out.println("Error communicating with server: " + e.getMessage());
        }
    }

    public void joinGame(String playerName, int gameId) {
        try (Socket socket = new Socket(serverHost, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send the "join" command to the server with the player name and game ID
            out.println("join " + playerName + " " + gameId);

            String response = in.readLine();
            System.out.println("Server response: " + response);
        } catch (Exception e) {
            System.out.println("Error communicating with server: " + e.getMessage());
        }
    }

    public void submitMove(int x, int y) {
        try (Socket socket = new Socket(serverHost, serverPort);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            // Send the "move" command to the server with the coordinates
            out.println("move " + x + " " + y);

            String response = in.readLine();
            System.out.println("Server response: " + response);
        } catch (Exception e) {
            System.out.println("Error communicating with server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 5000;
        GameClient client = new GameClient(serverHost, serverPort);
        client.start();
    }
}

