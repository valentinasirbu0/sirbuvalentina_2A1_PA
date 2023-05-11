package org.example;

import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {
    private final int port;
    private boolean isRunning;

    public GameServer(int port) {
        this.port = port;
        this.isRunning = false;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            isRunning = true;
            System.out.println("Server started on port " + port);

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread to handle the client connection
                ClientThread clientThread = new ClientThread(clientSocket);
                clientThread.start();
            }
        } catch (Exception e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    public void stop() {
        isRunning = false;
    }

    public static void main(String[] args) {
        int port = 5000;
        GameServer server = new GameServer(port);
        server.start();
    }
}

