package control;

import entity.Message;
import entity.User;
import inter.ITask;
import inter.IType;
import server.ServerAndroid;
import service.StreamService;
import util.CloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

//频道类
public class Channel implements Runnable, IType {
    private User user;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isRunning = true;

    //初始化用户
    public Channel(User user) {
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
    private String receive() {
        try {
            return StreamService.reciMsg(user.getClient());
        } catch (Exception e) {
            return null;
        }
    }

    //客户端断开连接
    private void disconnection() {
        System.out.println(this + "已经断开");
        ServerAndroid.getChannels().remove(this);
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
    public String getUserAccount() {
        return user.getAccount();
    }

    //获取这个Channel的ID
    public String getUserId() {
        return user.getId();
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public void run() {
        while (isRunning) {
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------");
            String s = receive();
            if (s == null) {
                disconnection();
                break;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒SSSS毫秒");
            System.out.println(getUserInfo());
            System.out.println("接收到" + this + "的消息：" + s);
            System.out.println("时间："+sdf.format(new Date()));

            //处理消息
            ITask task = Analyze.analyze(s);
            Message m = Analyze.getMessage(s);

            //执行任务
            task.doTask(this.user, m);
        }
    }
}
