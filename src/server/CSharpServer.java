package server;

import util.Log;

import java.io.IOException;
import java.net.ServerSocket;

public class CSharpServer {

    private static ServerSocket Server = null;

    private CSharpServer() {
    }

    public static ServerSocket getServer(String port) {
        if (Server == null) {
            try {
                Server = new ServerSocket(Integer.parseInt(port));
                Log.d("CSharp主服务器打开在：" + Server.getLocalSocketAddress());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Server;
    }
}
