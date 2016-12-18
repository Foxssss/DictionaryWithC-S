package dic_client;

import dic_other.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

public class LoggedUI extends JFrame {
	private String userName;
	private Component mainframe;
	private JLabel jlblUsericon = new JLabel();
	private JLabel jlblUserol = new JLabel("�����û���");
	private String[] userList;
	private JList jlUserlist = new JList(userList);
	private JScrollPane jspUserlist = new JScrollPane(jlUserlist);
	private JButton jbtShare = new JButton("�����ʿ�");
	private JButton jbtAdjustpw = new JButton("�޸�����");
	private JButton jbtLogout = new JButton("�˳���½");
	private AdjustPassword apWnd = new AdjustPassword();

	public boolean logout = false;
	public JButton jbtAccount;

	public LoggedUI() {
		// ���ò�������
		jlUserlist.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblUsericon.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblUserol.setFont(new Font("Serif", Font.PLAIN, 18));
		jbtShare.setFont(new Font("Monospaced", Font.PLAIN, 14));
		jbtShare.setPreferredSize(new Dimension(10, 10));
		jbtAdjustpw.setFont(new Font("Monospaced", Font.PLAIN, 14));
		jbtLogout.setFont(new Font("Monospaced", Font.PLAIN, 14));
		jlblUsericon.setIcon(new ImageIcon(getClass()
				.getResource("logged3.png")));
		jlblUsericon.setFont(new Font("Serif", Font.PLAIN, 16));
		// �����¼���Ӧ
		jbtShare.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				String selUser = (String) jlUserlist.getSelectedValue();
				if (selUser == null) {
					JLabel jlblShare_tmp = new JLabel("��ѡ���û�");
					jlblShare_tmp
							.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblShare_tmp, "�޷�����",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				boolean doShareJob = false;
				ScreenShot test = new ScreenShot();
				test.screenShotAsFile(mainframe.getX(), mainframe.getY(),
						mainframe.getWidth(), mainframe.getHeight(), "G:/",
						userName + "to" + selUser, "jpg");
				ServerConnection.sendMessage("401#" + userName + "#" + selUser);
				//DataOutputStream dos = null;
				//FileInputStream fis = null;
				try {
					Thread.sleep(1000);
					Socket sk = new Socket(ServerConnection.getIP(), 9900);
					DataOutputStream out = new DataOutputStream(sk.getOutputStream());
					FileInputStream in = new FileInputStream(new File("G:/" + userName + "to" + selUser + ".jpg"));
					byte[] bytes = new byte[1024];
					int length = 0;
					while((length=in.read(bytes, 0, bytes.length))>0){
						out.write(bytes,0,length);
						out.flush();
					}
					in.close();
					Thread.sleep(1000);
					sk.close();
				} catch (FileNotFoundException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		jbtAdjustpw.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				apWnd.setName(userName);
				apWnd.clearInput();
				apWnd.setVisible(true);
			}
		});
		jbtLogout.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				jbtAccount.setIcon(new ImageIcon(getClass().getResource(
						"account2.png")));
				logout = true;
				String logoutRequest = "104#" + userName;
				ServerConnection.sendMessage(logoutRequest);
				setVisible(false);
			}
		});

		// ���ò���
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(new GridLayout(2, 1, 8, 8));
		jpNorth.add(jlblUsericon);
		jpNorth.add(jlblUserol);
		JPanel jpButtona = new JPanel();
		jpButtona.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		jpButtona.add(jbtAdjustpw);
		jpButtona.add(jbtLogout);
		JPanel jpButtons = new JPanel();
		jpButtons.setLayout(new GridLayout(2, 1, 5, 5));
		jpButtons.add(jbtShare);
		jpButtons.add(jpButtona);
		JPanel jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout(8, 8));
		jpMain.add(jpNorth, BorderLayout.NORTH);
		jpMain.add(jspUserlist, BorderLayout.CENTER);
		jpMain.add(jpButtons, BorderLayout.SOUTH);
		jpMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(jpMain);

		// ���û�������
		setTitle("���ߴʵ�");
		setSize(300, 590);
		// setLocationRelativeTo(null);
		setLocation(1040, 70);
		setResizable(false);
		// pack();
		try {
			Image imgacnt = ImageIO
					.read(getClass().getResource("account1.png"));
			setIconImage(imgacnt);
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
		}
		setVisible(false);
	}

	public void setUserlist(String[] ulist) {
		userList = ulist;
		jlUserlist.setListData(userList);
	}

	public void setName(String name) {
		userName = name;
		jlblUsericon.setText(name);
	}

	public void setMainframe(Component frame) {
		mainframe = frame;
	}

	public void clearInput() {
		userList = null;
	}
}
