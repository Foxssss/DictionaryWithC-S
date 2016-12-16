package dic_client;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import dic_other.ServerConnection;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Pattern;

public class DictionaryClient extends JFrame{	
	private JButton jbtSearch = new JButton();
	private JButton jbtAccount = new JButton();
	private JTextField jtfInput = new JTextField();
	private JCheckBox jchkJinshan = new JCheckBox("金山", false);
	private JCheckBox jchkYoudao = new JCheckBox("有道", false);
	private JCheckBox jchkBiying = new JCheckBox("必应", false);
	private JTextArea[] jtaMeaning = new JTextArea[3];
	private JScrollPane[] jspMeaning = new JScrollPane[3];
	private JLabel[] jlblIcon = new JLabel[3];
	private ImageIcon[] icons = new ImageIcon[3];
	private LikeButton likeJs = new LikeButton(1);
	private LikeButton likeYd = new LikeButton(3);
	private LikeButton likeBy = new LikeButton(5);
	//private String[] tranName = {"金山翻译", "有道翻译", "必应翻译"};
	//private String[] meaning = {"","",""};
	//private int[] likedNum = {0, 0, 0};
	private Translation[] translation = new Translation[3];
	private int selectedNum = 0;
	private int[] tranRank = {0, 1, 2};
	private Login logWnd = new Login();
	private LoggedUI loggedWnd = new LoggedUI();
	private Component myframe = this;
	
	public static void main(String[] args) {
		DictionaryClient mydic = new DictionaryClient();
		mydic.setTitle("DictionaryOL");
		mydic.setSize(800,600);
		//mydic.setLocationRelativeTo(null);
		mydic.setLocation(250, 70);
		mydic.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mydic.setVisible(true);
		mydic.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//ServerConnection.init();
	}
	
