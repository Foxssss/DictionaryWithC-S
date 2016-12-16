package dic_client;
import dic_client.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.regex.Pattern;

import dic_other.ServerConnection;

public class Login extends JFrame {
	private String name;
	//private String password;
	private JLabel jlblName = new JLabel("�û�����");
	private JLabel jlblPassword = new JLabel("   ���룺");
	private JTextField jtfName = new JTextField();
	private JPasswordField jpfPassword = new JPasswordField();
	private JButton jbtLog = new JButton("��¼");
	private JButton jbtRegister = new JButton("ע��");
	private Register regWnd = new Register();
	
	public boolean logged = false;	
	public JButton jbtAccount = null;
	
	public Login() {
		//���ò�������
		jtfName.setFont(new Font("Serif", Font.PLAIN, 18));
		jpfPassword.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblName.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblPassword.setFont(new Font("Serif", Font.PLAIN, 18));
		jbtLog.setFont(new Font("Monospaced", Font.PLAIN, 14));
		jbtRegister.setFont(new Font("Monospaced", Font.PLAIN, 14));
		
		//�����¼���Ӧ
		jbtLog.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				name = jtfName.getText();
				String password = new String(jpfPassword.getPassword());
				if(name.equals("") || password.equals("")) {
					JLabel jlblNull_tmp = new JLabel("�û��������벻��Ϊ��");
					jlblNull_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblNull_tmp, "����", JOptionPane.ERROR_MESSAGE);
					jtfName.setText("");
					jpfPassword.setText("");
					return;
				}
				else if(name.length() > 20) {
					JLabel jlblLen_tmp = new JLabel("�û�������");
					jlblLen_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblLen_tmp, "����", JOptionPane.ERROR_MESSAGE);
					jtfName.setText("");
					jpfPassword.setText("");
					return;
				}
				else if(password.length() < 6 || password.length() > 16 || !legalPassword(password)) {
					JLabel jlblNopw_tmp = new JLabel("�������");
					jlblNopw_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
					JOptionPane.showMessageDialog(null, jlblNopw_tmp, "����", JOptionPane.ERROR_MESSAGE);
					jtfName.setText("");
					jpfPassword.setText("");
					return;
				}
				else {
					String logRequest = "102#" + name + "#" + password;
					do {
						//String message = ServerConnection.sendMessage(logRequest);
						String message = "107";
						if(message.equals("107")) {
							logged = true;
							jbtAccount.setIcon(new ImageIcon(getClass().getResource("logged2.png")));
							JLabel jlblLogsuc_tmp = new JLabel("��¼�ɹ���");
							jlblLogsuc_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
							JOptionPane.showMessageDialog(null, jlblLogsuc_tmp, "��¼�ɹ�", JOptionPane.INFORMATION_MESSAGE);
							break;
						}
						else if(message.equals("108")) {
							JLabel jlblLogfail_tmp = new JLabel("��������û�����������");
							jlblLogfail_tmp.setFont(new Font("Monospaced", Font.BOLD, 14));
							JOptionPane.showMessageDialog(null, jlblLogfail_tmp, "��¼ʧ��", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} while(true);
				}
				setVisible(false);
			}
		});
		jbtRegister.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				setVisible(false);
				regWnd.clearInput();
				regWnd.setVisible(true);
			}
		});
		
		//���ò���
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
		
		//���û�������
		setTitle("�û���¼");
		setSize(400,300);
		setLocationRelativeTo(null);
		setResizable(false);
		pack();
		try {
			Image imgacnt = ImageIO.read(getClass().getResource("account1.png"));
			setIconImage(imgacnt);
		} catch (IOException e1) {
			// TODO �Զ����ɵ� catch ��
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
	public void clearInput() {
		jtfName.setText("");
		jpfPassword.setText("");
		name = "";
	}
	private boolean legalPassword(String str) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]+");
		return p.matcher(str).matches();
	}
}
