package dic_client;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Pattern;
import dic_other.ServerConnection;

public class Register extends JFrame {
	private boolean success = false;
	private String name;
	//private String password;
	private JLabel jlblName = new JLabel("   用户名：");
	private JLabel jlblPw = new JLabel("       密码：");
	private JLabel jlblPwAgain = new JLabel("确认密码：");
	private JTextField jtfName = new JTextField();
	private JPasswordField jpfPw = new JPasswordField();
	private JPasswordField jpfPwAgain = new JPasswordField();
	private JButton jbtConfirm = new JButton("确认");
	
	public Register() {
		//设置部件属性
		jtfName.setFont(new Font("Serif", Font.PLAIN, 18));
		jpfPw.setFont(new Font("Serif", Font.PLAIN, 18));
		jpfPwAgain.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblName.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblPw.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblPwAgain.setFont(new Font("Serif", Font.PLAIN, 18));
		jbtConfirm.setFont(new Font("Monospaced", Font.PLAIN, 14));
		
		//设置事件响应
		jbtConfirm.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				name = jtfName.getText();
				String password = new String(jpfPw.getPassword());
				String pwAgain = new String(jpfPwAgain.getPassword());
				if(name.equals("") || password.equals("")) {
					JLabel jlblNull_tmp = new JLabel("用户名或密码不能为空!");
					jlblNull_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblNull_tmp, "错误", JOptionPane.ERROR_MESSAGE);
					jtfName.setText("");
					jpfPw.setText("");
					jpfPwAgain.setText("");
					return;
				}
				else if(name.length() > 20) {
					JLabel jlblLen_tmp = new JLabel("用户名长度不得多余20个字符!");
					jlblLen_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblLen_tmp, "错误", JOptionPane.ERROR_MESSAGE);
					jtfName.setText("");
					jpfPw.setText("");
					jpfPwAgain.setText("");
					return;
				}
				else if(!password.equals(pwAgain)) {
					JLabel jlblDiff_tmp = new JLabel("两次输入的密码不一致！");
					jlblDiff_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblDiff_tmp, "错误", JOptionPane.ERROR_MESSAGE);
					jtfName.setText("");
					jpfPw.setText("");
					jpfPwAgain.setText("");
					return;
				}
				else if(password.length() < 6 || password.length() > 16 || !legalPassword(password)) {
					JLabel jlblNopw_tmp = new JLabel("密码格式错误：必须为6-16个字符，只能包含数字和字母");
					jlblNopw_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblNopw_tmp, "错误", JOptionPane.ERROR_MESSAGE);
					jtfName.setText("");
					jpfPw.setText("");
					jpfPwAgain.setText("");
					return;
				}
				else {
					String regRequest = "101#" + name + "#" + password;
					do {
						String message = ServerConnection.sendMessage(regRequest);
						if(message.equals("103")) {
							success = true;
							JLabel jlblRegsuc_tmp = new JLabel("注册成功！");
							jlblRegsuc_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
							JOptionPane.showMessageDialog(null, jlblRegsuc_tmp, "注册成功", JOptionPane.INFORMATION_MESSAGE);
							break;
						}
						else if(message.equals("104")) {
							JLabel jlblRegfail_tmp = new JLabel("用户名已存在");
							jlblRegfail_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
							JOptionPane.showMessageDialog(null, jlblRegfail_tmp, "注册失败", JOptionPane.ERROR_MESSAGE);
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
		JPanel jpRegister = new JPanel();
		jpRegister.setLayout(new GridLayout(6, 1, 5, 5));
		JPanel jpName = new JPanel();
		JPanel jpPw = new JPanel();
		JPanel jpPwAgain = new JPanel();
		JPanel jpConfirm = new JPanel();
		jpName.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jpName.add(jlblName);
		jpName.add(jtfName);
		jtfName.setPreferredSize(new Dimension(200, 40));
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
		jpRegister.add(jpBlank1);
		jpRegister.add(jpName);
		jpRegister.add(jpPw);
		jpRegister.add(jpPwAgain);
		jpRegister.add(jpConfirm);
		jpRegister.add(jpBlank2);
		
		setLayout(new BorderLayout(5, 5));
		add(jpRegister, BorderLayout.CENTER);
		
		//设置基本属性
		setTitle("用户注册");
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
	public String getName() {
		return name;
	}
	public boolean getSuccess() {
		return success;
	}
	public void clearInput() {
		jtfName.setText("");
		jpfPw.setText("");
		jpfPwAgain.setText("");
		name = "";
	}
	private boolean legalPassword(String str) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]+");
		return p.matcher(str).matches();
	}
}