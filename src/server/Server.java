package server;

import control.Channel;
import entity.User;
import util.XMLUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

//主线程
public class Server {

    public static ArrayList<Channel> channels = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        int user_count = 0;
        int port = Integer.parseInt((String) new XMLUtil().getBean("serverport"));
        ServerSocket ss = new ServerSocket(port);
        System.out.println(ss.getLocalSocketAddress());

        //开始接受客户端
        while(true){
            try {
                Thread.sleep(1000);
                Socket socket = ss.accept();
                Date d = new Date();
                System.out.println("连接,第" + user_count + "次,连接时间:" + d.getTime());
                User u = new User("windy@qq.com", "123456", socket);
                Channel channel = new Channel(u);
                channels.add(channel);
                new Thread(channel).start();
            } catch (Exception e) {
            }
        }
    }
}
