package control.task;

import entity.Message;
import inter.IChannel;
import inter.ITask;
import server.ChannelServer;
import service.HistoryService;
import service.TokenService;

public class RelayTask implements ITask {
    @Override
    public boolean doTask(IChannel channel, Message message) {
        System.out.println("RelayTask");
        //如果用户发送的token和服务器token不合，则拒绝发送消息
        if (null == channel.getUser().getId() || null == message.getToken()) {
            //没有id或token 拒绝发送消息
            return false;
        }
        if (!TokenService.getToken(channel.getUser().getId()).equals(message.getToken())) {
            //如果id和token不符合 拒绝发送消息
            Message m = new Message();
            m.setType(TYPE_ERROR);
            m.setResult(RESULT_FAIL);
            m.setError(ERROR_RELAY_TOKEN_IS_EXPIRATION);
            send(channel, m.toString());
            return false;
        }

        Message result = relay(message);
        return sendMessage(channel, result);
    }

    //发送信息
    private static boolean sendMessage(IChannel channel, Message message) {
        //设置访客信息
        Message guest = new Message();
        guest.setType(TYPE_RELAY);
        guest.setMsg("您还未注册，无法接受消息");

        //返回发送端消息
        send(channel, message.toString());
        message.setToken(null);
        ////////////////////////////////////////////////////////////////////
        //如果Receiver是系统大群
        if (message.getReceiver().equals("1")) {
            boolean isSuccess = false;
            for (IChannel other : ChannelServer.getChannels()) {
                //跳过自己
//                if (null != other.getUserId() && other.getUserId().equals(channel.getUser().getId())) {
//                    continue;
//                }
                if (other == channel) {
                }
                //发送给所有登录的人
                else if (null != other.getUserId()) {
                    isSuccess = send(other, message.toString());
                }
                //发送给未登录的人
                else {
                    isSuccess = send(other, guest.toString());
                }
            }
            return isSuccess;
        } else if (Integer.parseInt(message.getReceiver()) > 1000000000) {
            //在频道中找到接受人的id并发送
            for (IChannel ch : ChannelServer.getChannels()) {
                if (null != ch.getUserId() && ch.getUserId().equals(message.getReceiver())) {
                    return send(ch, message.toString());
                }
            }
            return false;
        } else {
            return false;
        }
    }

    private static boolean send(IChannel channel, String message) {
        return channel.send(message);
    }

    //记录数据库并获取返回信息
    private static Message relay(Message mi) {
        //这里需要通过User来过度
        Message msg = new Message();
        msg.setType(mi.getType());
        msg.setUid(mi.getUid());
        msg.setReceiver(mi.getReceiver());
        msg.setMsg(mi.getMsg());
        msg.setToken(mi.getToken());

        //通过Account | Receiver记录历史记录
        HistoryService.insertHM(msg.getUid(), msg.getReceiver(), msg.getMsg(), msg.getType());

        //返回当前的Cursor
        String Cursor = HistoryService.getCursor(msg.getUid());
        msg.setCursor(Cursor);

        return msg;
    }
}
