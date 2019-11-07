package control;

import entity.Message;
import entity.User;
import inter.IType;
import server.Server;
import service.StreamService;
import util.CloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

    //群发数据
    private void sendOthers(Message m) {
//        for(Channel other:Server.channels){
//            if(other.user.getAccount().equals(this.user.getAccount())){
//                continue;
//            }
//            other.send(m);
//        }
        //根据Socket来判断用户
        for (Channel other : Server.channels) {
            if (other == this) {
                continue;
            }
            other.send(m);
        }
    }

    //登录消息
    private void system_login_msg(Message m) {
//        for(Channel other:Server.channels){
//            if(other.user.getAccount().equals(this.user.getAccount())){
//                other.send(m);
//                break;
//            }
//        }
        //根据Socket来判断用户是否为自己
        this.send(m);
    }

    //注册消息
    private void system_register_msg(Message m) {
        this.send(m);
    }

    //系统消息
    private void system_msg(Message m) {
        for (Channel ch : Server.channels) {
            ch.send(m);
        }
    }

    //错误信息
    private void error_msg(Message m) {
        this.send(m);
    }

    //接受数据
    private String receive() {
        try {
            return StreamService.reciMsg(user.getClient());
        } catch (Exception e) {
            disconnection();
            return null;
        }
    }

    //客户端断开连接
    private void disconnection() {
        System.out.println(this + "已经断开");
        Server.channels.remove(this);
    }

    //发送数据
    private void send(Message m) {
        try {
            StreamService.sendMsg(user.getClient(), m.toString());
        } catch (IOException e) {
            disconnection();
        }
    }

    //获取自己的User信息
    private String getUserInfo() {
        if (user.getAccount() == null) {
            return this + "还未登录";
        } else {
            return "Channel:{account=" + user.getAccount() + ",password=" + user.getPassword() + "}";
        }
    }

    public String getUserAccount(){
        return user.getAccount();
    }

    //分类发送消息
    private void sortMsg(Message msg) {
        switch (msg.getType()) {
            case TYPE_LOGIN:
                //转为登录返回消息
                system_login_msg(msg);
                break;
            case TYPE_RELAY:
                //根据receive判断发送人
                //先判断receive是人还是群
                //再找到并发送

                //如果receive为1则为默认大群
                if (msg.getReceiver().equals("1")) {
                    sendOthers(msg);
                    break;
                }

                //在频道中找到接受人的id并发送
                for (Channel ch : Server.channels) {
                    if (null != ch.user.getAccount() && ch.user.getAccount().equals(msg.getReceiver())) {
                        ch.send(msg);
                    }
                }

                break;
            case TYPE_REGISTER:
                system_register_msg(msg);
                break;
            case TYPE_SYSTEM:
                system_msg(msg);
                break;
            case TYPE_ERROR:
                error_msg(msg);
                break;
            default:
                break;
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            System.out.println();
            String s = receive();
            if (s == null) {
                return;
            }
            System.out.println(getUserInfo());
            System.out.println("接收到" + this + "的消息：" + s);

            //处理消息
            Message msg = Analyze.analyzeMessage(this.user, s);
            if (null == msg) {
                isRunning = false;
                disconnection();
                continue;
            }

            //分类发送消息
            sortMsg(msg);

        }
    }
}
