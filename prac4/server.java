package prac4;

import java.io.*;
import java.net.*;

public class server {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket();

        // configure socket options
        serverSocket.setReuseAddress(true);

        // bind server to port 5000
        serverSocket.bind(new InetSocketAddress(5001));

        System.out.println("Server started on port 5000");

        // accept client connection
        Socket socket = serverSocket.accept();

        // SO_KEEPALIVE
        socket.setKeepAlive(true);

        // SO_LINGER
        socket.setSoLinger(true, 10); // linger for 10 seconds

        // SO_SNDBUF
        socket.setSendBufferSize(8192);

        // SO_RCVBUF
        socket.setReceiveBufferSize(8192);

        // TCP_NODELAY
        socket.setTcpNoDelay(true);

        // display configured values
        System.out.println("\nSocket Options:");
        System.out.println("SO_KEEPALIVE : " + socket.getKeepAlive());
        System.out.println("SO_LINGER    : " + socket.getSoLinger());
        System.out.println("SO_SNDBUF    : " + socket.getSendBufferSize());
        System.out.println("SO_RCVBUF    : " + socket.getReceiveBufferSize());
        System.out.println("TCP_NODELAY  : " + socket.getTcpNoDelay());

        // simple communication
        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

        String message = in.readLine();
        System.out.println("Client: " + message);

        out.println("Message received by server");

        socket.close();
        serverSocket.close();
    }
}
