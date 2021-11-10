import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChatPanel extends JPanel {

    private JTextField input;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> msgs = new JList<>(listModel);
    public String SelectedUser;
    private JLabel lbl;

    public OutputStream out;
    public InputStream in;

    private BufferedReader BufferIn;



    public ChatPanel(Socket socket) throws IOException {

        out = socket.getOutputStream();
        in = socket.getInputStream();
        BufferIn = new BufferedReader(new InputStreamReader(in));

        JLabel msgPane = new JLabel("Your Messages");
        msgPane.setForeground(new Color(102,51,0));
        msgPane.setFont(new Font("Tahoma", Font.BOLD, 20));
        msgPane.setBounds(50, 30, 250, 20);
        this.add(msgPane);


        lbl = new JLabel("Not Connected...");
        lbl.setForeground(new Color(102,51,0));
        lbl.setFont(new Font("Tahoma", Font.BOLD, 15));
        lbl.setBounds(50, 350, 250, 20);
        this.add(lbl);

        JScrollPane sp = new JScrollPane(msgs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setBounds(40, 65, 600, 250);;
        add(sp);

        // listModel.addElement("Not Connected with any User...");
        // listModel.addElement("Select any user from available users list to start Chatting..");

        input = new JTextField();
        input.setFont(new Font("Tahoma", Font.PLAIN, 14));
        input.setBounds(50, 380, 450, 30);
        input.addActionListener((ActionListener) new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = input.getText();
                if(SelectedUser == null){
                    JOptionPane.showMessageDialog(input, "Message not sent! First select a user from the users list.");
                } else {
                    listModel.addElement("You (To "+ SelectedUser +"): " + text);
                    sendMsg(text);
                    input.setText("");
                }
				sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
			}
        });
        add(input);        

        JButton sendBtn = new JButton("Send");
        sendBtn.setForeground(new Color(0, 0, 0));
        sendBtn.setBackground(new Color(8,120,81));
        sendBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        sendBtn.setBounds(550, 380, 100, 30);
        sendBtn.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                String text = input.getText();
                if(SelectedUser == null){
                    JOptionPane.showMessageDialog(input, "Message not sent! First select a user from the users list.");
                } else {
                    listModel.addElement("You (To "+ SelectedUser +"): " + text);
                    sendMsg(text);
                    input.setText("");
                }
                sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
			}
        });
        add(sendBtn);

        StartMessageReader();
    }

    // following function will help to create and edit the chat panel properties!
    public void CreateNewPanel(String newSelectedUser){
        this.SelectedUser = newSelectedUser;
        // listModel.clear();
        listModel.addElement("You can now send messages to " + SelectedUser);
        lbl.setText("Send a Message to: " + SelectedUser);
    }

    public void sendMsg(String txt){
        String cmd = "msg " + SelectedUser + " " + txt + "\n";
        System.out.println("sending msg to " + SelectedUser + ": " + txt);
        try {
            this.out.write(cmd.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void StartMessageReader(){
        Thread t = new Thread(){
            @Override
            public void run(){
                readMessageLoop();
            }
        };
        t.start();
    }

    private void readMessageLoop(){
        try {
            String line;
            while((line = BufferIn.readLine())!=null){
                String[] tokens = line.split(" ", 3);
                if("msg".equalsIgnoreCase(tokens[0])){
                    listModel.addElement("From "+tokens[1]+": "+tokens[2]);
                }
                System.out.println(line);        
            }
        } catch (IOException e) {
            System.out.println("chatPanel.java: Connection lost");
            e.printStackTrace();
        }
    }

}
