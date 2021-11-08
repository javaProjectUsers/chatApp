import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        int port = 8818;
        Connect2Server instance = new Connect2Server(port);
        instance.connection = true;

    }
}
