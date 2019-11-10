package entity.message;

import control.Channel;
import inter.IError;
import inter.IType;
import inter.message.IRegisterMessage;
import service.StreamService;

import java.io.IOException;
import java.net.Socket;

public class RegisterMessage implements IRegisterMessage, IError, IType {

    private String requestType;
    private String account;
    private String password;


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
