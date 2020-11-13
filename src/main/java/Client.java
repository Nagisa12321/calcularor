import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("*------------Welcome to my calculator--------------*\n" +
                           "|                                                  |\n"+
                           "|Author : JT Chen                                  |\n"+
                           "|Class : 184                                       |\n"+
                           "|Num : 1806200013                                  |\n"+
                           "|                                                  |\n"+
                           "|       --- Please input like this----             |\n"+
                           "|       eg: 3 * 6 + ( 9 - 7 )                      |\n"+
                           "|                                                  |\n"+
                           "*--------------------------------------------------*");
        Socket theSocket = null;
        try {
            theSocket = new Socket("127.0.0.1", 15);
            theSocket.setSoTimeout(15000);
        } catch (IOException e) {
            System.out.println("无法和服务器创建链接\n");
            System.exit(1);
        }
        while (true) {
            try {
                System.out.println("请输入你要计算的式子(按#退出程序)");
                String theString = in.nextLine();
                if (theString.equals("#")) break;
                OutputStream out = theSocket.getOutputStream();
                DataOutputStream dataOut = new DataOutputStream(out);
                dataOut.writeUTF(theString);
                dataOut.flush();
                Thread theThread = new getMessage(theSocket);
                theThread.start();
                theThread.join();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
