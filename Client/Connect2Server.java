import java.awt.EventQueue;
import java.io.IOException;
import java.net.Socket;

public class Connect2Server {

    public Boolean connection;
    public int serverPort;
    private Socket socket;
    public int clientPort;

    public Connect2Server(int port) throws IOException{

        this.serverPort = port;

        if(connect()){
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        UserLogin frame = new UserLogin(socket);
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            System.out.println("connection with server failed...");
        }
    }

    private boolean connect(){
        try{
            this.socket = new Socket("localhost", serverPort);
            return true;
        } catch(IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}