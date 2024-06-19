/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gamebingo;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 *
 * @author TRAN HUY
 */
public class MTPClient {
    private final String hostname;
    private final int port;

    public MTPClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void start() throws IOException {
        Socket socket = new Socket(hostname, port);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (true) {
                String message;
                message = inputStream.toString();
                System.out.println(message);
            }
        }).start();

        while (true) {
            String message = scanner.nextLine();
            outputStream.write((message + "\n").getBytes());
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("Usage: ChatClient <hostname> <port>");
            System.exit(1);
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

    }
}
