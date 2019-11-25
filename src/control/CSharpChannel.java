package control;

import entity.Message;
import entity.User;
import inter.IChannel;
import inter.ITask;
import inter.IType;
import server.ChannelServer;
import service.CSharpStreamService;
import util.CloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSharpChannel implements IChannel,Runnable, IType {

    private User user;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isRunning = true;

    public CSharpChannel(User user) {
        this.user = user;
        try {
            dis = new DataInputStream(this.user.getClient().getInputStream());
            dos = new DataOutputStream(this.user.getClient().getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            CloseUtil.Close(dis, dos);
            isRunning = false;
        }
    }

    @Override
    public String receive() {
        try {
            return CSharpStreamService.receive(user.getClient());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean send(String message) {
        try{
            CSharpStreamService.send(user.getClient(),message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Socket getClient() {
        return user.getClient();
    }

    @Override
    @Deprecated
    public boolean sendObject(Object obj) {
        return false;
    }

    //客户端断开连接
    private void disconnection() {
        try {
            this.user.getClient().close();
            System.out.println(this + "已经断开");
            ChannelServer.getChannels().remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public String getUserAccount() {
        return user.getAccount();
    }

    @Override
    public String getUserId() {
        return user.getId();
    }

    private String getUserInfo() {
        if (user.getAccount() == null) {
            return this + "还未登录";
        } else {
            return "Channel:{id=" + getUserId() + ",account=" + user.getAccount() + ",password=" + user.getPassword() + "}";
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------");
            String s = receive();
            if (s == null || s.trim().equals("")) {
                disconnection();
                break;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒SSSS毫秒");
            System.out.println(getUserInfo());
            System.out.println("接收到" + this + "的消息：" + s);
            System.out.println("时间：" + sdf.format(new Date()));

            //处理消息
            ITask task = Analyze.analyze(s);
            Message m = Analyze.getMessage(s);

            //执行任务
            boolean isS = task.doTask(this, m);

            System.out.println("发送结果：" + (isS ? "成功" : "失败"));
        }
    }
}
