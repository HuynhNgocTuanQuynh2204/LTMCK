package remotedestopckltm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class InitConnection {
    private ServerSocket serverSocket;

    public InitConnection(int port, String password) {
        try {
            serverSocket = new ServerSocket(port);
            new Thread(this::startServer).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startServer() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            InputStream is = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Tin nhắn: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
