/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package remotedestopckltm;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author TUẤN QUỲNH CODER
 */
public class SetPassword extends JFrame implements ActionListener{
    static String port="4907";
    JButton submit;
    JPanel panel;
    JTextField text1,text2;
    String value1,value2;
    JLabel labe1,labe11,lable12;
    
    SetPassword()
    {
        labe11=new JLabel();
        labe11.setText("Set Password");
        text1=new JTextField(15);
        labe1=new JLabel();
        this.setLayout(new BorderLayout());
        submit=new JButton("Submit");
        panel=new JPanel(new GridLayout(2,1));
        panel.add(labe11);
        panel.add(text1);
        panel.add(labe1);
        panel.add(submit);
        add(panel,BorderLayout.CENTER);
        submit.addActionListener(this);
        setTitle("setting password for client");
    }
    
    public void actionPerformed(ActionEvent e) {
     value1=text1.getText();
     dispose();
     new InitConnection(Integer.parseInt(port), value1);

    }
    public String getValue1(){
        return value1;
    }
    public static void main(String[] args) {
        SetPassword frame1 = new SetPassword();
        frame1.setSize(300,80);
        frame1.setLocation(500,300);
        frame1.setVisible(true);
    }
}
