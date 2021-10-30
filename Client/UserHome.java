
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.*;
import java.io.*;


public class UserHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField input;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> msgs = new JList<>(listModel);

    private OutputStream serverOut;


    /** * Create the frame. */
    public UserHome(String userName) {


        this.setTitle("Home Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel Label = new JLabel("Hello! " + userName);
        Label.setForeground(Color.BLACK);
        Label.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        Label.setBounds(150, 13, 473, 93);
        contentPane.add(Label);


        JTextArea area=new JTextArea("Welcome to javatpoint");  
        area.setBounds(300, 100, 500, 400);  
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        
        // JScrollPane scrollableTextArea = new JScrollPane(area);  
        // scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        // scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  
        
        contentPane.add(area);  

        input = new JTextField();
        input.setFont(new Font("Tahoma", Font.PLAIN, 12));
        input.setBounds(300, 500, 500, 20);
        contentPane.add(input);
        input.setColumns(5);

        input.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String text = input.getText();
                listModel.addElement( "You : " + text);
                input.setText("");
                
            }
        });

        msgs.setBounds(0, 0, 500, 400); 
        area.add(msgs);

        JButton sendBtn = new JButton(">");
        sendBtn.setForeground(new Color(0, 0, 0));
        sendBtn.setBackground(UIManager.getColor("Button.disabledForeground"));
        sendBtn.setFont(new Font("Tahoma", Font.PLAIN, 8));
        sendBtn.setBounds(800, 500, 40, 20);

        sendBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = input.getText();
                listModel.addElement( "You : " + text);
                input.setText("");
            }
            
        });
        contentPane.add(sendBtn);



        JButton btnNewButton = new JButton("Logout");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnNewButton.setBounds(850, 43, 100, 25);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                    UserLogin obj = new UserLogin();
                    obj.setVisible(true);
                }
            }
        });
        contentPane.add(btnNewButton);

    }

    public void msg(String sendTo, String msgBody) throws IOException {
        String cmd = "msg " + sendTo + " " + msgBody + "\n";
        serverOut.write(cmd.getBytes());
    }
}