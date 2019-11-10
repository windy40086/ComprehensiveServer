package entity.message;

import control.Channel;
import inter.IError;
import inter.IType;
import inter.message.ILoginMessage;
import service.StreamService;

public class LoginMessage implements ILoginMessage, IError, IType {

    private String requestType;
    private String account;
    private String password;
    private String result;
    private String error;


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
        return TYPE + "=" + requestType + "&" + ACCOUNT + "=" + account + "&" + PASSWORD + "=" + password;
    }
}
