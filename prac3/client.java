package prac3;

import java.io.*;
import java.net.*;

public class client {

    public static void main(String[] args) {

        String serverHost = "localhost";
        int serverPort = 5001;

        try {

            // connect to server
            Socket clientSocket =
                    new Socket(serverHost, serverPort);

            System.out.println(
                    "Connected to server.");


            // get client's own IP address
            String clientIP =
                    clientSocket.getLocalAddress()
                               .getHostAddress();

            System.out.println(
                    "Client IP Address: "
                    + clientIP);


            // send client's IP address to server
            PrintWriter out =
                    new PrintWriter(
                            clientSocket.getOutputStream(),
                            true);

            out.println(clientIP);


            // receive date and time from server
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(
                                    clientSocket.getInputStream()));

            String serverResponse =
                    in.readLine();


            // display server response
            System.out.println(
                    "Server response: "
                    + serverResponse);


            // close streams
            in.close();
            out.close();

            // close socket
            clientSocket.close();


            System.out.println(
                    "Client stopped.");

        } catch (IOException e) {

            System.out.println(
                    "Client exception: "
                    + e.getMessage());
        }
    }
}
