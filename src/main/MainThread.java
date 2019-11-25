package main;

import control.AndroidChannel;
import control.CSharpChannel;
import entity.User;
import inter.IChannel;
import server.AndroidServer;
import server.CSharpServer;
import server.ChannelServer;
import server.SMSServer;

import java.net.Socket;

//主线程
public class MainThread {

    public static void main(String[] args) {

        ChannelServer.init();

        //AndroidServer
        new Thread(MainThread::startAndroidServer).start();

        //CSharpServer
        new Thread(MainThread::startCSharpServer).start();

        //SMSServer
        new Thread(MainThread::startSMSServer).start();

        //验证码清洗服务
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                boolean isRunning = true;
//                int sleep = 10;
//                System.out.println("短信清洗开启");
//                while (true) {
//                    try {
//                        if (!VCService.isVCEmpty()) {
//                            VCService.update_vc(sleep);
//                            Thread.sleep(sleep * 1000);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }

    //开启C#主服务
    private static void startCSharpServer() {
//        String port = (String) XMLUtil.getBean("server port");
        //开始接受客户端
        while (true) {
            try {
                Thread.sleep(1000);
                //如服务器异常关闭，会在这里重启
                Socket socket = CSharpServer.getServer("11443").accept();
                //User u = new User("windy@qq.com", "123456", socket);
                User u = new User(socket);
                IChannel channel = new CSharpChannel(u);
                ChannelServer.getChannels().add(channel);

                System.out.println(channel + "已经连接");

                new Thread(channel).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //开启安卓主服务
    private static void startAndroidServer() {
//        String port = (String) XMLUtil.getBean("serverport");
        //开始接受客户端
        while (true) {
            try {
                Thread.sleep(1000);
                //如服务器异常关闭，会在这里重启
                Socket socket = AndroidServer.getServer("10443").accept();
                //User u = new User("windy@qq.com", "123456", socket);
                User u = new User(socket);
                IChannel channel = new AndroidChannel(u);
                ChannelServer.getChannels().add(channel);

                System.out.println(channel + "已经连接");

                new Thread(channel).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //开启短信验证服务

    private static void startSMSServer() {
        SMSServer.start();
    }
}
