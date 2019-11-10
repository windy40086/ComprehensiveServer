package entity.message;

import control.Channel;
import inter.IError;
import inter.IType;
import inter.message.IErrorMessage;

public class ErrorMessage implements IErrorMessage, IError, IType {

    private String requestType;
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
        return TYPE + "=" + requestType + "&" + ERR + "=" + error;
    }
}
