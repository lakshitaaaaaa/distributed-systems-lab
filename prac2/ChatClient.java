package prac2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class ChatClient extends JFrame {

    JTextArea textArea;
    JTextField textField;
    JButton sendButton;

    Socket socket;

    BufferedReader br;
    PrintWriter pw;

    String name;


    ChatClient() {

        setTitle("Chat Client");

        setSize(500, 450);

        setLayout(new BorderLayout());


        // Text area
        textArea = new JTextArea();

        textArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(textArea);


        // Bottom panel
        JPanel bottomPanel =
                new JPanel(new BorderLayout());

        textField = new JTextField();

        sendButton =
                new JButton("Send");


        bottomPanel.add(
                textField,
                BorderLayout.CENTER);

        bottomPanel.add(
                sendButton,
                BorderLayout.EAST);


        add(
                scrollPane,
                BorderLayout.CENTER);

        add(
                bottomPanel,
                BorderLayout.SOUTH);


        sendButton.addActionListener(
                new ActionListener() {

                    public void actionPerformed(
                            ActionEvent e) {

                        sendMessage();
                    }
                });


        textField.addActionListener(
                new ActionListener() {

                    public void actionPerformed(
                            ActionEvent e) {

                        sendMessage();
                    }
                });


        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE);

        setVisible(true);


        connectToServer();
    }


    void connectToServer() {

        try {

            name = JOptionPane.showInputDialog(
                    this,
                    "Enter your name:");

            if (name == null ||
                    name.trim().isEmpty()) {

                System.exit(0);
            }


            textArea.append(
                    "Connecting to server...\n");


            socket = new Socket(
                    "localhost",
                    8088);


            br = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));


            pw = new PrintWriter(
                    socket.getOutputStream(),
                    true);


            // Send name to server
            pw.println(name);


            textArea.append(
                    "Connected to server.\n");


            // Start receiving thread
            Thread receiveThread =
                    new Thread(() -> {

                        try {

                            String message;

                            while ((message =
                                    br.readLine()) != null) {

                                textArea.append(
                                        message + "\n");
                            }

                        } catch (Exception e) {

                            textArea.append(
                                    "Disconnected from server.\n");
                        }
                    });


            receiveThread.start();


        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to connect to server."
            );

            System.exit(0);
        }
    }


    void sendMessage() {

        String message =
                textField.getText().trim();


        if (message.isEmpty()) {
            return;
        }


        pw.println(message);

        textArea.append(
                "You: " + message + "\n");


        textField.setText("");


        if (message.equalsIgnoreCase("exit")) {

            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.exit(0);
        }
    }


    public static void main(String[] args) {

        new ChatClient();
    }
}
