package prac3;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class server {

    public static void main(String[] args) {

        int port = 5001;

        try {
            ServerSocket serverSocket =
                    new ServerSocket(port);

            System.out.println(
                    "Server started on port " + port);

            while (true) {

                // accept client
                Socket clientSocket =
                        serverSocket.accept();

                // create a new thread for each client
                ClientHandler client =
                        new ClientHandler(clientSocket);

                client.start();
            }

        } catch (IOException e) {

            System.out.println(
                    "Server exception: " + e.getMessage());
        }
    }
}


// thread for handling each client
class ClientHandler extends Thread {

    Socket clientSocket;

    ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {

        try {

            // get client's IP address
            String clientIP =
                    clientSocket.getInetAddress()
                               .getHostAddress();

            System.out.println(
                    "Client connected: IP Address = "
                    + clientIP);


            // send date and time to client
            PrintWriter out =
                    new PrintWriter(
                            clientSocket.getOutputStream(),
                            true);

            String dateTime =
                    new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss")
                    .format(new Date());

            out.println(
                    "Current date and time: "
                    + dateTime);


            // close output stream
            out.close();

            // close client socket
            clientSocket.close();

        } catch (IOException e) {

            System.out.println(
                    "Client exception: "
                    + e.getMessage());
        }
    }
}
