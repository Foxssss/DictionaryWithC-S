package dic_ver2;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Pattern;

public class Login extends JFrame {
	private String name;
	private String password;
	private JLabel jlblName = new JLabel("用户名：");
	private JLabel jlblPassword = new JLabel("   密码：");
	private JTextField jtfName = new JTextField();
	private JPasswordField jpfPassword = new JPasswordField();
	private JButton jbtLog = new JButton("登录");
	private JButton jbtRegister = new JButton("注册");
	private Register regWnd = new Register();
	private Boolean logged = false;
	
	public Login() {
		//设置部件属性
		jtfName.setFont(new Font("Serif", Font.PLAIN, 18));
		jpfPassword.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblName.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblPassword.setFont(new Font("Serif", Font.PLAIN, 18));
		jbtLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
		jbtRegister.setFont(new Font("Monospaced", Font.PLAIN, 14));
		
		//设置事件响应
		jbtLog.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				name = jtfName.getText();
				password = new String(jpfPassword.getPassword());
				if(name.equals("") || password.equals("")) {
					JLabel jlblNull_tmp = new JLabel("用户名或密码不能为空");
					jlblNull_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblNull_tmp, "错误", JOptionPane.ERROR_MESSAGE);
					jtfName.setText("");
					jpfPassword.setText("");
					return;
				}
				else if(name.length() > 20) {
					JLabel jlblLen_tmp = new JLabel("用户名错误");
					jlblLen_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblLen_tmp, "错误", JOptionPane.ERROR_MESSAGE);
					jtfName.setText("");
					jpfPassword.setText("");
					return;
				}
				else if(password.length() < 6 || password.length() > 16 || !legalPassword(password)) {
					JLabel jlblNopw_tmp = new JLabel("密码错误");
					jlblNopw_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblNopw_tmp, "错误", JOptionPane.ERROR_MESSAGE);
					jtfName.setText("");
					jpfPassword.setText("");
					return;
				}
				logged = true;
				setVisible(false);
			}
		});
		jbtRegister.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				setVisible(false);
				regWnd.setVisible(true);
			}
		});
		
		//设置布局
		JPanel jpBlank1 = new JPanel();
		JPanel jpBlank2 = new JPanel();
		JPanel jpBlank3 = new JPanel();
		//JPanel jpBlank4 = new JPanel();
		//JPanel jpBlank5 = new JPanel();
		JPanel jpLog = new JPanel();
		jpLog.setLayout(new GridLayout(6, 1, 5, 5));
		JPanel jpName = new JPanel();
		JPanel jpPassword = new JPanel();
		JPanel jpConfirm = new JPanel();
		jpName.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jpName.add(jlblName);
		jpName.add(jtfName);
		jtfName.setPreferredSize(new Dimension(200, 40));
		jpPassword.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jpPassword.add(jlblPassword);
		jpPassword.add(jpfPassword);
		jpfPassword.setPreferredSize(new Dimension(200, 40));
		jpConfirm.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
		jpConfirm.add(jbtLog);
		jpConfirm.add(jbtRegister);
		jpLog.add(jpBlank1);
		jpLog.add(jpName);
		jpLog.add(jpPassword);
		jpLog.add(jpConfirm);
		jpLog.add(jpBlank2);
		jpLog.add(jpBlank3);
		
		setLayout(new BorderLayout(5, 5));
		add(jpLog, BorderLayout.CENTER);
		
		//设置基本属性
		setTitle("用户登录");
		setSize(400,300);
		setLocationRelativeTo(null);
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
	public String getPassword() {
		return password;
	}
	public Boolean getLogged() {
		return logged;
	}
	public void clearInput() {
		jtfName.setText("");
		jpfPassword.setText("");
		name = "";
		password = "";
	}
	private boolean legalPassword(String str) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]+");
		return p.matcher(str).matches();
	}
}
