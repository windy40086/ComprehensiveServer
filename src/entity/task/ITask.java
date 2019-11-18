package entity.task;

import entity.Message;

public interface ITask {

    //需要执行的任务
    boolean doTask(Message message);

    //执行完后的返回值
    boolean SendMessage(Message message);
}
