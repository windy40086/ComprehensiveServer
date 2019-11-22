package entity;

import inter.IError;
import inter.IType;

public class Message implements IType, IError {
    private String type = null;

    private String uid = null;
    private String account = null;
    private String password = null;
    private String email = null;
    private String phone = null;
    private String receiver = null;
    private String msg = null;
    private String result = null;
    private String error = null;
    private String cursor = null;
    private String vc = null;

    private String time = null;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isUIDExist() {
        return uid != null;
    }

    public String getVc() {
        return vc;
    }

    public void setVc(String vc) {
        this.vc = vc;
    }

    public boolean isVCExist() {
        return vc != null;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public boolean isCursorExist() {
        return cursor != null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isTypeExist() {
        return type != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailExist() {
        return email != null;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPhoneExist() {
        return phone != null;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isAccountExist() {
        return account != null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordExist() {
        return password != null;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public boolean isReceiverExist() {
        return receiver != null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isMsgExist() {
        return msg != null;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean isResultExist() {
        return result != null;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String createTime) {
        this.time = createTime;
    }

    public boolean isTimeExist() {
        return time != null;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setError(int error) {
        this.error = error + "";
    }

    public boolean isErrorExist() {
        return error != null;
    }

    public String toString() {
        switch (type) {
            case TYPE_SYSTEM:
                return toSystem();
            case TYPE_LOGIN:
                return toLogin();
            case TYPE_REGISTER:
                return toRegister();
            case TYPE_RELAY:
                return toRelay();
            case TYPE_ERROR:
                return toError();
            default:
                return null;
        }
    }

    //4
    private String toError() {
        return STRING_TYPE + "=" + type + "&" + STRING_ERR + "=" + error;
    }

    //1
    private String toLogin() {
        return STRING_TYPE + "=" + type + "&" + STRING_RESULT + "=" + result + "&" + STRING_ERR + "=" + error + "&" + STRING_UID + "=" + uid;
    }

    //2
    private String toRegister() {
        return STRING_TYPE + "=" + type + "&" + STRING_RESULT + "=" + result + "&" + STRING_ERR + "=" + error;
    }

    //0
    private String toSystem() {
        return STRING_TYPE + "=" + type + "&" + STRING_UID + "=" + uid + "&" + STRING_MSG + "=" + msg;
    }

    //3
    private String toRelay() {
        return STRING_TYPE + "=" + type + "&" + STRING_UID + "=" + uid + "&" + STRING_RECEIVER + "=" + receiver + "&" + STRING_MSG + "=" + msg + "&" + STRING_CURSOR + "=" + cursor;
    }

}
