package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
    private final Socket clientSocket;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received message: " + inputLine);

                if (inputLine.equals("stop")) {
                    out.println("Server stopped");
                    break;
                }

                // TODO: Handle other commands

                out.println("Server received the request: " + inputLine);
            }

            clientSocket.close();
        } catch (Exception e) {
            System.out.println("Error handling client connection: " + e.getMessage());
        }
    }
}

