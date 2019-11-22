package control.task;

import control.Channel;
import entity.Message;
import entity.User;
import inter.ITask;
import server.ServerAndroid;
import service.HistoryService;
import service.StreamService;

import java.io.IOException;

public class RelayTask implements ITask {
    @Override
    public boolean doTask(User u, Message message) {
        System.out.println("RelayTask");
        Message result = relay(message);
        return sendMessage(u, result);
    }

    //发送信息
    private static boolean sendMessage(User u, Message message) {
        //如果Receiver是系统大群
        if (message.getReceiver().equals("1")) {
            boolean isSuccess = false;
            for (Channel other : ServerAndroid.getChannels()) {
                if (null != other.getUserId() && other.getUserId().equals(u.getId())) {
                    continue;
                }
                isSuccess = send(other.getUser(), message.toString());
            }
            return isSuccess;
        } else {
            //在频道中找到接受人的id并发送
            for (Channel ch : ServerAndroid.getChannels()) {
                if (null != ch.getUserId() && ch.getUserId().equals(message.getReceiver())) {
                    return send(ch.getUser(), message.toString());
                }
            }
            return false;
        }
    }

    private static boolean send(User u, String message) {
        try {
            StreamService.sendMsg(u.getClient(), message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //记录数据库并获取返回信息
    private static Message relay(Message mi) {
        //这里需要通过User来过度
        Message msg = new Message();
        msg.setType(mi.getType());
        msg.setUid(mi.getUid());
        msg.setReceiver(mi.getReceiver());
        msg.setMsg(mi.getMsg());

        //通过Account | Receiver记录历史记录
        HistoryService.insertHM(msg.getUid(), msg.getReceiver(), msg.getMsg(), msg.getType());

        //返回当前的Cursor
        String Cursor = HistoryService.getCursor(msg.getUid());
        msg.setCursor(Cursor);

        return msg;
    }
}
