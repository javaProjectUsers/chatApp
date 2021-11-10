
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.io.*;
import java.net.Socket;


public class UserHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    public  UserListPanel container;


    /** * Create the frame. */
    public UserHome(String userName, Socket socket) throws IOException{

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
                    try {
                        System.out.println("requesting logout");
                        socket.getOutputStream().write("logout\n".getBytes());
                    } catch (IOException e1) {e1.printStackTrace();}
                    dispose();
                    container.t = null;
                    container.myUsername = null;
                    container = null;
                    System.gc();
                    UserLogin obj = new UserLogin(socket);
                    obj.setVisible(true);
                }
            }
        });
        contentPane.add(LogoutButton);

        // Below container will keep the record of all the available users !

        try {
            container = new UserListPanel(userName,socket);
            container.setBackground(new Color(154,142,149));
            container.setBounds(0,100,300,500);
            this.add(container);
            container.setLayout(null);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

    }

}