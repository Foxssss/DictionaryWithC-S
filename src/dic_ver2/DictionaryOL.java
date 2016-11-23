package dic_ver2;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class DictionaryOL extends JFrame{
	private JButton jbtConfirm = new JButton("��ѯ");
	private JButton jbtClear = new JButton("���");
	private JTextField jtfInput = new JTextField();
	private JCheckBox jchkBaidu = new JCheckBox("�ٶ�", false);
	private JCheckBox jchkYoudao = new JCheckBox("�е�", false);
	private JCheckBox jchkBiying = new JCheckBox("��Ӧ", false);
	private JTextArea jtaBDMeaning = new JTextArea("�ٶȷ���");
	private JTextArea jtaYDMeaning = new JTextArea("�е�����");
	private JTextArea jtaBYMeaning = new JTextArea("��Ӧ����");
	
	public static void main(String[] args) {
		DictionaryOL mydic = new DictionaryOL();
	
		mydic.setTitle("DictionaryOL");
		mydic.setSize(400, 470);
		mydic.setLocationRelativeTo(null);
		mydic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mydic.setVisible(true);
	}
	
	public DictionaryOL() {
		jtfInput.setFont(new Font("Serif", Font.PLAIN, 18));
		jtaBDMeaning.setEditable(false);
		jtaYDMeaning.setEditable(false);
		jtaBYMeaning.setEditable(false);
		jtaBDMeaning.setFont(new Font("Serif", Font.PLAIN, 18));
		jtaYDMeaning.setFont(new Font("Serif", Font.PLAIN, 18));
		jtaBYMeaning.setFont(new Font("Serif", Font.PLAIN, 18));
		jtaBDMeaning.setWrapStyleWord(true);
		jtaBDMeaning.setLineWrap(true);
		jtaYDMeaning.setWrapStyleWord(true);
		jtaYDMeaning.setLineWrap(true);
		jtaBYMeaning.setWrapStyleWord(true);
		jtaBYMeaning.setLineWrap(true);	
		jtaBDMeaning.setBorder(new TitledBorder("�ٶȷ���"));
		jtaYDMeaning.setBorder(new TitledBorder("�е�����"));
		jtaBYMeaning.setBorder(new TitledBorder("��Ӧ����"));
		
		jbtClear.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				jtfInput.setText("");
				jtaBDMeaning.setText("");
				jtaYDMeaning.setText("");
				jtaBYMeaning.setText("");
			}
		});
		
		//input, confirm, clear
		JPanel jpConfirm = new JPanel();
		jpConfirm.setLayout(new BorderLayout(5, 0));
		jpConfirm.add(jtfInput, BorderLayout.CENTER);
		jpConfirm.add(jbtConfirm, BorderLayout.EAST);
		JPanel jpInput = new JPanel();
		jpInput.setLayout(new BorderLayout(5, 0));
		jpInput.add(jpConfirm, BorderLayout.CENTER);
		jpInput.add(jbtClear, BorderLayout.EAST);
		//checkbox
		JPanel jpBaidu = new JPanel();
		JPanel jpYoudao = new JPanel();
		JPanel jpBiying = new JPanel();
		LikeButton likeBd = new LikeButton();
		LikeButton likeYd = new LikeButton();
		LikeButton likeBy = new LikeButton();
		jpBaidu.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jpBaidu.add(jchkBaidu);
		jpBaidu.add(likeBd);
		jpYoudao.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		jpYoudao.add(jchkYoudao);
		jpYoudao.add(likeYd);
		jpBiying.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
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
		jpCenter.add(jtaBDMeaning);
		jpCenter.add(jtaYDMeaning);
		jpCenter.add(jtaBYMeaning);
		
		setLayout(new BorderLayout(5, 5));
		add(jpNorth, BorderLayout.NORTH);
		add(jpCenter, BorderLayout.CENTER);
	}
	
	static class LikeButton extends JButton {
		private ImageIcon img_like = new ImageIcon(getClass().getResource("Like2.png"));
		private ImageIcon img_liked = new ImageIcon(getClass().getResource("Liked2.png"));
		private boolean liked;
		public LikeButton() {
			liked = false;
			setIcon(img_like);
			setToolTipText("����");
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
