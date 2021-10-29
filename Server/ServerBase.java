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
    private ArrayList<ServerWorker> groupList = new ArrayList<>();
    public ServerBase(int port){
        this.port = port;
    }
    public List<ServerWorker> getUserList(){
        return userList;
    }
    public List<ServerWorker> getGroupList(){
        return groupList;
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
    public void removeUser(ServerWorker serverWorker){
        userList.remove(serverWorker);
    }
    public void removeGroup(ServerWorker serverWorker){
        groupList.remove(serverWorker);
    }
    public void addGroup(ServerWorker serverWorker){
        groupList.add(serverWorker);
    }
}