import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class ServerWorker extends Thread {
    public final Socket clientSocket;
    private String username;
    private final ServerBase server;
    private OutputStream out;

    public ServerWorker(ServerBase server ,Socket clientSocket){
        this.clientSocket = clientSocket;
        this.server = server;
    }
    @Override
    public void run() {
        try{
            handleClientSocket();
        } catch (IOException io) {
            io.printStackTrace();
        } catch(InterruptedException ie){
            ie.printStackTrace();                
        }
    }

    public void handleClientSocket() throws IOException, InterruptedException {
        this.out = clientSocket.getOutputStream();
        InputStream in = clientSocket.getInputStream();

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
        String input;
        input = bufferReader.readLine();
        while(input != null){
            String[] token = input.split(" ");
            if(token != null && token.length > 0){
                String ref = token[0];
                if("quit".equalsIgnoreCase(ref)){
                    break;
                }else if("login".equalsIgnoreCase(ref)){
                    manageLogin(out , token);
                }else{
                    String message = "unknown " +  ref + "\n";
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
            String password = token[2];

            if((username.equals("guest") && password.equals("guest")) || (username.equals("admin") && password.equals("admin"))){
                try{
                    String res = "logged in successfully \n";
                    out.write(res.getBytes());
                    this.username = username;
                    System.out.println("User has logged in: " + username);

                    String onlineMessage = username + " is online ";
                    List<ServerWorker> workerList = server.getUserList();
                    for(ServerWorker worker : workerList){
                        worker.send(onlineMessage);
                    }
                    }catch(IOException e) {
                        e.printStackTrace();
                    }
            }else{
                try{
                String res = "username or password is incorrect \n";
                out.write(res.getBytes());
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void send(String onlineMessage){
        try{
            out.write(onlineMessage.getBytes());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
