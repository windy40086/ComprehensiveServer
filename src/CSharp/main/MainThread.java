package CSharp.main;


import CSharp.server.ServerCSharp;
import CSharp.service.CSharpStreamService;

import java.io.IOException;
import java.net.Socket;

public class MainThread {
    public static void main(String[] args) {
        new Thread(() -> {
            while(true)
            try {
                Socket client = ServerCSharp.getServer(11443).accept();

//                    String msg = CSharpStreamService.receive(client);

//                    System.out.println(msg);

                CSharpStreamService.send(client,"this is a message");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
