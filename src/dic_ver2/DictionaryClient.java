package dic_ver2;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class DictionaryClient extends JFrame{
	private boolean logged = false;
	
	private JButton jbtSearch = new JButton();
	private JButton jbtAccount = new JButton();
	private JTextField jtfInput = new JTextField();
	private JCheckBox jchkBaidu = new JCheckBox("百度", false);
	private JCheckBox jchkYoudao = new JCheckBox("有道", false);
	private JCheckBox jchkBiying = new JCheckBox("必应", false);
	private JTextArea[] jtaMeaning = new JTextArea[3];
	private String[] tranName = {"百度翻译", "有道翻译", "必应翻译"};
	private String[] meaning = {"","",""};
	private int[] tranRank = {1, 2, 3};
	private int selectedNum = 2;
	private Login logWnd = new Login();
	
	public static void main(String[] args) {
		DictionaryClient mydic = new DictionaryClient();
		mydic.setTitle("DictionaryOL");
		mydic.setSize(800,600);
		mydic.setLocationRelativeTo(null);
		mydic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mydic.setVisible(true);
	}
	
	public DictionaryClient() {
		//设置部件属性
		UIManager.put("TitledBorder.font", new Font("Serif", Font.PLAIN, 18));
		UIManager.put("TabbedPane.font", new Font("Serif", Font.PLAIN, 18));
		jtfInput.setFont(new Font("Serif", Font.PLAIN, 18));
		jchkBaidu.setFont(new Font("Serif", Font.PLAIN, 18));
		jchkYoudao.setFont(new Font("Serif", Font.PLAIN, 18));
		jchkBiying.setFont(new Font("Serif", Font.PLAIN, 18));
		jbtSearch.setIcon(new ImageIcon(getClass().getResource("search2.png")));
		jbtAccount.setToolTipText("登录/注册");
		jbtAccount.setIcon(new ImageIcon(getClass().getResource("account1.png")));
		for(int i = 0; i < 3; ++i) {
			jtaMeaning[i] = new JTextArea();
			jtaMeaning[i].setEditable(false);
			jtaMeaning[i].setFont(new Font("Serif", Font.PLAIN, 18));
			jtaMeaning[i].setWrapStyleWord(true);
			jtaMeaning[i].setLineWrap(true);
			jtaMeaning[i].setVisible(false);
		}
		
		//设置事件响应
		jbtSearch.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				String word = jtfInput.getText();
				showTran();
			}
		});
		jbtAccount.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				//JFrame jfAccount = new JFrame();
				if(!logged) {
					logWnd.setVisible(true);
				}
			}
		});
		
		//设置布局
		//input, search, account
		JPanel jpSearch = new JPanel();
		jpSearch.setLayout(new BorderLayout(0, 0));
		jpSearch.add(jtfInput, BorderLayout.CENTER);
		jpSearch.add(jbtSearch, BorderLayout.EAST);
		JPanel jpInput = new JPanel();
		jpInput.setLayout(new BorderLayout(5, 0));
		jpInput.add(jpSearch, BorderLayout.CENTER);
		jpInput.add(jbtAccount, BorderLayout.WEST);
		//checkbox
		JPanel jpBaidu = new JPanel();
		JPanel jpYoudao = new JPanel();
		JPanel jpBiying = new JPanel();
		LikeButton likeBd = new LikeButton();
		LikeButton likeYd = new LikeButton();
		LikeButton likeBy = new LikeButton();
		jpBaidu.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jpBaidu.add(jchkBaidu);
		jpBaidu.add(likeBd);
		jpYoudao.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jpYoudao.add(jchkYoudao);
		jpYoudao.add(likeYd);
		jpBiying.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jpBiying.add(jchkBiying);
		jpBiying.add(likeBy);
		JPanel jpChoose = new JPanel();
		jpChoose.setLayout(new GridLayout(1, 3, 5, 5));
		jpChoose.add(jpBaidu);
		jpChoose.add(jpYoudao);
		jpChoose.add(jpBiying);
		//north part
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(new GridLayout(2, 1, 5, 5));
		jpNorth.add(jpInput);
		jpNorth.add(jpChoose);
				
		//center part
		JPanel jpCenter = new JPanel();
		jpCenter.setLayout(new GridLayout(3, 1, 5, 5));
		for(int i = 0; i < 3; ++i) {
			jpCenter.add(jtaMeaning[i]);
		}
		
		setLayout(new BorderLayout(5, 5));
		add(jpNorth, BorderLayout.NORTH);
		add(jpCenter, BorderLayout.CENTER);
	}
	public void showTran() {
		for(int i = 0;i < selectedNum; ++i) {
			int index = 0;
			while(tranRank[index] != i+1)
				index++;
			jtaMeaning[i].setBorder(new TitledBorder(tranName[index]));
			jtaMeaning[i].setText(meaning[index]);
			jtaMeaning[i].setVisible(true);
		}
	}
	
	static class LikeButton extends JButton {
		private ImageIcon img_like = new ImageIcon(getClass().getResource("Like2.png"));
		private ImageIcon img_liked = new ImageIcon(getClass().getResource("Liked2.png"));
		private boolean liked;
		public LikeButton() {
			liked = false;
			setIcon(img_like);
			setToolTipText("点赞");
			setPressedIcon(new ImageIcon(getClass().getResource("Liking2.png")));
			//setPressedIcon(img_liked);
			addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if(liked == false) {
						liked = true;
						setIcon(img_liked);
					}
					else {
						liked = false;
						setIcon(img_like);
					}
				}
			});
		}
	}
}
/*
try { 
	//UIManager.getSystemLookAndFeelClassName() 
	//返回实现默认的跨平台外观 -- Java Look and Feel (JLF) -- 的 LookAndFeel 类的名称。 
	//下面语句实现：将外观设置为系统外观. 
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
	} catch (ClassNotFoundException ex) { 
	Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, 
	null, ex); 
	} catch (InstantiationException ex) { 
	Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, 
	null, ex); 
	} catch (IllegalAccessException ex) { 
	Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, 
	null, ex); 
	} catch (UnsupportedLookAndFeelException ex) { 
	Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, 
	null, ex); 
	} */