
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CreateUser extends JFrame {

  private static final long serialVersionUID = 1L;
  private JTextField textField;
  private JPasswordField passwordField;
  private JPasswordField confirmField;
  private JButton CreateUserButton;
  private JButton LoginButton;
  private JPanel contentPane;

  /** * Create the frame. */
  public CreateUser() {

    this.setTitle("Create New Account");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(450, 190, 1014, 700);
    setResizable(false);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel Label = new JLabel("Create New User");
    Label.setForeground(Color.BLACK);
    Label.setFont(new Font("Times New Roman", Font.PLAIN, 46));
    Label.setBounds(350, 13, 473, 93);
    contentPane.add(Label);

    textField = new JTextField();
    textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
    textField.setBounds(481, 170, 281, 68);
    contentPane.add(textField);
    textField.setColumns(10);

    passwordField = new JPasswordField();
    passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
    passwordField.setBounds(481, 286, 281, 68);
    contentPane.add(passwordField);

    confirmField = new JPasswordField();
    confirmField.setFont(new Font("Tahoma", Font.PLAIN, 32));
    confirmField.setBounds(481, 402, 281, 68);
    contentPane.add(confirmField);

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

    JLabel lblConfirm = new JLabel("Confirm Password");
    lblConfirm.setForeground(Color.BLACK);
    lblConfirm.setBackground(Color.CYAN);
    lblConfirm.setFont(new Font("Tahoma", Font.PLAIN, 31));
    lblConfirm.setBounds(170, 402, 293, 52);
    contentPane.add(lblConfirm);

    CreateUserButton = new JButton("Create Account");
    CreateUserButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
    CreateUserButton.setBounds(345, 510, 262, 50);
    CreateUserButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) { // action to be performed when login button is clicked!
        String userName = textField.getText();
        String pass = new String(passwordField.getPassword());
        String confirm = new String(confirmField.getPassword());

        try {
          if (validateUsername(userName)) {
            JOptionPane.showMessageDialog(CreateUserButton, "Username already exists");
          } else if (!pass.equalsIgnoreCase(confirm)) {
            JOptionPane.showMessageDialog(CreateUserButton, "Passwords does not match");
          } else {
            createUser(userName, pass);
            dispose();
            UserLogin obj = new UserLogin();
            obj.setUsername(userName);
            obj.setPassword(pass);
            obj.setVisible(true);
            JOptionPane.showMessageDialog(CreateUserButton, "New User \'"+ userName +"\' Successfully created!");
          }
        } catch (HeadlessException | IOException e1) {
          e1.printStackTrace();
        }

      }

    });
    contentPane.add(CreateUserButton);




    LoginButton = new JButton("Already have an account?");
    LoginButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
    LoginButton.setBounds(325, 590, 300, 25);
    LoginButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {          // action to be performed when login button is clicked!
        dispose();
        UserLogin obj = new UserLogin();
        obj.setVisible(true);
      }
    });
    contentPane.add(LoginButton);

  }

  protected boolean validateUsername(String userName) throws FileNotFoundException {
    Scanner sc = new Scanner(new File("db.txt"));
    while (sc.hasNextLine()) {
      String[] token = sc.nextLine().split(" &%& ");
      if (token[0].equalsIgnoreCase(userName)) {
        return true;
      }
    }
    return false;
  }

  protected void createUser(String userName, String password) throws IOException {
    FileWriter fw = new FileWriter("db.txt",true);
    PrintWriter addUser = new PrintWriter(fw);
    addUser.println(userName+" &%& "+password);
    addUser.close();
    System.out.println("New User added: "+userName);
  }

}