import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class UserListPanel extends JPanel {


    public UserListPanel() {

        JLabel lbl = new JLabel("Users List");
        lbl.setForeground(Color.BLACK);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 25));
        lbl.setBounds(70, 120, 150, 50);
        this.add(lbl);

        
        //  We will create a new chat Panel every time the user wants to chat with someone else

        ChatPanel chatPanel = new ChatPanel("Username");
        chatPanel.add(new JLabel("2"));
        chatPanel.setBackground(Color.RED);
        chatPanel.setBounds(300,100,800,500);
        this.add(chatPanel);
        chatPanel.setLayout(null);
        
    }

}
