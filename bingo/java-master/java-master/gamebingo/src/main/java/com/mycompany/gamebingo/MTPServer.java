/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamebingo;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
/**
 *
 * @author TRAN HUY
 */
public class MTPServer {
    private final ServerSocket serverSocket;
    private final Map<Socket, ClientHandler> clients;
    private final ExecutorService executorService;

    public MTPServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clients = new HashMap<>();
        executorService = Executors.newCachedThreadPool();
    }

    public void start() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            executorService.execute(clientHandler);
        }
    }

    private class ClientHandler implements Runnable {

        private final Socket clientSocket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public ClientHandler(Socket clientSocket) throws IOException {
            this.clientSocket = clientSocket;
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String message = readMessage();
                    if (message == null) {
                        break; // Client disconnected
                    }

                    broadcastMessage(message);
                }
            } catch (IOException e) {
                removeClient(clientSocket);
            }
        }

        private String readMessage() throws IOException {
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            if (bytesRead == -1) {
                return null;
            }

            return new String(buffer, 0, bytesRead);
        }

        private void sendMessage(String message) throws IOException {
            byte[] messageBytes = message.getBytes();
            outputStream.write(messageBytes);
        }

        private void broadcastMessage(String message) {
            synchronized (clients) {
                for (ClientHandler client : clients.values()) {
                    if (client != this) {
                        try {
                            client.sendMessage(message);
                        } catch (IOException e) {
                            removeClient(client.clientSocket);
                        }
                    }
                }
            }
        }

        private void removeClient(Socket clientSocket) {
            synchronized (clients) {
                clients.remove(clientSocket);
                try {
                    clientSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: ChatServer <port>");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
        MTPServer server = new MTPServer(port);
        server.start();
        System.out.println("Server started on port " + port);
    }
}


