package entity;

public class MsgInfo {
    private String type;

    private String account;
    private String password;
    private String msg;
    private String receive;
    private String error;
    private String vc;
    private String cursor;

    private String time;

    public String getVc() {
        return vc;
    }

    public void setVc(String vc) {
        this.vc = vc;
    }


    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

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
        if (type == null) {
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

    public boolean isTypeExist() {
        return type != null;
    }

    public boolean isAccountExist() {
        return account != null;
    }

    public boolean isPasswordExist() {
        return password != null;
    }

    public boolean isMsgExist() {
        return msg != null;
    }

    public boolean isReceiveExist() {
        return receive != null;
    }

    public boolean isErrorExist() {
        return error != null;
    }

    public boolean isTimeExist() {
        return time != null;
    }

    public boolean isVCExist() {
        return vc != null;
    }

}
