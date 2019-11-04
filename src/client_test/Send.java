package client_test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class Send implements Runnable{
	//输入流
	private BufferedReader console;
	//输出流
	private DataOutputStream dos;
	
	private boolean isRunnable = true;
	
	private Send(){
		console = new BufferedReader(new InputStreamReader(System.in));
	}
	
	Send(Socket client){
		this();
		try {
			dos = new DataOutputStream(client.getOutputStream());
		} catch (Exception e) {
			isRunnable = false;
		}
	}
	
	//控制台获取发送 字符串
	private String getMsg() {
		try {
			return console.readLine();
		} catch (IOException e) {
			isRunnable = false;
		}
		return "";
	}
	
	//发送信息
	private void send(){
		String msg = getMsg();
		try {
			if(null!=msg && !msg.equals("")){
				dos.writeUTF(msg);
			}
		} catch (Exception e) {
			isRunnable = false;
		}
	}
	
	//循环线程
	@Override
	public void run() {
		while(isRunnable){
			send();
		}
	}
}
