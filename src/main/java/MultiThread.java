import java.io.*;
import java.net.Socket;

public class MultiThread extends Thread {
    private final Socket theSocket;

    public MultiThread(Socket theSocket) {
        this.theSocket = theSocket;
    }

    public void run() {
        String theString;
        try {
            System.out.println(theSocket.getInetAddress() + " : " + theSocket.getPort() + "加入了服务器\n");
            while (true) {
                InputStream in = theSocket.getInputStream();
                DataInputStream dataIn = new DataInputStream(in);
                theString = dataIn.readUTF();
                //发送信息
                OutputStream out = theSocket.getOutputStream();
                DataOutputStream dataOut = new DataOutputStream(out);
                System.out.println(theSocket.getInetAddress() + " : " + theSocket.getPort() +
                        "请求计算，结果是 : " + MyCalculator.Calculate(theString));
                dataOut.writeUTF(MyCalculator.Calculate(theString));
                dataOut.flush();
            }
        } catch (IOException ignored) {
        }
    }
}
