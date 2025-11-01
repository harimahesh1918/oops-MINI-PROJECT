package com.example.blog.client.ui;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ClientSocketUtil {
    private static final String SERVER = "localhost";
    private static final int PORT = 12345;

    public static String sendCommand(String command, String message) {
        try (Socket socket = new Socket(SERVER, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println(command);
            if (message != null) {
                out.println(message);
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null && !line.equals("END")) {
                response.append(line).append("\n");
            }

            return response.toString();

        } catch (IOException e) {
            return "❌ Connection error: " + e.getMessage();
        }
    }

    public static List<String> getPosts() {
        List<String> posts = new ArrayList<>();
        try (Socket socket = new Socket(SERVER, PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            out.println("VIEW");
            String line;
            while ((line = in.readLine()) != null && !line.equals("END")) {
                posts.add(line);
            }

        } catch (IOException e) {
            posts.add("❌ Error: " + e.getMessage());
        }
        return posts;
    }
}
