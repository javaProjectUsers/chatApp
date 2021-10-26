public class Main {
    public static void main(String[] args) {
        // Chat server with chat socket
        int port = 8818;
        ServerBase server = new ServerBase(port);
        server.start();
        System.out.println("started...");      
    }
}