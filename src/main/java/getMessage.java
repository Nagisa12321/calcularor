import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class getMessage extends Thread{
    private final DataInputStream dataIn;

    public getMessage(Socket theSocket) throws IOException {
        this.dataIn = new DataInputStream(theSocket.getInputStream());
    }

    public void run(){
        try {
            String theString = dataIn.readUTF();
            System.out.println("从服务器收到信息，该信息为: " + theString + "\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
