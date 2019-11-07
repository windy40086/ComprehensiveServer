package client_test;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TalkClient {
	public static void main(String args[]) {
		JFrame jf = new JFrame();//用JFrame创建一个名为frame的顶级容器
		jf.setContentPane(new JPanel());//在jf里面添加一个JPanel对象，用来增加滚动条
		jf.setTitle("JAVA聊天器");
		jf.setResizable(true);//窗口可由用户调整大小
		jf.setSize(540, 600);//大小
		jf.setLocation(650, 100);//位置
		jf.setLayout(null);//布局方式
		JButton btnSend = new JButton("发送");//创建一个按钮
		btnSend.setForeground(Color.red); //盖住背景色
		final JTextArea xie = new JTextArea();//创建文本区域,写
		final JTextArea du = new JTextArea();//读
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//右键关闭窗口时操作
		jf.getContentPane().setBackground(Color.yellow);
		jf.setVisible(true);//使图形可见
		btnSend.setBounds(410, 320, 100, 50);//位置，大小的设置
		Font font = new Font("等线", Font.PLAIN, 20);//字体，20的字号
		btnSend.setFont(font);
		jf.add(btnSend);//添加到顶级容器
		xie.setBounds(10, 380, 500, 150);
		xie.setFont(font);
		jf.add(xie);
		du.setEditable(false);//该控件不能被编辑，即显示区无法选择
		//taChat.setBounds(10,10,500,300);
		du.setFont(font);
		//jf.add(taChat);
		JScrollPane js = new JScrollPane(du);//把du封装到JScrollPane里面
		js.setBounds(10, 10, 500, 300);
		jf.getContentPane().add(js);//添加到jf里，即添加滚动条
		try {
			//向本机的4700端口发出客户请求
			@SuppressWarnings("resource") final Socket socket = new Socket("127.0.0.1", 10443);
			DataInputStream is = new DataInputStream(socket.getInputStream());//消息，文件输入流
			du.append("发消息：1 hello   发文件： 1 hello.txt   退出：1 bye" + "\n");
			System.out.println("发消息：1 hello   发文件： 1 hello.txt   退出：1 bye" + "\n");

			btnSend.addActionListener(//监听“发送”是否被点击
					new ActionListener() {
						public void actionPerformed(ActionEvent event) {
							String readline = xie.getText();//从输入框读取
							xie.setText("");//清空输入区域
							try {
								char pd1 = readline.charAt(0);
								char pd2 = readline.charAt(1);
								if (pd1 <= '9' && pd1 >= '0' && pd2 == ' ')//判断输入格式是否正确
								{
									DataOutputStream os = new DataOutputStream(socket.getOutputStream());//消息，文件输出流
									String[] talk = readline.split(" ");
									String[] wj = talk[1].split("\\.");//分割字符串，判断是否要发送文件
									boolean cz = false;//标志该文件是不是存在
									if (wj.length == 2)//发文件的话，长度等于2
									{
										File directory = new File("");// 参数为空
										String courseFile = directory.getCanonicalPath();//获得项目所在路径，也就是发文件读取文件的路径
										File file = new File(courseFile);
										File filelist[] = file.listFiles();
										for (File f : filelist) {
											String filename = f.getName();
											if (filename.equals(talk[1])) {
												cz = true;//要是存在就真
											}
										}
									}//例如hello.abc这是不存在的，就把他当作消息发过去，所以要判断该文件是否存在
									if (cz)//如果要发送文件
									{
										os.writeUTF("9q8w7e6a5s4d");//奇怪的字符串，用来标志是不是发文件
										os.flush();
										os.writeUTF(readline);
										os.flush();//刷新输出流，使Server马上收到该字符串
										File fi = new File(talk[1]);
										FileInputStream fis = new FileInputStream(fi);//读文件对象，该文件就在根目录下
										int m, len;
										long l = fi.length(); //获取文件长度，单位kb
										if ((l % 512) != 0) len = m = 512;
										else len = m = 511;//防止文件正好是512字节的倍数
										byte buffer[] = new byte[m];//读取输入流 ，一次读取512字节
										//while ((len=fis.read(buffer,0,m))!=-1) {
										while (len >= m) {//这while不能用上面注释的那个，read会阻塞
											len = fis.read(buffer, 0, m);
											os.write(buffer, 0, len);
											os.flush();
										}
										fis.close();
										du.append("文件读取发送成功！" + "\n");
										System.out.println("文件读取发送成功！");
									} else {
										os.writeUTF(readline);//正常发消息
										os.flush();//刷新输出流，使Server马上收到该字符串
										if (talk[1].equals("bye"))//若从标准输入读入的字符串为 "bye"则停止循环
										{
											System.exit(0);//退出系统
										}
									}
								} else if (readline.equals("bye")) {
									System.exit(0);//退出系统
								} else du.append("输入格式有误！" + "\n");
							} catch (Exception e) {

							}
						}
					}
			);

			while (true) {//无限次收消息
				if (is.available() > 0)//如果先收消息，也是先available()查看输入流的长度（未读取的情况下）
				{
					String get = is.readUTF();
					if (get.equals("9q8w7e6a5s4d"))//判断是否要收文件
					{
						get = is.readUTF();//读取文件名等
						du.append(get + "\n");
						System.out.println(get);
						String[] wj1 = get.split(" ");
						String name = "E:\\" + wj1[2];//文件名为原文件名，自己指定目录
						File file = new File(name);
						if (!file.exists()) {
							file.createNewFile();
							du.append("文件已新建！" + "\n");
							System.out.println("文件已新建！");
						}

						FileOutputStream fos = new FileOutputStream(name);//文件写入对象
						int n = 512, len = 512;
						byte[] data = new byte[n];//一次读取字节,一般不会太大
						while (len >= n)//判断是否有可以读取的数据
						{
							len = is.read(data, 0, n);
							//一定要用DataInputStream，他有方法可以判断缓冲区是否有内容，这样就不会在没有内容时用read()阻塞了
							fos.write(data, 0, len);
							fos.flush();
						}
						du.append("文件已上传成功：" + name + "\n");
						System.out.println("文件已上传成功：" + name + "\n");
						fos.close();
					} else {
						du.append(get + "\n");
						System.out.println(get);
					}

				}
			} //继续循环
		} catch (Exception e) {
			System.out.println("Error" + e); //出错，则打印出错信息
		}
		du.append("您已退出系统！" + "\n");
		System.out.println("您已退出系统！");
	}
}