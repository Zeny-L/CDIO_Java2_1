import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class sendMessage extends JFrame {
	private JButton butOk;
	private JLabel jLabel1, jLabel2;
	private JTextField jtf1, jtf2;

	public sendMessage() {
		super("���Ͷ���");
		initComponents();
		this.setVisible(true);
		this.setBounds(300, 300, 300, 250);
	}

	private void initComponents() {
		// ʵ��������ӿؼ�
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		
		jtf1 = new JTextField();
		jtf2 = new JTextField();
		
		butOk = new JButton();
		new JButton();

		getContentPane().setLayout(null);// ���ò���

		// Ϊ�ռ���þ���λ��
		jLabel1.setText("������Ҫ֪ͨ�ĺ���:");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(20, 20, 120, 20);
		
		jLabel2.setText("��������:");
		getContentPane().add(jLabel2);
		jLabel2.setBounds(20, 85, 60, 20);

		getContentPane().add(jtf1);
		jtf1.setBounds(90, 50, 120, 25);

		getContentPane().add(jtf2);
		jtf2.setBounds(75, 115, 150, 50);

		butOk.setText("����");
		getContentPane().add(butOk);
		butOk.setBounds(115, 180, 70, 25);

		// Ϊ��Ӻ�ȡ��ť���¼�
		butOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		this.setResizable(false);
	}
}