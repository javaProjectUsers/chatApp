import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ServerWorker extends Thread {
    public final Socket clientSocket;

    public ServerWorker(Socket clientSocket){
        this.clientSocket = clientSocket;
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
        OutputStream outputStream = clientSocket.getOutputStream();
        InputStream in = clientSocket.getInputStream();

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
        String input;
        input = bufferReader.readLine();
        while(input != null){
            String[] token = input.split(" ");
            System.out.println(token[0]);
            System.out.println(token.length);
            if(token != null && token.length > 0){
                String ref = token[0];
                if("quit".equalsIgnoreCase(ref)){
                    break;
                }
                String message = "unknown " +  ref;
                outputStream.write(message.getBytes());
                input = bufferReader.readLine();
            }
        }
        clientSocket.close();
    }
}
