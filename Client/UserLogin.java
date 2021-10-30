import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.util.Scanner;


public class UserLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton LoginButton;
    private JButton CreateButton;
    private JLabel label;
    private JPanel contentPane;

    public void setUsername(String username){
        this.textField.setText(username);
    }
    public void setPassword(String pass){
        this.passwordField.setText(pass);
    }

    // Create the frame //
    public UserLogin() {
        this.setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 650);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Login");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        lblNewLabel.setBounds(423, 13, 273, 93);
        contentPane.add(lblNewLabel);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(481, 170, 281, 68);
        contentPane.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(481, 286, 281, 68);
        contentPane.add(passwordField);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBackground(Color.BLACK);
        lblUsername.setForeground(Color.BLACK);
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(250, 166, 193, 52);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.BLACK);
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(250, 286, 193, 52);
        contentPane.add(lblPassword);

        LoginButton = new JButton("Login");
        LoginButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        LoginButton.setBounds(545, 392, 162, 73);
        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            // action to be performed when login button is clicked!
                String userName = textField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    if ( auth(userName,password) ) {
                        dispose();
                        UserHome ah = new UserHome(userName);
                        ah.setVisible(true);
                        JOptionPane.showMessageDialog(LoginButton, "You have successfully logged in");
                        System.out.println(userName + " Logged in..");
                    } else {
                        JOptionPane.showMessageDialog(LoginButton, "Wrong Username & Password");
                    }
                } catch (HeadlessException | FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });
        contentPane.add(LoginButton);

        CreateButton = new JButton("Create New Account");
        CreateButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
        CreateButton.setBounds(300, 520, 400, 25);
        CreateButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                dispose();
                CreateUser newUser = new CreateUser();
                newUser.setVisible(true);
            }
        });
        contentPane.add(CreateButton);


        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPane.add(label);
    }

    private boolean auth(String username, String pass) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("db.txt"));
        while (sc.hasNextLine()){
            String[] token = sc.nextLine().split(" &%& ");
            if (token[0].equalsIgnoreCase(username) && token[1].equalsIgnoreCase(pass)) {
                return true;
            }
        }
        return false;
    }
}