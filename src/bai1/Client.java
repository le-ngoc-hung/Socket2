package bai1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "127.0.0.1";
        final int PORT = 12345;

        try {
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

