package dic_ver2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerConnection {
	public static String sendMessage(String message) {
		  try {
			  int port = 9999;
			  BufferedReader in;
			  PrintWriter out;
			  
			  Socket socket = new Socket("114.212.132.185",port);
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