import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//����Ϊ��ͨȨ�޵�������
public class ordinarySystem extends JFrame {

	JDesktopPane deskpane = new JDesktopPane();
	private Dimension frameSize = new Dimension(210, 280);
	JPanel global = new JPanel();
	JPanel buttonPanel = new JPanel();
	JPanel left_blank = new JPanel();
	JPanel right_blank = new JPanel();
	JPanel imagePanel = new JPanel() {
		protected void paintComponent(Graphics g) {
			ImageIcon icon = new ImageIcon(
					getClass().getResource(".").getFile().toString() + "\\������.jpg");
			Image img = icon.getImage();

			g.drawImage(img, 0, 0, frameSize.width, frameSize.height, 0, 0,
					icon.getIconWidth(), icon.getIconHeight(),
					icon.getImageObserver());
		}
	};

	public ordinarySystem() {
		setTitle("���찮ѧϰ");
		Container con = getContentPane();
		left_blank.setPreferredSize(new Dimension(190, 280));
		con.setLayout(new BorderLayout());
		con.add(global, BorderLayout.NORTH);
		con.add(left_blank, BorderLayout.WEST);
		con.add(imagePanel, BorderLayout.CENTER);
		con.add(right_blank, BorderLayout.EAST);
		con.add(buttonPanel, BorderLayout.SOUTH);

		JLabel jlabel1 = new JLabel();
		jlabel1.setFont(new Font("����", Font.BOLD, 15));
		jlabel1.setHorizontalAlignment(SwingConstants.CENTER);
		global.add(jlabel1);

		final JLabel jlabel2 = new JLabel("��ȡ����");
		jlabel2.setFont(new Font("������", Font.BOLD, 13));
		jlabel2.setHorizontalAlignment(SwingConstants.CENTER);

		jlabel2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				URI uri;
				try {
					uri = new URI("http://199.189.175.100:8000/discuz/");
					Desktop dep = Desktop.getDesktop();

					if (Desktop.isDesktopSupported()
							&& dep.isSupported(Desktop.Action.BROWSE)) {
						try {
							dep.browse(uri);
						} catch (IOException e1) {
							
							e1.printStackTrace();
						}
					}
				} catch (URISyntaxException e2) {
					
				}

			}
		});
		right_blank.add(jlabel2);

		buttonPanel.setLayout(new GridLayout(2, 2));

		Button video = new Button("��Ƶ����");
		video.setFont(new Font("����", Font.BOLD, 15));
		buttonPanel.add(video);

		Button drawBoard = new Button("���廥��");
		drawBoard.setFont(new Font("����", Font.BOLD, 15));
		buttonPanel.add(drawBoard);

		Button task = new Button("��ȡ����");
		task.setFont(new Font("����", Font.BOLD, 15));
		buttonPanel.add(task);

		Button message = new Button("��������");
		message.setFont(new Font("����", Font.BOLD, 15));
		buttonPanel.add(message);

		//��Ƶ��ť�ĵ���¼�
		video.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				global.add(new captureVideo());
				//global.add(new connectVideo());
			}
		});

		//���尴ť�ĵ���¼�
		drawBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				global.add(new beginDraw());
			}
		});
		
		//����ť�ĵ���¼�
		task.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				global.add(new getTask());
			}
		});

		//���Ű�ť�ĵ���¼�
		message.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				global.add(new sendMessage());
			}
		});

		this.setBounds(250, 250, 600, 400);
		this.setVisible(true);
		this.setResizable(false);
	}

	public static void main(String[] args) {
		new Land();
	}
}
