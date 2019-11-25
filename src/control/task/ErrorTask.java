package control.task;

import entity.Message;
import inter.IChannel;
import inter.ITask;

public class ErrorTask implements ITask {
    @Override
    public boolean doTask(IChannel channel, Message message) {
        System.out.println("ErrorTask");
        message.setType(TYPE_ERROR);
        return sendMessage(channel, message.toString());
    }

    private boolean sendMessage(IChannel channel, String message) {
        channel.send(message);
        return true;
    }
}
