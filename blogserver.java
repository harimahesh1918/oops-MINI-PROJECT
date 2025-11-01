package com.example.blog.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class BlogServer {
    private static final int PORT = 12345;
    private static List<String> posts = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("✅ Blog Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("❌ Error starting server: " + e.getMessage());
        }
    }

    private static void handleClient(Socket socket) {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String command = in.readLine();

            if (command.equalsIgnoreCase("ADD")) {
                String title = in.readLine();
                posts.add(title);
                out.println("Post added successfully: " + title);

            } else if (command.equalsIgnoreCase("VIEW")) {
                if (posts.isEmpty()) {
                    out.println("No posts yet.");
                } else {
                    for (String post : posts) {
                        out.println(post);
                    }
                }
                out.println("END");

            } else if (command.equalsIgnoreCase("DELETE")) {
                String title = in.readLine();
                if (posts.remove(title)) {
                    out.println("Post deleted: " + title);
                } else {
                    out.println("Post not found!");
                }

            } else {
                out.println("Invalid command!");
            }

        } catch (IOException e) {
            System.out.println("❌ Error handling client: " + e.getMessage());
        }
    }
}
