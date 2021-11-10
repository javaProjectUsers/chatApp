import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class UserListPanel extends JPanel {

    public static List userList;
    private ChatPanel chatPanel;
    public DefaultListModel<String> listModel = new DefaultListModel<String>();
    public JList<String> users = new JList<String>(listModel);
    public String myUsername;

    private String otherUser;


    public UserListPanel(String userName, Socket socket) throws IOException {
        this.myUsername = userName;

        JLabel lbl = new JLabel("Users List");
        lbl.setForeground(Color.BLACK);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 25));
        lbl.setBounds(70, 120, 150, 50);
        this.add(lbl);

        userList = new List();
        userList.setFont(new Font("Tahoma", Font.BOLD, 14));
        userList.setBounds(60, 200, 180, 280);
        // users.setFont(new Font("Tahoma", Font.BOLD, 14));
        // users.setBounds(60, 200, 180, 280);

        userList.addItemListener(e -> {
            String selectedUser = userList.getSelectedItem();
            System.out.println("clicked: " + selectedUser);

         //  We will create a new chat Panel every time the user wants to chat with someone else            
            otherUser = selectedUser;
            chatPanel.CreateNewPanel(otherUser);
        });
        add(userList);


        //  We will create a new chat Panel every time the user wants to chat with someone else
        chatPanel = new ChatPanel(socket);
        chatPanel.setBackground(Color.LIGHT_GRAY);
        chatPanel.setBounds(300,100,800,500);
        this.add(chatPanel);
        chatPanel.setLayout(null);
        
        new Thread() {
            @Override
            public void run() {
                while(true){
                    try {
                        refreshList();
                    } catch (InterruptedException | FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


    }

    public void refreshList() throws InterruptedException, FileNotFoundException{
        while(true){
            userList.removeAll();
            // listModel.clear();
            Scanner sc = new Scanner(new File("availableUsersList.txt")); 
            while (sc.hasNextLine()){
                String token = sc.nextLine();
                // System.out.println(token+" mu:"+myUsername);
                if(!token.equalsIgnoreCase(myUsername)){
                    userList.add(token);
                    // listModel.addElement(token);
                }
            }
            Thread.sleep(2000);
        }
    }


}
