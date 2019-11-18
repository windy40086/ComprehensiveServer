package service;

import control.Channel;
import entity.Message;
import server.Server;
import util.CloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class StreamService {

    public static void sendMsg(Socket s, String msg) throws IOException {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(msg);
        System.out.println("发送内容：" + msg);
        dos.flush();
    }

    public static String reciMsg(Socket s) throws IOException {
        DataInputStream dis = new DataInputStream(s.getInputStream());
        String msg = dis.readUTF();
        System.out.println("接受内容：" + msg);
        return msg;
    }
}
