import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ServerBase extends Thread {
    public int port;
    private ArrayList<UserThread> userList = new ArrayList<>();

    public ServerBase(int port){
        this.port = port;
    }
    public List<UserThread> getUserList(){
        return userList;
    }

    @Override
    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            EditUsersListFile edit = new EditUsersListFile();
            edit.start();
            System.out.println("Open for Connections...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("connection accepted...");
                UserThread thread = new UserThread(this,clientSocket);
                userList.add(thread);
                thread.start();
                // serverSocket.close();
            }
        } catch (IOException io) {
            System.out.println("Error here in severBase...1");
            io.printStackTrace();
        }
    }
    private class EditUsersListFile extends Thread {
        @Override
        public void run() {
            while(true){
                try{
                    EditUsersList();
                } catch (IOException | InterruptedException io) {
                    io.printStackTrace();
                }
            }
        }
        public void EditUsersList() throws InterruptedException, IOException{
            while(true){
                sleep(500);     /// toggle after how many second (1000 = 1 sec) this list will refresh!
                FileWriter fw = new FileWriter("availableUsersList.txt");
                PrintWriter addUser = new PrintWriter(fw);
                ArrayList<String> ArrList = new ArrayList<String>();
                for(UserThread thread : userList){
                    if ( thread.isAlive() &&  thread.getUsername() != null) {
                        ArrList.add(thread.getUsername());
                    }
                    // System.out.println(worker.getUsername());
                }

                HashSet<String> hset = new HashSet<String>(ArrList);  ////  to get unique usersnames in the db.txt
                for(String e : hset){
                    addUser.println(e);
                }

                addUser.close();
            }
        }
    }


}
