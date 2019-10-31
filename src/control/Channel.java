package control;

import entity.Message;
import entity.User;
import server.Server;
import util.CloseUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//频道类
public class Channel implements Runnable {
    private User user;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isRunning = true;

    //初始化
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

    //发送数据
    public void send(Message m) {
        try {
            StreamService.sendMsg(user.getClient(), m.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Server.channels.remove(this);
        }
    }

    //群发数据
    public void sendOthers(Message m){
        for(Channel other:Server.channels){
            if(other==this){
                continue;
            }
            other.send(m);
        }
    }

    //接受数据
    public String receive(){
        try {
            return StreamService.reciMsg(user.getClient());
        }catch (Exception e){
            e.printStackTrace();
            Server.channels.remove(this);
            return null;
        }
    }

    @Override
    public void run() {
        while(isRunning){
            String s = receive();
            sendOthers(Analyze.analyzeMessage(s));
        }
    }
}
