import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ServerBase extends Thread {
    public int port;
    private ArrayList<ServerWorker> userList = new ArrayList<>();
    public ServerBase(int port){
        this.port = port;
    }
    public List<ServerWorker> getUserList(){
        return userList;
    }

    @Override
    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("connection accepted...");
                ServerWorker worker = new ServerWorker(this,clientSocket);
                userList.add(worker);
                worker.start();
                // serverSocket.close();
            }
        } catch (IOException io) {
            io.printStackTrace();
        } 
    }
}