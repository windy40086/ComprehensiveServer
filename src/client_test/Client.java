package client_test;

import java.net.Socket;

public class Client {
	public static void main(String[] args) throws Exception {

		Socket client = new Socket("127.0.0.1",10443);
//		Socket client = new Socket("192.168.0.102",10443);

		
		new Thread(new Send(client)).start();
		new Thread(new Recieve(client)).start();

	}
}
