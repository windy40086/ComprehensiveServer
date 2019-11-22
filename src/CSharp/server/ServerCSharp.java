package CSharp.server;

import control.Channel;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerCSharp {

    private static ServerSocket Server = null;

    private static ArrayList<Channel> channels = new ArrayList<>();

    public static ArrayList<Channel> getChannels() {
        return channels;
    }

    private ServerCSharp() {
    }

    public static ServerSocket getServer(int port) {
        if (Server == null) {
            try {
                Server = new ServerSocket(port);
                System.out.println("主服务器打开在：" + Server.getLocalSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Server;
    }

}