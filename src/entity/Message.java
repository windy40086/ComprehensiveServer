package entity;

import inter.IType;

public class Message implements IType {
    private String type;
    private String msg;
    private String account;
    private String receiver;
    private String password;
    private String createTime;

    //系统消息
    public Message(String type, String msg, String account, String password, String createTime) {
        this.type = type;
        this.msg = msg;
        this.account = account;
        this.password = password;
        this.createTime = createTime;
    }

    //用户消息
    public Message(String type, String msg, String account, String receiver) {
        this.type = type;
        this.msg = msg;
        this.account = account;
        this.receiver = receiver;
    }

    public String getAccount() {
        return account;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }

    public String getMsg() {
        return msg;
    }

    public String getCreateTime() {
        return createTime;
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
        return TYPE + "=" + type + "&" + MSG + "=" + msg;
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
