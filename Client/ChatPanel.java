import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import java.io.OutputStream;


import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class ChatPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField input;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> msgs = new JList<>(listModel);
    private OutputStream serverOut;


    public ChatPanel(String username) {


        JScrollPane sp = new JScrollPane(msgs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setBounds(30, 50, 600, 250);;
        add(sp);

        input = new JTextField();
        input.setFont(new Font("Tahoma", Font.PLAIN, 14));
        input.setBounds(50, 350, 450, 30);
        input.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = input.getText();
                listModel.addElement( "You : " + text);
                input.setText("");
				sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
			}
        });
        add(input);        

        JButton sendBtn = new JButton("Send");
        sendBtn.setForeground(new Color(0, 0, 0));
        sendBtn.setBackground(Color.CYAN);
        sendBtn.setFont(new Font("Tahoma", Font.BOLD, 14));
        sendBtn.setBounds(550, 350, 100, 30);
        sendBtn.addActionListener((ActionListener) new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                String text = input.getText();
                listModel.addElement("You : " + text);
                input.setText("");
                sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
			}
        });
        add(sendBtn);
        
    }

    public void msg(String sendTo, String msgBody) throws IOException {
        String cmd = "msg " + sendTo + " " + msgBody + "\n";
        serverOut.write(cmd.getBytes());
    }

}
