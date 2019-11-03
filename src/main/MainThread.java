package main;

import control.Channel;
import entity.User;
import server.Server;
import util.XMLUtil;

import java.io.IOException;
import java.net.Socket;

//主线程
public class MainThread {
    public static void main(String[] args) throws IOException {

        String port = (String) new XMLUtil().getBean("serverport");
        //开始接受客户端
        while (true) {
            try {
                Thread.sleep(1000);
                //如服务器异常关闭，会在这里重启
                Socket socket = Server.getServer(port).accept();
                User u = new User("windy@qq.com", "123456", socket);
                Channel channel = new Channel(u);
                System.out.println(channel + "已经连接");
                Server.channels.add(channel);
                new Thread(channel).start();
            } catch (Exception e) {
            }
        }
    }
}
