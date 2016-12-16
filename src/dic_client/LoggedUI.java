package dic_client;

import dic_other.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class LoggedUI extends JFrame{
	public String userName;
	private JLabel jlblUsericon = new JLabel();
	private JLabel jlblUserol = new JLabel("在线用户：");
	private String[] userList;
	private JList jlUserlist = new JList(userList);
	private JScrollPane jspUserlist = new JScrollPane(jlUserlist);
	private JButton jbtShare = new JButton("分享单词卡");
	private JButton jbtAdjustpw = new JButton("修改密码");
	private JButton jbtLogout = new JButton("退出登陆");
	private AdjustPassword apWnd = new AdjustPassword();
	
	public boolean logout = false;
	public JButton jbtAccount;
	
	public LoggedUI() {
		//设置部件属性
		jlUserlist.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblUsericon.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblUserol.setFont(new Font("Serif", Font.PLAIN, 18));
		jbtShare.setFont(new Font("Monospaced", Font.PLAIN, 14));
		jbtShare.setPreferredSize(new Dimension(10, 10));
		jbtAdjustpw.setFont(new Font("Monospaced", Font.PLAIN, 14));
		jbtLogout.setFont(new Font("Monospaced", Font.PLAIN, 14));
		jlblUsericon.setIcon(new ImageIcon(getClass().getResource("logged3.png")));
		jlblUsericon.setFont(new Font("Serif", Font.PLAIN, 16));
		//设置事件响应
		jbtShare.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				String selUser = (String)jlUserlist.getSelectedValue();
				if(selUser == null) {
					JLabel jlblShare_tmp = new JLabel("请选择用户");
					jlblShare_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblShare_tmp, "无法分享", JOptionPane.ERROR_MESSAGE);
					return;
				}
				boolean doShareJob = false;
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
				jbtAccount.setIcon(new ImageIcon(getClass().getResource("account2.png")));
				logout = true;
				String logoutRequest = "104#" + userName;
				ServerConnection.sendMessage(logoutRequest);
				setVisible(false);
			}
		});
		
		//设置布局
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
		
		//设置基本属性
		setTitle("在线词典");
		setSize(300, 590);
		//setLocationRelativeTo(null);
		setLocation(1040, 70);
		setResizable(false);
		//pack();
		try {
			Image imgacnt = ImageIO.read(getClass().getResource("account1.png"));
			setIconImage(imgacnt);
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		setVisible(false);		
	}
	public void setUserlist(String[] ulist) {
		userList =ulist;
		jlUserlist.setListData(userList);
	}
	public void setName(String name) {
		userName = name;
		jlblUsericon.setText(name);
	}
	public void clearInput() {
		userList = null;
	}
}
