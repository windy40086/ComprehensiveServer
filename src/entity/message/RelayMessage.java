package entity.message;

import control.Channel;
import inter.IError;
import inter.IType;
import inter.message.IRelayMessage;
import server.Server;
import service.StreamService;

public class RelayMessage implements IRelayMessage, IType, IError {

    private String requestType;
    private String account;
    private String receiver;
    private String message;

    public RelayMessage(String requestType, String account, String receiver, String message) {
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
        return TYPE + "=" + requestType + "&" + ACCOUNT + "=" + account + "&" + RECEIVER + "=" + receiver + "&" + MSG + "=" + message;
    }
}
