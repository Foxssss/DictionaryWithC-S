package dic_client;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Pattern;
import dic_other.ServerConnection;

public class AdjustPassword extends JFrame {
	private boolean success = false;
	private String name;
	//private String password;
	private JLabel jlblOldpw = new JLabel("       旧密码：");
	private JLabel jlblPw = new JLabel("       新密码：");
	private JLabel jlblPwAgain = new JLabel("  确认密码：");
	private JPasswordField jpfOldpw = new JPasswordField();
	private JPasswordField jpfPw = new JPasswordField();
	private JPasswordField jpfPwAgain = new JPasswordField();
	private JButton jbtConfirm = new JButton("确认");
	
	public AdjustPassword() {
		//设置部件属性
		jpfOldpw.setFont(new Font("Serif", Font.PLAIN, 18));
		jpfPw.setFont(new Font("Serif", Font.PLAIN, 18));
		jpfPwAgain.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblOldpw.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblPw.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblPwAgain.setFont(new Font("Serif", Font.PLAIN, 18));
		jbtConfirm.setFont(new Font("Monospaced", Font.PLAIN, 14));
		
		//设置事件响应
		jbtConfirm.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				String oldPW = new String(jpfOldpw.getPassword());
				String newPW = new String(jpfPw.getPassword());
				String pwAgain = new String(jpfPwAgain.getPassword());
				if(oldPW.equals("") || newPW.equals("") || pwAgain.equals("")) {
					JLabel jlblNull_tmp = new JLabel("输入不能为空!");
					jlblNull_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblNull_tmp, "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if(!newPW.equals(pwAgain)) {
					JLabel jlblDiff_tmp = new JLabel("两次输入的新密码不一致！");
					jlblDiff_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblDiff_tmp, "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if(newPW.length() < 6 || newPW.length() > 16 || !legalPassword(newPW)) {
					JLabel jlblNopw_tmp = new JLabel("密码格式错误：必须为6-16个字符，只能包含数字和字母");
					jlblNopw_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblNopw_tmp, "错误", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else {
					String adjRequest = "103#" + name + "#" + oldPW + "#" + newPW;
					do {
						String message = ServerConnection.sendMessage(adjRequest);
						if(message.equals("106")) {
							success = true;
							JLabel jlblRegsuc_tmp = new JLabel("修改密码成功！");
							jlblRegsuc_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
							JOptionPane.showMessageDialog(null, jlblRegsuc_tmp, "修改成功", JOptionPane.INFORMATION_MESSAGE);
							break;
						}
						else if(message.equals("105")) {
							JLabel jlblRegfail_tmp = new JLabel("旧密码输入错误");
							jlblRegfail_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
							JOptionPane.showMessageDialog(null, jlblRegfail_tmp, "修改失败", JOptionPane.ERROR_MESSAGE);
							break;
						}
					} while (true);
				}
				setVisible(false);
			}
		});
		
		//设置布局
		JPanel jpBlank1 = new JPanel();
		JPanel jpBlank2 = new JPanel();
		JPanel jpAdjustPassword = new JPanel();
		jpAdjustPassword.setLayout(new GridLayout(6, 1, 5, 5));
		JPanel jpName = new JPanel();
		JPanel jpPw = new JPanel();
		JPanel jpPwAgain = new JPanel();
		JPanel jpConfirm = new JPanel();
		jpName.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jpName.add(jlblOldpw);
		jpName.add(jpfOldpw);
		jpfOldpw.setPreferredSize(new Dimension(200, 40));
		jpPw.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jpPw.add(jlblPw);
		jpPw.add(jpfPw);
		jpfPw.setPreferredSize(new Dimension(200, 40));
		jpPwAgain.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jpPwAgain.add(jlblPwAgain);
		jpPwAgain.add(jpfPwAgain);
		jpfPwAgain.setPreferredSize(new Dimension(200, 40));
		jpConfirm.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		jpConfirm.add(jbtConfirm);
		jpAdjustPassword.add(jpBlank1);
		jpAdjustPassword.add(jpName);
		jpAdjustPassword.add(jpPw);
		jpAdjustPassword.add(jpPwAgain);
		jpAdjustPassword.add(jpConfirm);
		jpAdjustPassword.add(jpBlank2);
		
		setLayout(new BorderLayout(5, 5));
		add(jpAdjustPassword, BorderLayout.CENTER);
		
		//设置基本属性
		setTitle("修改密码");
		setSize(400,300);
		setLocationRelativeTo(null);
		setResizable(false);
		pack();
		try {
			Image imgacnt = ImageIO.read(getClass().getResource("account1.png"));
			setIconImage(imgacnt);
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		setVisible(false);		
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(400, 300);
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getSuccess() {
		return success;
	}
	public void clearInput() {
		jpfOldpw.setText("");
		jpfPw.setText("");
		jpfPwAgain.setText("");
	}
	private boolean legalPassword(String str) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]+");
		return p.matcher(str).matches();
	}
}