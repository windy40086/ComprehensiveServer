package entity.task;

import entity.Message;

public class LoginTask implements ITask {

    @Override
    public boolean doTask(Message message) {
        return false;
    }

    @Override
    public boolean SendMessage(Message message) {
        return false;
    }
}
