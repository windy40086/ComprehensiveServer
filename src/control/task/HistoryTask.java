package control.task;

import entity.Message;
import entity.User;
import inter.IChannel;
import inter.IError;
import inter.ITask;
import inter.IType;
import service.HistoryService;
import service.AndroidStreamService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistoryTask implements ITask, IType, IError {
    @Override
    public boolean doTask(IChannel channel, Message message) {
        System.out.println("HistoryTask");

        String uid = message.getUid();
        String receiver = message.getReceiver();
        String cursor = message.getCursor();

        List<String> messages = getHistory(uid, receiver, cursor);

        return sendObject(channel, messages);
    }

    private boolean sendObject(IChannel channel, Object messages) {
        return channel.sendObject(messages);
    }

    private ArrayList<String> getHistory(String uid, String receiver, String cursor) {
        return HistoryService.getMessages(uid, receiver, cursor);
    }
}
