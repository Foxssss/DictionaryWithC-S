package dic_ver2;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Pattern;

public class DictionaryClient extends JFrame{
	private boolean logged = false;
	
	private JButton jbtSearch = new JButton();
	private JButton jbtAccount = new JButton();
	private JTextField jtfInput = new JTextField();
	private JCheckBox jchkJinshan = new JCheckBox("金山", false);
	private JCheckBox jchkYoudao = new JCheckBox("有道", false);
	private JCheckBox jchkBiying = new JCheckBox("必应", false);
	private JTextArea[] jtaMeaning = new JTextArea[3];
	//private String[] tranName = {"金山翻译", "有道翻译", "必应翻译"};
	//private String[] meaning = {"","",""};
	//private int[] likedNum = {0, 0, 0};
	private Translation[] translation = new Translation[3];
	private int selectedNum = 0;
	private int[] tranRank = {0, 1, 2};
	private Login logWnd = new Login();
	
	public static void main(String[] args) {
		DictionaryClient mydic = new DictionaryClient();
		mydic.setTitle("DictionaryOL");
		mydic.setSize(800,600);
		mydic.setLocationRelativeTo(null);
		mydic.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mydic.setVisible(true);
		mydic.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public DictionaryClient() {
		//设置部件属性
		UIManager.put("TitledBorder.font", new Font("Serif", Font.PLAIN, 18));
		UIManager.put("TabbedPane.font", new Font("Serif", Font.PLAIN, 18));
		jtfInput.setFont(new Font("Serif", Font.PLAIN, 18));
		jchkJinshan.setFont(new Font("Serif", Font.PLAIN, 18));
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
		translation[0] = new Translation("金山翻译");
		translation[1] = new Translation("有道翻译");
		translation[2] = new Translation("必应翻译");
		
		//设置事件响应
		jbtSearch.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				String input = jtfInput.getText();
				if(input.equals(""))
					return;
				int beginindex = 0;
				while(input.charAt(beginindex) == ' ')
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
				String result = word.toString();
				if(result.length() > 0)
					result = result.substring(0, result.length() - 1);
				jtfInput.setText(result);
				showTran();
			}
		});
		jbtAccount.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				//JFrame jfAccount = new JFrame();
				logged = logWnd.getLogged();
				if(!logged) {
					logWnd.clearInput();
					logWnd.setVisible(true);
				}
				else
					;
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
		LikeButton likeJs = new LikeButton();
		LikeButton likeYd = new LikeButton();
		LikeButton likeBy = new LikeButton();
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
		jpCenter.setLayout(new GridLayout(3, 1, 5, 5));
		for(int i = 0; i < 3; ++i) {
			jpCenter.add(jtaMeaning[i]);
		}
		
		setLayout(new BorderLayout(5, 5));
		add(jpNorth, BorderLayout.NORTH);
		add(jpCenter, BorderLayout.CENTER);
	}
	private void showTran() {
		for(int i = 0; i < 3; ++i)
			jtaMeaning[i].setVisible(false);
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
			jtaMeaning[i].setBorder(new TitledBorder(translation[tranRank[index]].name));
			jtaMeaning[i].setText(translation[tranRank[index]].meaning);
			jtaMeaning[i].setVisible(true);
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