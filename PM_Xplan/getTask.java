import java.awt.Container;
import java.awt.GridBagConstraints;
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
import javax.swing.JList;
import javax.swing.JScrollPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class getTask extends JFrame {
	private JButton butOk;
	private Container cont = this.getContentPane();
	private JList list = null;
	private Connection connect = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	int gridx, gridy, gridwidth, gridheight, anchor, fill, ipadx, ipady;
	double weightx, weighty;
	Insets inset;

	public getTask() {
		super("ѡ������");
		initComponents();
		this.setVisible(true);
		this.setBounds(300, 300, 200, 300);
	}

	private void initComponents() {
		// ʵ��������ӿؼ�
		cont.setLayout(null);

		Vector<String> v = new Vector<String>();// ��ʵ���Զ�������������
		try {
			Class.forName("com.mysql.jdbc.Driver"); // ����MYSQL JDBC��������
		} catch (Exception e) {
			System.out.print("Error loading Mysql Driver!");
			e.printStackTrace();
		}
		try {
			connect = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/StudyEveryday", "root", "Zeny");
			// ����URLΪ jdbc:mysql//��������ַ/���ݿ��� �������2�������ֱ��ǵ�½�û���������
			stmt = (Statement) connect.createStatement();
			rs = stmt.executeQuery("select * from task");
			while (rs.next()) {
				if(rs.getString("Receive").equals("0"))
					v.add(rs.getString("TaskName"));
			}
		} catch (Exception e) {
			System.out.print("get data error!");
			e.printStackTrace();
		}
		

		list = new JList(v);
		cont.add(list);
		list.setBorder(BorderFactory.createTitledBorder("�����б�"));
		
		JScrollPane jsp = new JScrollPane(list);
		cont.add(jsp);
		jsp.setBounds(50, 20, 100, 175);

		butOk = new JButton("ȷ��");
		getContentPane().add(butOk);
		butOk.setBounds(70, 210, 60, 25);

		// Ϊ��Ӻ�ȡ����ť���¼�
		butOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rs = stmt.executeQuery("select * from task");
					while (rs.next()) {
						if(rs.getString("TaskName").equals(list.getSelectedValue())) {
							String sql = "update task set Receive = 1 where TaskID ="+Integer.parseInt(rs.getString("TaskID"));
							stmt.executeUpdate(sql); 
							}
						}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		this.setResizable(false);
	}
}
