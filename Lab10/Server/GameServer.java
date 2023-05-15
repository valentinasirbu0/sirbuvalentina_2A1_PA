package org.example;

import org.example.Game.Game;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private final int port;
    private boolean isRunning;
    private final ExecutorService executorService;
    protected static Game game = null;

    public GameServer(int port) {
        this.port = port;
        this.isRunning = false;
        this.executorService = Executors.newFixedThreadPool(10); // create a thread pool with 10 threads
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            isRunning = true;
            System.out.println("Server started on port " + port);

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Submit a new task to the thread pool to handle the client connection
                ClientTask clientTask = new ClientTask(clientSocket);
                executorService.submit(clientTask);
            }
        } catch (Exception e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int port = 5000;
        GameServer server = new GameServer(port);
        server.start();
    }
}
