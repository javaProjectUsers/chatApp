import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

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
        OutputStream out = clientSocket.getOutputStream();
        InputStream in = clientSocket.getInputStream();

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(in));
        String input;
        input = bufferReader.readLine();
        while(input != null){
            if("quit".equalsIgnoreCase(input)){
                break;
            }
            String clientOutput = "You typed " + input + "\n";
            out.write(clientOutput.getBytes());
            input = bufferReader.readLine();
        }
        clientSocket.close();
    }
}
