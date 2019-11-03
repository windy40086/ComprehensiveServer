package client_test;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import entity.Message;

@SuppressWarnings("unused")
class Recieve implements Runnable {
	// 输出流
	private BufferedWriter console;
	// 输入流
	private DataInputStream dis;
	
	private boolean isRunnable = true;

	public Recieve() {
		console = new BufferedWriter(new OutputStreamWriter(System.out));
	}

	public Recieve(Socket client) {
		this();
		try {
			dis = new DataInputStream(client.getInputStream());
		} catch (Exception e) {
			isRunnable = false;
		}
	}

	// 接受信息
	public String getMsg() {
		String msg = "";
		try {
			msg = dis.readUTF();
			return msg;
		} catch (IOException e) {
			isRunnable = false;
		}
		return "";
	}
	
	//发送信息
	public void recieve(){
		String msg = getMsg();
		try {
			if(null!=msg && !msg.equals("")){
				System.out.println(msg);
			}
		} catch (Exception e) {
			isRunnable = false;
		}
	}

	// 循环线程
	@Override
	public void run() {
		while (isRunnable) {
			recieve();
		}
	}
}