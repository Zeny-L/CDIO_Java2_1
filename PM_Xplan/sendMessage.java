import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class sendMessage extends JFrame {
	private JButton butOk;
	private JLabel jLabel1, jLabel2;
	private static JTextField jtf1;
	private static JTextField jtf2;

	public sendMessage() {
		super("���Ͷ���");
		initComponents();
		this.setVisible(true);
		this.setBounds(300, 300, 300, 250);
	}

	//��ʼ������
	private void initComponents() {
		jLabel1 = new JLabel();
		jLabel2 = new JLabel();

		jtf1 = new JTextField();
		jtf2 = new JTextField();

		butOk = new JButton();
		new JButton();

		getContentPane().setLayout(null);// ���ò���

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
				try {
					//���÷���
					readContentFromGet();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		this.setResizable(false);
	}

	public static void readContentFromGet() throws IOException {
		// ���������URL
		String getURL = "http://172.22.53.2:8080/?numbers=" + jtf1.getText()
				+ "&text=" + jtf2.getText();

		URL getUrl = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();

		connection.connect();//��������

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		
		String lines;
		while ((lines = reader.readLine()) != null) {
			System.out.println(lines);
		}
		reader.close();
		//�Ͽ�����
		connection.disconnect();
	}
}