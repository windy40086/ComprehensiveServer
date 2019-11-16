package entity;

import java.net.Socket;

//用户实体类
public class User {
    private String id;
    private String account;
    private String password;
    private Socket client;

    public User(String account, String password, Socket client) {
        this.account = account;
        this.password = password;
        this.client = client;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public User(Socket client) {
        this.client = client;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Socket getClient() {
        return client;
    }

    public String getAccount() {
        return account;
    }

    public String getPassword() {
        return password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "id:" + id + "account:" + account + " password:" + password;
    }
}
