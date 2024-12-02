package remotedestopckltm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class Start {
    private static InitConnection server;
    private static JTextField textID, textPass;
    private static JTextArea chatArea;
    private static Socket socket;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Remote Desktop App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Giao diện chính
        JPanel panel = new JPanel(new GridLayout(5, 2));
        JLabel labelID = new JLabel("ID của bạn:");
        textID = new JTextField();
        textID.setEditable(false);
        JLabel labelPass = new JLabel("Mật khẩu:");
        textPass = new JTextField();
        textPass.setEditable(false);
        JLabel labelPartnerID = new JLabel("ID đối tác:");
        JTextField textPartnerID = new JTextField();
        JButton btnStartClient = new JButton("Điều khiển máy tính khác");
        JButton btnResetServer = new JButton("Reset Server");

        panel.add(labelID);
        panel.add(textID);
        panel.add(labelPass);
        panel.add(textPass);
        panel.add(labelPartnerID);
        panel.add(textPartnerID);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnStartClient);
        buttonPanel.add(btnResetServer);

        // Chat và gửi file
        chatArea = new JTextArea(10, 30);
        chatArea.setEditable(false);
        JTextField messageField = new JTextField(20);
        JButton sendButton = new JButton("Gửi tin nhắn");
        JButton sendFileButton = new JButton("Gửi file");

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel messagePanel = new JPanel();
        messagePanel.add(messageField);
        messagePanel.add(sendButton);
        messagePanel.add(sendFileButton);

        chatPanel.add(messagePanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.NORTH);
        frame.getContentPane().add(chatPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Khởi động server
        initializeServer();

        // Reset server
        btnResetServer.addActionListener(e -> resetServer());

        // Khởi động client
        btnStartClient.addActionListener(e -> {
            String partnerID = textPartnerID.getText();
            startClient(partnerID);
        });

        // Gửi tin nhắn
        sendButton.addActionListener(e -> {
            String message = messageField.getText();
            if (!message.isEmpty()) {
                sendMessage(message);
                chatArea.append("Bạn: " + message + "\n");
                messageField.setText("");
            }
        });

        // Gửi file
        sendFileButton.addActionListener(e -> sendFile());
    }

    private static void initializeServer() {
        try {
            String ipAddress = InetAddress.getLocalHost().getHostAddress();
            textID.setText(ipAddress);
            resetServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void resetServer() {
        if (server != null) server.stopServer();
        String password = generateRandomPassword();
        textPass.setText(password);
        server = new InitConnection(6789, password);
        JOptionPane.showMessageDialog(null, "Server đã khởi động lại với mật khẩu: " + password);
    }

    private static void startClient(String partnerID) {
        try {
            socket = new Socket(partnerID, 6789);
            chatArea.append("Kết nối thành công tới " + partnerID + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối tới đối tác!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void sendMessage(String message) {
        try {
            if (socket != null) {
                socket.getOutputStream().write((message + "\n").getBytes());
                socket.getOutputStream().flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile() {
        if (socket == null) {
            JOptionPane.showMessageDialog(null, "Chưa kết nối tới đối tác!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            try {
                var file = fileChooser.getSelectedFile();
                var fileStream = file.getName().getBytes();
                socket.getOutputStream().write(fileStream);
                socket.getOutputStream().flush();
                chatArea.append("Đã gửi file: " + file.getName() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }
}
