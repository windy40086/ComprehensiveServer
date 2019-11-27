package server;

import inter.IChannel;
import util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class AndroidServer {
    
    private static ServerSocket Server = null;

    private AndroidServer() {
    }

    public static ServerSocket getServer(String port) {
        if (Server == null) {
            try {
                Server = new ServerSocket(Integer.parseInt(port));
                Log.d("Android主服务器打开在：" + Server.getLocalSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Server;
    }

}