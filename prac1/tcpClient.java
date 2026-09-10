import java.net.*;
import java.io.*;

class tcpClient {

    public static void main(String[] args) throws Exception {

        System.out.println("Connecting to server");

        Socket cs = new Socket("localhost", 8088);

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(System.in));

        System.out.println("The Local Port "
                + cs.getLocalPort());

        System.out.println("The Remote Port "
                + cs.getPort());

        System.out.println("The Local socket is " + cs);

        while (true) {

            System.out.println("Enter your message:");

            String str = br.readLine();

            if (str.equalsIgnoreCase("exit"))
                break;

            // SENDING DATA TO SERVER
            OutputStream os = cs.getOutputStream();

            os.write(str.getBytes());

            // READING DATA FROM SERVER
            InputStream is = cs.getInputStream();

            byte data[] = new byte[100];

            int n = is.read(data);

            String mfs = new String(data, 0, n);

            mfs = mfs.trim();

            // PRINTING MESSAGE ON CLIENT CONSOLE
            System.out.println(mfs);
        }

        cs.close();
    }
}
