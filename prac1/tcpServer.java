import java.io.*;
import java.net.*;

public class tcpServer {

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(8088);

        System.out.println("Server is ready!");

        while (true) {

            // wait for a client
            Socket ls = ss.accept();

            System.out.println("Client connected: "
                    + ls.getInetAddress());

            // create a new thread for each client
            ClientHandler ch = new ClientHandler(ls);
            ch.start();
        }
    }
}

// thread to handle each client
class ClientHandler extends Thread {

    Socket ls;

    ClientHandler(Socket ls) {
        this.ls = ls;
    }

    public void run() {

        try {

            while (true) {

                System.out.println("Client Port is "
                        + ls.getPort());

                // reading data from client
                InputStream is = ls.getInputStream();

                byte data[] = new byte[50];

                int n = is.read(data);

                if (n == -1)
                    break;

                String mfc = new String(data, 0, n);
                mfc = mfc.trim();

                System.out.println("Message from client: " + mfc);

                // creating response
                String mfs = "The message was: " + mfc;

                // sending message to client
                OutputStream os = ls.getOutputStream();

                os.write(mfs.getBytes());
            }

            ls.close();

        } catch (Exception e) {
            System.out.println("Client disconnected.");
        }
    }
}