package dic_ver2;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Login extends JFrame {
	private String name;
	private String password;
	private JLabel jlblName = new JLabel("�û�����");
	private JLabel jlblPassword = new JLabel("   ���룺");
	private JTextField jtfName = new JTextField();
	private JPasswordField jpfPassword = new JPasswordField();
	private JButton jbtLog = new JButton("��¼");
	private JButton jbtRegister = new JButton("ע��");
	
	public Login() {
		//���ò�������
		jtfName.setFont(new Font("Serif", Font.PLAIN, 18));
		jpfPassword.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblName.setFont(new Font("Serif", Font.PLAIN, 18));
		jlblPassword.setFont(new Font("Serif", Font.PLAIN, 18));
		jbtLog.setFont(new Font("Dialog", Font.BOLD, 13));
		jbtRegister.setFont(new Font("Dialog", Font.BOLD, 13));
		
		//�����¼���Ӧ
		jbtLog.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				name = jtfName.getText();
				password = new String(jpfPassword.getPassword());
				if(name.equals("") || password.equals("")) {
					JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��", "����", JOptionPane.ERROR_MESSAGE);
				}
				System.out.println(password);
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
	public String getPassword() {
		return password;
	}
}
