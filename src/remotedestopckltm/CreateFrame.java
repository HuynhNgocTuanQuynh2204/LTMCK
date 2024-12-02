/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package remotedestopckltm;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 *
 * @author TUẤN QUỲNH CODER
 */
class CreateFrame extends Thread{
    String width="",height="";
    private JFrame frame = new JFrame();
    private  JDesktopPane destop = new JDesktopPane();
    private Socket cSocket=null;
    private JInternalFrame interFrame = new JInternalFrame("Server Screen",true,true,true);
    private JPanel cPanel = new JPanel();
    
    public CreateFrame(Socket cSocket,String width,String height)
    {
        this.width = width;
        this.height=height;
        this.cSocket=cSocket;
        start();
    }
    public void drawGUI()
    {
        frame.add(destop,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        interFrame.setLayout(new BorderLayout());
        interFrame.getContentPane().add(cPanel,BorderLayout.CENTER);
        interFrame.setSize(100,100);
        destop.add(interFrame);
        
        try {
            interFrame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
        cPanel.setFocusable(true);
        cPanel.requestFocusInWindow(); ;
        interFrame.setVisible(true);
    }
    public void run()
    {
        InputStream in =null;
        
        drawGUI();
        
        try {
            in=cSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new ReceivingScreen(in,cPanel);
        new SendEvents(cSocket,cPanel,width,height);
    }
    
}
