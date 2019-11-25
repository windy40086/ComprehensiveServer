package inter;

import entity.Message;
import entity.User;

public interface ITask extends IType, IError {

    //需要执行的任务
    boolean doTask(IChannel channel, Message message);

}
