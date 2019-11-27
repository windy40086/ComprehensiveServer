package service;

import util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CSharpStreamService {

    private static final int MAX_SIZE = 1000;

    public static String receive(Socket client) {
        StringBuilder message = new StringBuilder();
        try {
            DataInputStream dis = new DataInputStream(client.getInputStream());
            byte[] temp_len = new byte[8];

            dis.read(temp_len, 0, 8);

            int len = 0;
            try {
                len = Integer.parseInt(new String(temp_len, 0, 8));
            } catch (Exception ignored) {
                return "";
            }


            byte[] msg = new byte[MAX_SIZE];

            while (len != 0) {
                len -= dis.read(msg);
                message.append(new String(msg, 0, msg.length));
                if (len <= MAX_SIZE) {
                    dis.read(msg, 0, len);
                    message.append(new String(msg, 0, len));
                    break;
                }
            }
        } catch (Exception e) {
//            System.err.println("CSharp Stream 18");
        }
        return message.toString();
    }

    public static void send(Socket client, String msg) {
        try {
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            byte[] message = msg.getBytes();
            StringBuilder len = new StringBuilder(message.length + "");
            while (len.length() < 8) {
                len.insert(0, "0");
            }
            dos.write((len + msg).getBytes());
            dos.flush();
            Log.d("发送完毕:" + msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
