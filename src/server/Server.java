package server;

import control.Channel;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Server {
    
    private static ServerSocket Server = null;

    private static ArrayList<Channel> channels = new ArrayList<>();

    public static ArrayList<Channel> getChannels() {
        return channels;
    }

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