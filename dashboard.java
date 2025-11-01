package com.example.blog.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class DashboardUI extends JFrame {
    private JTextArea postArea;

    public DashboardUI() {
        setTitle("Blog Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Welcome to the Blog Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        add(title, BorderLayout.NORTH);

        postArea = new JTextArea();
        postArea.setEditable(false);
        add(new JScrollPane(postArea), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add Blog Post");
        JButton refreshBtn = new JButton("Refresh Posts");
        JButton deleteBtn = new JButton("Delete Post");
        JButton exitBtn = new JButton("Exit");

        buttons.add(addBtn);
        buttons.add(refreshBtn);
        buttons.add(deleteBtn);
        buttons.add(exitBtn);

        add(buttons, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addPost());
        refreshBtn.addActionListener(e -> refreshPosts());
        deleteBtn.addActionListener(e -> deletePost());
        exitBtn.addActionListener(e -> System.exit(0));

        refreshPosts();
        setVisible(true);
    }

    private void addPost() {
        String title = JOptionPane.showInputDialog(this, "Enter Post Title:");
        if (title != null && !title.trim().isEmpty()) {
            String response = ClientSocketUtil.sendCommand("ADD", title);
            JOptionPane.showMessageDialog(this, response);
            refreshPosts();
        }
    }

    private void refreshPosts() {
        List<String> posts = ClientSocketUtil.getPosts();
        postArea.setText("");
        for (String post : posts) {
            postArea.append(post + "\n");
        }
    }

    private void deletePost() {
        String title = JOptionPane.showInputDialog(this, "Enter Post Title to Delete:");
        if (title != null && !title.trim().isEmpty()) {
            String response = ClientSocketUtil.sendCommand("DELETE", title);
            JOptionPane.showMessageDialog(this, response);
            refreshPosts();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardUI::new);
    }
}
