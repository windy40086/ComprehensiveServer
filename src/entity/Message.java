package entity;

public class Message {
    private String msg;
    private String msgAccount;
    private String createTime;

    public Message(String msg, String msgAccount, String createTime) {
        this.msg = msg;
        this.msgAccount = msgAccount;
        this.createTime = createTime;
    }

    public String getMsg() {
        return msg;
    }

    public String getMsgAccount() {
        return msgAccount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String toString(){
        return "account="+msgAccount+"&msg="+msg;
    }
}
