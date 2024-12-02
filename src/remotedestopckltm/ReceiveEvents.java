/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package remotedestopckltm;

import java.awt.Robot;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author TUẤN QUỲNH CODER
 */
public class ReceiveEvents extends Thread{
    Socket socket=null;
    Robot robot=null;
    boolean continueLoop=true;
    public ReceiveEvents(Socket socket, Robot robot) {
        this.socket=socket;
        this.robot=robot;
        start();
    }
 
    @Override
public void run() {
    Scanner scanner = null;
    try {
        scanner = new Scanner(socket.getInputStream());
        while (continueLoop) {
            int command = scanner.nextInt();
            switch (command) {
                case -1:  // Nhấn chuột
                    robot.mousePress(scanner.nextInt());
                    break;
                case -2:  // Thả chuột
                    robot.mouseRelease(scanner.nextInt());
                    break;
                case -3:  // Nhấn phím
                    robot.keyPress(scanner.nextInt());  // Thực thi nhấn phím
                    break;
                case -4:  // Thả phím
                    robot.keyRelease(scanner.nextInt());  // Thực thi thả phím
                    break;
                case -5:  // Di chuyển chuột
                    robot.mouseMove(scanner.nextInt(), scanner.nextInt());
                    break;
                default:
                    throw new AssertionError("Lệnh không hợp lệ: " + command);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
   
}
