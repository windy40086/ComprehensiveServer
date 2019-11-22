package control.task;

import entity.Message;
import entity.User;
import inter.IError;
import inter.ITask;
import inter.IType;
import service.HistoryService;
import service.StreamService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryTask implements ITask, IType, IError {
    @Override
    public boolean doTask(User u, Message message) {
        System.out.println("HistoryTask");

        String uid = message.getUid();
        String receiver = message.getReceiver();
        String cursor = message.getCursor();

        List<String> messages = getHistory(uid, receiver, cursor);

        return sendObject(u, messages);
    }

    private boolean sendObject(User u, Object messages) {
        try {
            StreamService.sendObject(u.getClient(),messages);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private ArrayList<String> getHistory(String uid, String receiver, String cursor) {
        return HistoryService.getMessages(uid, receiver, cursor);
    }
}
