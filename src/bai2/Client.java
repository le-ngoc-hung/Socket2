package bai2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 12345;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    private JTextArea chatTextArea;
    private JTextField inputTextField;

    public Client() {
        setTitle("Chat Client");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        chatTextArea = new JTextArea();
        chatTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        inputTextField = new JTextField();
        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        panel.add(inputTextField, BorderLayout.SOUTH);

        add(panel);

        connectToServer();

        getUsername();

        startListening();

        setVisible(true);
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUsername() {
        username = JOptionPane.showInputDialog("Nhập tên của bạn");
        out.println(username);
    }

    private void startListening() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        chatTextArea.append(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendMessage() {
        String message = inputTextField.getText();
        out.println( message);
        inputTextField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client();
            }
        });
    }
}


