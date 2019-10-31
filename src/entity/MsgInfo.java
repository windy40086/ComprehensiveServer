package entity;

public class MsgInfo {
    private String type;
    private String password;
    private String msg;
    private String account;
    private String time;

    public MsgInfo() {
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getAccount() {
        return account;
    }

    public String getTime() {
        return time;
    }
}
