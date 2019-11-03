package entity;

public class MsgInfo {
    private String type;

    private String account;
    private String password;
    private String msg;
    private String receive;
    private String error;

    private String time;

    public MsgInfo() {
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getType() {
        if(type == null){
            return "0";
        }
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
