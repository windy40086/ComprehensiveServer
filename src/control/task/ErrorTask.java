package control.task;

import entity.Message;
import entity.User;
import inter.ITask;
import service.StreamService;

import java.io.IOException;

public class ErrorTask implements ITask {
    @Override
    public boolean doTask(User u, Message message) {
        System.out.println("ErrorTask");
        message.setType(TYPE_ERROR);
        return sendMessage(u, message.toString());
    }

    private boolean sendMessage(User u, String message) {
        try {
            StreamService.sendMsg(u.getClient(), message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