	public DictionaryClient() {
		//设置部件属性
		UIManager.put("TitledBorder.font", new Font("Serif", Font.PLAIN, 18));
		UIManager.put("TabbedPane.font", new Font("Serif", Font.PLAIN, 18));
		logWnd.jbtAccount = jbtAccount;
		loggedWnd.jbtAccount = jbtAccount;
		jtfInput.setFont(new Font("Serif", Font.PLAIN, 18));
		jchkJinshan.setFont(new Font("Serif", Font.PLAIN, 18));
		jchkYoudao.setFont(new Font("Serif", Font.PLAIN, 18));
		jchkBiying.setFont(new Font("Serif", Font.PLAIN, 18));
		jbtSearch.setIcon(new ImageIcon(getClass().getResource("search2.png")));
		jbtAccount.setToolTipText("登录/注册");
		jbtAccount.setIcon(new ImageIcon(getClass().getResource("account2.png")));
		for(int i = 0; i < 3; ++i) {
			jlblIcon[i] = new JLabel("", JLabel.CENTER);
			jlblIcon[i].setPreferredSize(new Dimension(100, 100));
			jlblIcon[i].setFont(new Font("Serif", Font.PLAIN, 16));
			jlblIcon[i].setForeground(new Color(238, 64, 0));
			jlblIcon[i].setVerticalTextPosition(JLabel.BOTTOM);
			jlblIcon[i].setHorizontalTextPosition(JLabel.CENTER);
			jtaMeaning[i] = new JTextArea();
			jtaMeaning[i].setEditable(false);
			jtaMeaning[i].setFont(new Font("Serif", Font.PLAIN, 18));
			jtaMeaning[i].setWrapStyleWord(true);
			jtaMeaning[i].setLineWrap(true);
			jtaMeaning[i].setBorder(new LineBorder(Color.gray, 1));
			jtaMeaning[i].setMargin(new Insets(10, 10, 10, 10));
			jtaMeaning[i].setVisible(false);
			jspMeaning[i] = new JScrollPane(jtaMeaning[i]);
			jspMeaning[i].setVisible(false);
		}
		translation[0] = new Translation("金山翻译");
		translation[1] = new Translation("有道翻译");
		translation[2] = new Translation("必应翻译");
		icons[0] = new ImageIcon(getClass().getResource("jinshan2.png"));
		icons[1] = new ImageIcon(getClass().getResource("youdao.png"));
		icons[2] = new ImageIcon(getClass().getResource("Bing.png"));
		
		//设置事件响应
		jbtSearch.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				String input = jtfInput.getText();
				if(input.equals(""))
					return;
				int beginindex = 0;
				while(beginindex < input.length() && input.charAt(beginindex) == ' ')
					beginindex ++;
				input = input.substring(beginindex);
				Pattern pSpace = Pattern.compile("\\s+");
				String[] splitLine = pSpace.split(input);
				StringBuilder word = new StringBuilder();
				int i;
				for(i = 0; i < splitLine.length; ++i) {
					if(isEnglishWord(splitLine[i]))
						word.append(splitLine[i] + " ");
					else
						break;
				}
				if(word.toString().equals(""))
					return;
				int []sel = new int[3];
				for(int j = 0; j < 3; ++j)
					sel[j] = translation[j].selected?1:0;
				String searchRequest;
				if(!logWnd.logged || loggedWnd.logout)
					searchRequest = "201#" + sel[0] + sel[1] + sel[2] + "#" + word.substring(0, word.length() - 1);
				else
					searchRequest = "203#" + sel[0] + sel[1] + sel[2] + "#" + word.substring(0, word.length() - 1) + "#" + logWnd.getName();
				String result = ServerConnection.sendMessage(searchRequest);
				String[] respieces = result.split("[#]");
				for(int j = 0; j < 3; ++j) {
					translation[j].meaning = respieces[2*j+1];
					translation[j].likedNum = Integer.parseInt(respieces[2*j+2]);
					if(translation[j].meaning.equals("@ERROR"))
						translation[j].meaning = "找不到该单词";
				}
				if(logWnd.logged && !loggedWnd.logout) {
					if(respieces[7].charAt(0) == '1')
						likeJs.setLiked();
					else
						likeJs.cancelLiked();
					if(respieces[7].charAt(1) == '1')
						likeYd.setLiked();
					else
						likeYd.cancelLiked();
					if(respieces[7].charAt(2) == '1')
						likeBy.setLiked();
					else
						likeBy.cancelLiked();
				}
				jtfInput.setText(word.substring(0, word.length() - 1));
				showTran();
			}
		});
		jbtAccount.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(loggedWnd.logout) {
					logWnd.logged = false;
					loggedWnd.logout = false;
				}
				if(!logWnd.logged) {
					logWnd.clearInput();
					logWnd.setVisible(true);
				}
				else {
					String getolRequest = "501#" + logWnd.getName();
					//String message = ServerConnection.sendMessage(getolRequest);
					String message = "502#aba#ddd";
					String[] resultol = message.split("#");
					String[] resol = new String[resultol.length - 1];
					for(int i = 0; i < resol.length; ++i)
						resol[i] = resultol[i+1];
					loggedWnd.setUserlist(resol);
					loggedWnd.clearInput();
					loggedWnd.setName(logWnd.getName());
					loggedWnd.setMainframe(myframe);
					loggedWnd.setVisible(true);
				}
			}
		});
		jchkJinshan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jchkJinshan.isSelected()) {
					selectedNum ++;
					translation[0].selected = true;
				}
				else {
					selectedNum--;
					translation[0].selected = false;
				}
			}
		});
		jchkYoudao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jchkYoudao.isSelected()) {
					selectedNum ++;
					translation[1].selected = true;
				}
				else {
					selectedNum--;
					translation[1].selected = false;
				}
			}
		});
		jchkBiying.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(jchkBiying.isSelected()) {
					selectedNum ++;
					translation[2].selected = true;
				}
				else {
					selectedNum--;
					translation[2].selected = false;
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
		JPanel jpJinshan = new JPanel();
		JPanel jpYoudao = new JPanel();
		JPanel jpBiying = new JPanel();
		jpJinshan.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jpJinshan.add(jchkJinshan);
		jpJinshan.add(likeJs);
		jpYoudao.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jpYoudao.add(jchkYoudao);
		jpYoudao.add(likeYd);
		jpBiying.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		jpBiying.add(jchkBiying);
		jpBiying.add(likeBy);
		JPanel jpChoose = new JPanel();
		jpChoose.setLayout(new GridLayout(1, 3, 5, 5));
		jpChoose.add(jpJinshan);
		jpChoose.add(jpYoudao);
		jpChoose.add(jpBiying);
		//north part
		JPanel jpNorth = new JPanel();
		jpNorth.setLayout(new GridLayout(2, 1, 5, 5));
		jpNorth.add(jpInput);
		jpNorth.add(jpChoose);
				
		//center part
		JPanel jpCenter = new JPanel();
		JPanel[] jpCenparts = new JPanel[3];
		jpCenter.setLayout(new GridLayout(3, 1, 5, 5));
		for(int i = 0; i < 3; ++i) {
			jpCenparts[i] = new JPanel();
			jpCenparts[i].setLayout(new BorderLayout(5, 5));
			jpCenparts[i].add(jlblIcon[i], BorderLayout.WEST);
			jpCenparts[i].add(jspMeaning[i], BorderLayout.CENTER);
			jpCenter.add(jpCenparts[i]);
		}
		
		JPanel jpMain = new JPanel();  //核心部分
		JPanel jpBlank1 = new JPanel(); //四周空白
		JPanel jpBlank2 = new JPanel();
		JPanel jpBlank3 = new JPanel();
		JPanel jpBlank4 = new JPanel();
		jpMain.setLayout(new BorderLayout(5, 5));
		jpMain.add(jpNorth, BorderLayout.NORTH);
		jpMain.add(jpCenter, BorderLayout.CENTER);
		
		setLayout(new BorderLayout(5, 5));
		add(jpMain, BorderLayout.CENTER);
		add(jpBlank1, BorderLayout.NORTH);
		add(jpBlank2, BorderLayout.SOUTH);
		add(jpBlank3, BorderLayout.WEST);
		add(jpBlank4, BorderLayout.EAST);
	}
	// 在界面中按排序显示翻译
	private void showTran() {
		for(int i = 0; i < 3; ++i) {
			jspMeaning[i].setVisible(false);
			jtaMeaning[i].setVisible(false);
			jlblIcon[i].setVisible(false);
		}
		boolean select_none = false;
		if(selectedNum == 0) {
			select_none = true;
			selectedNum = 3;
			translation[0].selected = true;
			translation[1].selected = true;
			translation[2].selected = true;
		}
		int max = -1;
		for(int i = 0; i < 3; ++i)
			if(max < translation[i].likedNum) {
				max = translation[i].likedNum;
				tranRank[0] = i;
			}
		max = -1;
		for(int i = 0; i < 3; ++i) {
			if(i != tranRank[0] && max < translation[i].likedNum) {
				max = translation[i].likedNum;
				tranRank[1] = i;
			}
		}
		tranRank[2] = 3 - tranRank[0] - tranRank[1];
		int index = 0;
		for(int i = 0;i < selectedNum; ++i, ++index) {
			while(!translation[tranRank[index]].selected)
				index++;
			//jtaMeaning[i].setBorder(new TitledBorder(translation[tranRank[index]].name));
			jtaMeaning[i].setText(translation[tranRank[index]].meaning);
			jlblIcon[i].setIcon(icons[tranRank[index]]);
			jlblIcon[i].setToolTipText(translation[tranRank[index]].name);
			String intro = translation[tranRank[index]].likedNum + " 赞";
			jlblIcon[i].setText(intro);
			jspMeaning[i].setVisible(true);
			jtaMeaning[i].setVisible(true);
			jlblIcon[i].setVisible(true);
		}
		if(select_none) {
			selectedNum = 0;
			translation[0].selected = false;
			translation[1].selected = false;
			translation[2].selected = false;
		}
	}
	private boolean isEnglishWord(String str) {
		Pattern p = Pattern.compile("[a-zA-Z][a-zA-Z.'/-]*");
		return p.matcher(str).matches();
	}
	
	class LikeButton extends JButton {
		private ImageIcon img_like = new ImageIcon(getClass().getResource("Like2.png"));
		private ImageIcon img_liked = new ImageIcon(getClass().getResource("Liked2.png"));
		private boolean liked;
		int type;
		public LikeButton(int type) {
			this.type = type;
			liked = false;
			setIcon(img_like);
			setToolTipText("点赞");
			//setPressedIcon(img_liked);
			addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					if(!logWnd.logged || loggedWnd.logout) {
						JLabel jlblLogfail_tmp = new JLabel("请先登录");
						jlblLogfail_tmp.setFont(new Font("Monospaced", Font.BOLD, 15));
						JOptionPane.showMessageDialog(null, jlblLogfail_tmp, "点赞失败", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					if(liked == false) {
						liked = true;
						int sign = 300 + type;
						String likedRequest = sign + "#" + logWnd.getName() + "#" + jtfInput.getText();
						ServerConnection.sendMessage(likedRequest);
						setIcon(img_liked);
						translation[type / 2].likedNum++;
						showTran();
					}
					else {
						int sign = 301 + type;
						String likedRequest = sign + "#" + logWnd.getName() + "#" + jtfInput.getText();
						ServerConnection.sendMessage(likedRequest);
						liked = false;
						setIcon(img_like);
						translation[type / 2].likedNum--;
						showTran();
					}
				}
			});
		}
		public void setLiked() {
			liked = true;
			setIcon(img_liked);
		}
		public void cancelLiked() {
			liked = false;
			setIcon(img_like);
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