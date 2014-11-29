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
		super("发送短信");
		initComponents();
		this.setVisible(true);
		this.setBounds(300, 300, 300, 250);
	}

	private void initComponents() {
		// 实例化并添加控件
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();
		
		jtf1 = new JTextField();
		jtf2 = new JTextField();
		
		butOk = new JButton();
		new JButton();

		getContentPane().setLayout(null);// 设置布局

		// 为空间放置具体位置
		jLabel1.setText("请输入要通知的号码:");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(20, 20, 120, 20);
		
		jLabel2.setText("发送内容:");
		getContentPane().add(jLabel2);
		jLabel2.setBounds(20, 85, 60, 20);

		getContentPane().add(jtf1);
		jtf1.setBounds(90, 50, 120, 25);

		getContentPane().add(jtf2);
		jtf2.setBounds(75, 115, 150, 50);

		butOk.setText("发送");
		getContentPane().add(butOk);
		butOk.setBounds(115, 180, 70, 25);

		// 为添加和取消钮加事件
		butOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		this.setResizable(false);
	}
}