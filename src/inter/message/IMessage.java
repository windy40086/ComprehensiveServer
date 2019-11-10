package inter.message;

import control.Channel;

public interface IMessage {
    public String getSendMessage();
    public boolean isMsgCorrect();
    public String toString();
    public void send(Channel this_ch);
}
