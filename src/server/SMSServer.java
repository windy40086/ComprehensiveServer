package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SMSServer {
    private static ServerSocket Server = null;

    private static Socket SMSClient;

    private static DataOutputStream dos;

    private static String port = "10442";

    public static boolean isSMSClientCon(){
        return SMSClient != null;
    }

    private SMSServer() {
    }

    private static ServerSocket getServer(String port) {
        if (Server == null) {
            try {
                Server = new ServerSocket(Integer.parseInt(port));
                System.out.println("短信发送服务器打开在：" + Server.getLocalSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Server;
    }

    private static void getCon() {
        try {
            while(true){
                if(SMSClient == null){
                    SMSClient = Server.accept();
                    System.out.println("短信发送机已经登录");
                    dos = new DataOutputStream(SMSClient.getOutputStream());
                }
            }
        } catch (IOException e) {
            SMSClient = null;
            e.printStackTrace();
        }
    }

    public static void start() {
        getServer(port);
        getCon();
    }

    public static boolean sendMsg(String msg) {
        try {
            dos.writeUTF(msg);
            return true;
        } catch (IOException e) {
            SMSClient = null;
            e.printStackTrace();
        }
        return false;
    }
}
