import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ChatClient {
    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private OutputStream serverOut;
    private InputStream serverIn;
    private BufferedReader bufferedReader;
    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();


    public ChatClient(String serverName, int serverPort){
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    private boolean connect(){
        try {
            this.socket = new Socket(serverName,serverPort);
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedReader = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        ChatClient client = new ChatClient("localhost",8818);
        client.addUserStatusListener(new UserStatusListener() {
            @Override
            public void online(String username) {
                System.out.println("Online: " + username);
            }

            @Override
            public void offline(String username) {
                System.out.println("Offline: " + username);

            }
        });
        client.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(String fromLogin, String msgBody) {
                System.out.println("You got a message from " + fromLogin + " ===> " + msgBody);
            }
        });
        if(client.connect()){
            System.out.println("Connected");
            if(client.login("admin","admin")){
                System.out.println("Login successful");
                client.msg("guest","hello");
            }else{
                System.out.println("Error in login");
            };
        }else{
            System.out.println("Error");
        }
    }

    private void msg(String username, String message) {
        String cmd = "msg " + username + " " + message;
        try {
            serverOut.write(cmd.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean login(String username, String password) throws IOException {
            String cmd = "login " + username + " " + password + "\n";
            System.out.println("cmd = " + cmd);
            serverOut.write(cmd.getBytes());

            String response = bufferedReader.readLine();
            System.out.println("response = " + response);
            System.out.println("logged in successfully ".equalsIgnoreCase(response));


            if("logged in successfully ".equalsIgnoreCase(response)){
              startMessageReader();
              return true;
            }else{
                return false;
            }
    }

    private void startMessageReader() {
        Thread t = new Thread(){
            @Override
            public void run(){
                readMessageLoop();
            }
        };
        t.start();
    }

    private void readMessageLoop() {
        try{
            String line;
            while((line = bufferedReader.readLine()) != null){
                String[] token = line.split(" ");
                if(token != null && token.length > 0) {
                    String ref = token[0];
                    System.out.println(ref);
                    if("online".equalsIgnoreCase(ref)){
                        handleOnline(token);
;                   }else if("offline".equalsIgnoreCase(ref)){
                        handleOffline(token);
                    }else if("msg".equalsIgnoreCase(ref)){
                        String[] tokenMsg = line.split(" ", 3);
                        handleMessage(tokenMsg);
                    }
                }
            }
        } catch(Exception ex){
            ex.printStackTrace();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(String[] token) {
        String username = token[1];
        String msg = token[2];

        for(MessageListener listener : messageListeners){
            listener.onMessage(username,msg);
        }
    }

    private void handleOffline(String[] token) {
        String username = token[1];
        for(UserStatusListener listener : userStatusListeners){
            listener.offline(username);
        }
    }

    private void handleOnline(String[] token) {
        String username = token[1];
        for(UserStatusListener listener : userStatusListeners){
            listener.online(username);
        }
    }

    public void addUserStatusListener(UserStatusListener listener){
        userStatusListeners.add(listener);
    }
    public void removeUserStatusListener(UserStatusListener listener){
        userStatusListeners.remove(listener);
    }
    public void addMessageListener(MessageListener listener){
        messageListeners.add(listener);
    }
    public void removeMessageListener(MessageListener listener){
        messageListeners.remove(listener);
    }
}
