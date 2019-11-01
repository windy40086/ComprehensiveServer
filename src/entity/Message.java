package entity;

import inter.IError;
import inter.IType;

public class Message implements IType, IError {
    private String type;

    private String account;
    private String password;
    private String receiver;
    private String msg;
    private String result;
    private String error;

    private String createTime;

    public Message(){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String toString(){
        switch (type) {
            case TYPE_SYSTEM:
                return toSystem();
            case TYPE_LOGIN:
                return toLogin();
            case TYPE_REGISTER:
                return toRegister();
            case TYPE_RELAY:
                return toRelay();
            default:
                return null;
        }
    }

    public String toLogin() {
        return TYPE + "=" + type + "&" + RESULT + "=" + result + "&" + ERR + "=" + error;
    }

    public String toRegister() {
        return TYPE + "=" + type + "&" + MSG + "=" + msg;
    }

    public String toSystem() {
        return TYPE + "=" + type + "&" + ACCOUNT + "=" + account + "&" + MSG + "=" + msg;
    }

    public String toRelay() {
        return TYPE + "=" + type + "&" + ACCOUNT + "=" + account + "&" + MSG + "=" + msg;
    }
}
