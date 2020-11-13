
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(15)) {
            while (true) {
                try {
                    Socket connection = server.accept();
                    Thread thread = new MultiThread(connection);
                    thread.start();
                } catch (IOException ignored) {
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);

        }
    }
}