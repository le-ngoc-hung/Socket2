package bai2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static final int PORT = 12345;
    private static final Map<Socket, String> clientMap = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                Thread thread = new Thread(() -> handleClient(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            out.println("Enter your username:");
            String username = in.readLine();
            clientMap.put(clientSocket, username);
            System.out.println(username + " đã tham gia đoạn chat.");

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String message = username + ": " + inputLine;
                broadcast(message);
            }

            clientMap.remove(clientSocket);
            System.out.println(username + " đã rời đoạn chat.");
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcast(String message) {
        for (Socket clientSocket : clientMap.keySet()) {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
