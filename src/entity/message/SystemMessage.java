package entity.message;

import control.Channel;
import inter.IError;
import inter.IType;
import inter.message.ISystemMessage;
import server.Server;
import service.StreamService;

public class SystemMessage implements ISystemMessage, IError, IType {

    private String requestType;
    private String account;
    private String receiver;
    private String message;

    public SystemMessage(String requestType, String account, String receiver, String message) {
        this.requestType = requestType;
        this.account = account;
        this.receiver = receiver;
        this.message = message;
    }


    @Override
    public String getSendMessage() {
        return null;
    }

    @Override
    public boolean isMsgCorrect() {
        return false;
    }

    @Override
    public void send(Channel this_ch) {

    }

    @Override
    public String toString() {
        return TYPE + "=" + requestType + "&" + RECEIVER + "=" + receiver + "&" + MSG + "=" + message;
    }
}
