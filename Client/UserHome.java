
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.GridLayout;

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


        setTitle("Home Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setBounds(0,0,1014,100);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.add(contentPane);
        contentPane.setLayout(null);

        JLabel Label = new JLabel("Hello! " + userName);
        Label.setForeground(Color.BLACK);
        Label.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        Label.setBounds(120, 5, 473, 93);
        contentPane.add(Label);


        JButton LogoutButton = new JButton("Logout");
        LogoutButton.setForeground(new Color(0, 0, 0));
        LogoutButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        LogoutButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        LogoutButton.setBounds(850, 40, 100, 25);
        LogoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(LogoutButton, "Are you sure?");
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                    UserLogin obj = new UserLogin();
                    obj.setVisible(true);
                }
            }
        });
        contentPane.add(LogoutButton);

        UserListPanel container = new UserListPanel();
        container.add(new JLabel("1"));
        container.setBackground(Color.CYAN);
        container.setBounds(0,100,300,500);
        this.add(container);
        container.setLayout(null);

    }

    public void msg(String sendTo, String msgBody) throws IOException {
        String cmd = "msg " + sendTo + " " + msgBody + "\n";
        serverOut.write(cmd.getBytes());
    }
}