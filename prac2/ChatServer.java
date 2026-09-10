package prac2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class ChatServer extends JFrame {

    JTextArea textArea;
    JButton startButton;

    ServerSocket serverSocket;

    ArrayList<ClientHandler> clients = new ArrayList<>();

    ChatServer() {

        setTitle("Chat Server");
        setSize(500, 400);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        startButton = new JButton("Start Server");

        add(scrollPane, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);

        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                startServer();

            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void startServer() {

        try {

            serverSocket = new ServerSocket(8088);

            textArea.append("Server started on port 8088\n");
            textArea.append("Waiting for clients...\n");

            startButton.setEnabled(false);

            // Thread to accept clients
            Thread acceptThread = new Thread(() -> {

                try {

                    while (true) {

                        Socket socket = serverSocket.accept();

                        textArea.append(
                                "Client connected: "
                                + socket.getInetAddress()
                                + "\n");

                        ClientHandler client =
                                new ClientHandler(socket);

                        clients.add(client);

                        client.start();
                    }

                } catch (Exception e) {

                    textArea.append("Server stopped.\n");
                }

            });

            acceptThread.start();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not start server."
            );
        }
    }

    void broadcast(String message, ClientHandler sender) {

        for (ClientHandler client : clients) {

            if (client != sender) {

                client.sendMessage(message);
            }
        }
    }


    class ClientHandler extends Thread {

        Socket socket;

        BufferedReader br;

        PrintWriter pw;

        String name;


        ClientHandler(Socket socket) {

            this.socket = socket;
        }


        public void run() {

            try {

                br = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

                pw = new PrintWriter(
                        socket.getOutputStream(),
                        true);


                // Read client name
                name = br.readLine();

                textArea.append(
                        name + " joined the chat.\n");

                broadcast(
                        name + " joined the chat.",
                        this);


                String message;


                while ((message = br.readLine()) != null) {

                    if (message.equalsIgnoreCase("exit")) {

                        break;
                    }

                    textArea.append(
                            name + ": " + message + "\n");

                    broadcast(
                            name + ": " + message,
                            this);
                }


            } catch (Exception e) {

                textArea.append(
                        name + " disconnected.\n");

            } finally {

                clients.remove(this);

                try {
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        void sendMessage(String message) {

            pw.println(message);
        }
    }


    public static void main(String[] args) {

        new ChatServer();
    }
}
