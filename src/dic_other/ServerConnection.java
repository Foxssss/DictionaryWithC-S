package dic_other;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerConnection {
	private static Socket socket;
	private static Socket share_socket;
	static int port = 9999;
	 
	 public static void init(){
		 try {
			socket = new Socket("114.212.134.203",port);
			share_socket = new Socket("114.212.134.203",8000);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	 }
	public static String sendMessage(String message) {
		  try {		
			  BufferedReader in;
			  PrintWriter out;
			  while(true){
			  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			  out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
//			  InputStreamReader str = new InputStreamReader(System.in);
//			  char[] s = new char[100];
//			  str.read(s);
			  out.println(message);//传输数据给服务器端
			  
			  String l=in.readLine();
			  l = l.replace('$', '\n');
			  return l;
			  }
		  } catch (Exception e) {
			  System.out.println(e);
			  return null;
		  }
		 }
}