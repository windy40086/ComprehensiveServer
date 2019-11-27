package control;

import entity.Message;
import entity.User;
import inter.IChannel;
import inter.ITask;
import inter.IType;
import server.AndroidServer;
import server.ChannelServer;
import service.AndroidStreamService;
import util.CloseUtil;
import util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

//频道类
public class AndroidChannel implements IChannel,Runnable, IType {
    private User user;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isRunning = true;

    //初始化用户
    public AndroidChannel(User user) {
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

    //接受数据
    @Override
    public String receive() {
        try {
            return AndroidStreamService.reciMsg(user.getClient());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean send(String message) {
        try{
            AndroidStreamService.sendMsg(user.getClient(),message);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean sendObject(Object obj){
        try{
            AndroidStreamService.sendObject(user.getClient(),obj);
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

    //客户端断开连接
    private void disconnection() {
        try {
            this.user.getClient().close();
            Log.d(this + "已经断开");
            ChannelServer.getChannels().remove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取自己的User信息
    private String getUserInfo() {
        if (user.getAccount() == null) {
            return this + "还未登录";
        } else {
            return "Channel:{id=" + getUserId() + ",account=" + user.getAccount() + ",password=" + user.getPassword() + "}";
        }
    }

    //获取这个Channel的用户名
    @Override
    public String getUserAccount() {
        return user.getAccount();
    }

    //获取这个Channel的ID
    @Override
    public String getUserId() {
        return user.getId();
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void run() {
        while (isRunning) {
            Log.d("--------------------------------------------------------------------------------");
            String s = receive();
            if (s == null) {
                disconnection();
                break;
            }


            Log.d(getUserInfo());
            Log.d("接收到" + this + "的消息：" + s);


            //处理消息
            ITask task = Analyze.analyze(s);
            Message m = Analyze.getMessage(s);

            //执行任务
            boolean isS = task.doTask(this, m);

            Log.d("发送结果：" + (isS ? "成功" : "失败"));
        }
    }
}
