package service;

import com.sun.corba.se.spi.ior.ObjectKey;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AndroidStreamService {

    //发送dos消息
    public static void sendMsg(Socket s, String msg) throws IOException {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(msg);
        dos.flush();
        System.out.println("发送内容：" + msg);
    }

    //接受dis消息
    public static String reciMsg(Socket s) throws IOException {
        DataInputStream dis = new DataInputStream(s.getInputStream());
        String msg = dis.readUTF();
        System.out.println("接受内容：" + msg);
        return msg;
    }

    //发送Object信息
    public static void sendObject(Socket s, Object messages) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(messages);
        oos.flush();
        System.out.println("发送内容:" + messages);
    }
}
