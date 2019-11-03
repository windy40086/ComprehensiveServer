package client_test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {

		ServerSocket server = null;
		try {
			server = new ServerSocket(8888);
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}

		//每个socket都是一个客户端
		while(true){
			/*
			 * 阻塞并等待客户端发送请求
			 * 
			 * 此处等待的是TCP协议的请求
			 * 在浏览器中可以输入127.0.0.1:port 进行请求
			 */
			try {
				Socket socket = server.accept();
				System.out.println("建立连接");
				
				
				new Thread(new Send(socket)).start();
				new Thread(new Recieve(socket)).start();

			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
	
}


