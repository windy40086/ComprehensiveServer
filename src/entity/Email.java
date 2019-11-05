package entity;

public class Email {
    private String receiver;
    private String account;
    private String title;
    private String content;
    private boolean debug;

    public Email() {
    }

    public Email(String receiver, String account, String title, String content, boolean debug) {
        this.receiver = receiver;
        this.account = account;
        this.title = title;
        this.content = content;
        this.debug = debug;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
