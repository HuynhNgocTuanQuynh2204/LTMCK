package remotedestopckltm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Authentication extends JFrame {
    private Socket socket;
    private JTextField passwordField;
    private DataOutputStream out;
    private DataInputStream in;

    public Authentication(Socket socket) {
        this.socket = socket;
        setupUI();
    }

    private void setupUI() {
        setLayout(new GridLayout(2, 2));
        add(new JLabel("Nhập mật khẩu:"));
        passwordField = new JTextField();
        add(passwordField);
        JButton submitButton = new JButton("Xác nhận");
        add(submitButton);

        submitButton.addActionListener(e -> authenticate());

        pack();
        setLocationRelativeTo(null); // Đưa giao diện vào giữa màn hình
        setVisible(true); // Hiển thị cửa sổ
    }

    private void authenticate() {
        try {
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            out.writeUTF(passwordField.getText());

            String response = in.readUTF();
            if ("valid".equals(response)) {
                JOptionPane.showMessageDialog(this, "Kết nối thành công!");
                dispose(); // Đóng cửa sổ xác thực
                new CreateFrame(socket, in.readUTF(), in.readUTF()).start();
            } else {
                JOptionPane.showMessageDialog(this, "Mật khẩu sai!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
