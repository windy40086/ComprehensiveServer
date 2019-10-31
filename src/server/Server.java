package server;

import control.Channel;
import entity.User;
import util.XMLUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//主线程
public class Server {

    public static ArrayList<Channel> channels = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt((String) new XMLUtil().getBean("serverport"));
        ServerSocket ss = new ServerSocket(port);
        System.out.println(ss.getLocalSocketAddress());

        //心跳检测
        new Thread(() -> {
            while (true) {
                for (int i = 0; i < channels.size(); i++) {
                    channels.get(i).isAlive();
                }
            }
        }).start();

        //开始接受客户端
        while(true){
            try {
                Thread.sleep(1000);
                Socket socket = ss.accept();
                System.out.println("连接");
                User u = new User("windy@qq.com", "123456", socket);
                Channel channel = new Channel(u);
                channels.add(channel);
                new Thread(channel).start();
            } catch (Exception e) {
            }
        }
    }
}
