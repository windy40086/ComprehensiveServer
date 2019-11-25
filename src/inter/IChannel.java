package inter;

import entity.User;

import java.net.Socket;

public interface IChannel extends Runnable, IType{

    String receive();

    boolean send(String message);

    Socket getClient();

    boolean sendObject(Object obj);

    User getUser();

    String getUserAccount();

    String getUserId();
}
