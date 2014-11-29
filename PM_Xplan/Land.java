import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class Land extends JFrame {
	JFrame jf;
	JTextField textname = new JTextField();
	JPasswordField textmima = new JPasswordField();
	JLabel label = new JLabel("���찮ѧϰ");
	JLabel labelname = new JLabel("�û���");
	JLabel labelmima = new JLabel("����");
	JButton btenter = new JButton("ȷ��");
	JButton btcancel = new JButton("���");
	
	public Land() {
		jf = this;
		setTitle("��¼");
		Font f = new Font("������", Font.PLAIN, 12);
		Container ctn = getContentPane();
		ctn.setLayout(null);
		this.setResizable(true);
		
		labelname.setBounds(45, 40, 55, 20);
		labelname.setFont(f);
		ctn.add(labelname);
		textname.setBounds(95, 40, 120, 20);
		ctn.add(textname);

		labelmima.setBounds(45, 70, 45, 20);
		ctn.add(labelmima);
		labelmima.setFont(f);
		textmima.setBounds(95, 70, 120, 20);
		ctn.add(textmima);

		btenter.setBounds(90, 110, 60, 20);
		btenter.setFont(f);
		ctn.add(btenter);
		Monitor_1 bt = new Monitor_1();
		btenter.addActionListener(bt);

		btcancel.setBounds(155, 110, 60, 20);
		btcancel.setFont(f);
		ctn.add(btcancel);
		Monitor_2 btc = new Monitor_2();
		btcancel.addActionListener(btc);
		jf.setResizable(false);
		jf.setLocation(400, 400);
		jf.setSize(300, 200);
		jf.setVisible(true);

	}

	class Monitor_1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (textname.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "�û�������Ϊ��!");
			} else if (textmima.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "���벻��Ϊ��!");
			} else {
				String name_mima = textname.getText() + " " + textmima.getText();
				try {
					Judge(name_mima);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class Monitor_2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			textname.setText("");
			textmima.setText("");
		}
	}

	private void Judge(String loginInfo) throws IOException {
		boolean mark = true;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			Connection connect = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/StudyEveryday", "root", "Zeny");

			Statement stmt = (Statement) connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from adopter");
			while (rs.next()) {
				if(rs.getString("UserName").equals(textname.getText().toString())&&rs.getString("UserPassword").equals(textmima.getText().toString())) {
					jf.setVisible(false);
					mark = false;
					new system();
				}
			}
			if(mark)
				JOptionPane.showMessageDialog(null, "�û����������!");
		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new Land();
	}
}
