import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class UserThread extends Thread {

    public final Socket clientSocket;
    public String username = null;
    public final ServerBase server;
    private OutputStream out;
    private InputStream in;

    public UserThread(ServerBase server ,Socket clientSocket){
        this.clientSocket = clientSocket;
        this.server = server;
    }
    
    @Override
    public void run() {
        try{
            manageClientSocket();
        } catch (IOException io) {
            System.out.println("Error here in severWorker..1");
            io.printStackTrace();
        } catch(InterruptedException ie){
            System.out.println("Error here in severWorker..2");
            ie.printStackTrace();
        }
    }

    private void manageClientSocket() throws IOException, InterruptedException {
        this.out = clientSocket.getOutputStream();
        this.in = clientSocket.getInputStream();

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
        String input;
        input = bufferReader.readLine();
        while(input != null){
            String[] token = input.split(" ");
            if(token != null && token.length > 0){
                String ref = token[0];
                if("logoff".equalsIgnoreCase(ref) || "quit".equalsIgnoreCase(ref) || "logout".equalsIgnoreCase(ref)){
                    manageLogoff();
                }else if("login".equalsIgnoreCase(ref)){
                    manageLogin(out , token);
                }else if("msg".equalsIgnoreCase(ref)){
                    String[] tokenMsg = input.split(" ", 3);
                    manageMsg(tokenMsg);
                } else {
                    String message = "unknown " +  ref + "\n";
                    System.out.println("serverworker.java: "+message);
                    out.write(message.getBytes());
                }
                input = bufferReader.readLine();
            }
        }
        clientSocket.close();
    }

    public String getUsername(){
        return username;
    }

    public void manageLogin(OutputStream out , String[] token){
        if(token.length == 3){
            String username = token[1];
            // String password = token[2];
            try{
                String res = "logged in successfully\n";
                out.write(res.getBytes());
                this.username = username;
                System.out.println("User has logged in: " + username);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void manageLogoff() throws IOException {
        System.out.println("logout called");
        username = null;
    }


    private void manageMsg(String[] tokenMsg) throws IOException{
        String sendTo = tokenMsg[1];
        String msgBody =  tokenMsg[2];

        List<UserThread> threadList = server.getUserList();
        for(UserThread thread : threadList) {
            if (sendTo.equalsIgnoreCase(thread.getUsername())) {
                String outMsg = "msg " + username + " " + msgBody + "\n";
                thread.send(outMsg);
            }
        }
    }

    private void send(String onlineMessage){
        try{
            out.write(onlineMessage.getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
