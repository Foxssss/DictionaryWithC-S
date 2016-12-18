package dic_other;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerConnection {
	private static Socket socket;
	private static Socket share_socket;
	static int port = 9999;
	static String ipaddress = "192.168.0.11";

	public static void init() {
		try {
			socket = new Socket(ipaddress, port);
			share_socket = new Socket(ipaddress, 8000);
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	public static String sendMessage(String message) {
		try {
			BufferedReader in;
			PrintWriter out;
			while (true) {
				in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				out = new PrintWriter(new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true);
				// InputStreamReader str = new InputStreamReader(System.in);
				// char[] s = new char[100];
				// str.read(s);
				out.println(message);// �������ݸ���������

				String l = in.readLine();
				l = l.replace('$', '\n');
				return l;
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	public static Socket getShared() {
		return share_socket;
	}
	public static Socket get(){
		return socket;
	}
	public static String getIP(){
		return ipaddress;
	}
}