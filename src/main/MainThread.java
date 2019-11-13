package main;

import control.Channel;
import entity.User;
import server.SMSServer;
import server.Server;
import util.XMLUtil;

import java.net.Socket;

//主线程
public class MainThread {

    public static void main(String[] args) {
        //MainServer
        new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        }).start();
        //SMSServer
        new Thread(new Runnable() {
            @Override
            public void run() {
                startSMSServer();
            }
        }).start();
    }

    //开启主服务
    private static void startServer() {
        String port = (String) XMLUtil.getBean("serverport");
        //开始接受客户端
        while (true) {
            try {
                Thread.sleep(1000);
                //如服务器异常关闭，会在这里重启
                Socket socket = Server.getServer("10443").accept();
                //User u = new User("windy@qq.com", "123456", socket);
                User u = new User(socket);
                Channel channel = new Channel(u);
                Server.channels.add(channel);

                System.out.println(channel + "已经连接");

                new Thread(channel).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //开启短信验证服务

    private static void startSMSServer(){
        SMSServer.start();
    }
}
