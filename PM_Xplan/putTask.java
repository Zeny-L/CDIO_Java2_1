import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class putTask extends JFrame {
	private JButton butOk;
	private Container cont = this.getContentPane();
	private JLabel taskName;
	private JTextField taskText;
	private Connection connect = null;
	private Statement stmt = null;

	public putTask() {
		super("ѡ������");
		initComponents();
		this.setVisible(true);
		this.setBounds(300, 300, 240, 160);
	}

	private void initComponents() {
		// ʵ��������ӿؼ�
		cont.setLayout(null);

		taskName = new JLabel("��������:");
		cont.add(taskName);
		taskName.setBounds(25, 25, 75, 25);
		
		taskText = new JTextField();
		cont.add(taskText);
		taskText.setBounds(100, 25, 125, 25);
		
		butOk = new JButton("ȷ��");
		cont.add(butOk);
		butOk.setBounds(75, 75, 75, 25);
		
		// Ϊ��Ӻ�ȡ����ť���¼�
		butOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver"); 
				} catch (Exception e2) {
					System.out.print("Error loading Mysql Driver!");
					e2.printStackTrace();
				}
				//�������ݿ�, ���Բ�������
				try {
					connect = (Connection) DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/StudyEveryday", "root", "Zeny");
					// ����URLΪ jdbc:mysql//��������ַ/���ݿ��� �������2�������ֱ��ǵ�½�û���������
					stmt = (Statement) connect.createStatement();
					String sql = "INSERT INTO `StudyEveryday`.`task` (`TaskID`, `TaskName`, `Publisher`, `Date`, `Receive`) VALUES (NULL, '" + taskText.getText() + "', NULL, NULL, '0');";
					stmt.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "�������ɹ�");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "�������ʧ��");
				}
			}
		});
		
		this.setResizable(false);
	}
}
