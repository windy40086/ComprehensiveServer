package control.task;

import entity.Message;
import inter.IChannel;
import inter.ITask;
import util.Log;

public class SystemTask implements ITask {
    @Override
    public boolean doTask(IChannel channel, Message message) {
        Log.d("SystemTask");
        return false;
    }

//    private boolean sendMessage(){
//        private void system_msg(Message m) {
//            for (Channel ch : Server.getChannels()) {
//                ch.send(m);
//            }
//        }
//        return false;
//    }
}
