package server;

import control.Channel;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    private static ServerSocket Server = null;

    public static ArrayList<Channel> channels = new ArrayList<>();

    private Server() {
    }

    public static ServerSocket getServer(String port) {
        if (Server == null) {
            try {
                Server = new ServerSocket(Integer.parseInt(port));
                System.out.println("主服务器打开在：" + Server.getLocalSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Server;
    }
}