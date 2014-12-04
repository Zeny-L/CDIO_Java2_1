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
		super("选择任务");
		initComponents();
		this.setVisible(true);
		this.setBounds(300, 300, 240, 160);
	}

	private void initComponents() {
		// 实例化并添加控件
		cont.setLayout(null);

		taskName = new JLabel("任务名称:");
		cont.add(taskName);
		taskName.setBounds(25, 25, 75, 25);
		
		taskText = new JTextField();
		cont.add(taskText);
		taskText.setBounds(100, 25, 125, 25);
		
		butOk = new JButton("确定");
		cont.add(butOk);
		butOk.setBounds(75, 75, 75, 25);
		
		// 为添加和取消按钮加事件
		butOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.jdbc.Driver"); 
				} catch (Exception e2) {
					System.out.print("Error loading Mysql Driver!");
					e2.printStackTrace();
				}
				//连接数据库, 可以插入任务
				try {
					connect = (Connection) DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/StudyEveryday", "root", "Zeny");
					// 连接URL为 jdbc:mysql//服务器地址/数据库名 ，后面的2个参数分别是登陆用户名和密码
					stmt = (Statement) connect.createStatement();
					String sql = "INSERT INTO `StudyEveryday`.`task` (`TaskID`, `TaskName`, `Publisher`, `Date`, `Receive`) VALUES (NULL, '" + taskText.getText() + "', NULL, NULL, '0');";
					stmt.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "添加任务成功");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "添加任务失败");
				}
			}
		});
		
		this.setResizable(false);
	}
}
