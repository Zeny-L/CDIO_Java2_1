import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class beginDraw extends JFrame {
	// ����
	private Palette palette = null;

	// ��ʾ��ǰ��ɫ�����
	private Panel nonceColor = null;

	// ���ʴ�ϸ
	private Label drawWidth = null;

	// ���ʶ˵��װ��
	private Label drawCap = null;

	// ѡȡ��ɫ��ť�ļ����¼���
	private ButtonColorAction buttonColorAction = null;

	// �����밴ť������ʽ�ļ����¼���
	private ButtonCursor buttonCursor = null;

	// ������ʽ�ļ����¼�
	private ButtonStrokeAction buttonStrokeAction = null;

	public beginDraw() {
		// ���ñ���������
		super("���廥��");
		// ����һ����ͼ��
		palette = new Palette();
		Panel pane = new Panel(new GridLayout(2, 1));
		// ������ɫѡ����
		Panel paneColor = new Panel(new GridLayout(1, 13));
		// 12 ����ɫѡ��ť
		Button[] buttonColor = new Button[12];
		Color[] color = { Color.black, Color.blue, Color.cyan, Color.darkGray,
				Color.gray, Color.green, Color.magenta, Color.orange,
				Color.pink, Color.red,

				Color.white, Color.yellow };
		// ��ʾ��ǰ��ɫ�����
		nonceColor = new Panel();
		nonceColor.setBackground(Color.black);
		paneColor.add(nonceColor);
		buttonColorAction = new ButtonColorAction();
		buttonCursor = new ButtonCursor();
		for (int i = 0; i < buttonColor.length; i++) {
			buttonColor[i] = new Button();
			buttonColor[i].setBackground(color[i]);
			buttonColor[i].addActionListener(buttonColorAction);
			buttonColor[i].addMouseListener(buttonCursor);
			paneColor.add(buttonColor[i]);
		}
		pane.add(paneColor);
		// ������ɫѡ����
		Panel paneStroke = new Panel(new GridLayout(1, 13));
		// ���ƻ�����ʽ
		buttonStrokeAction = new ButtonStrokeAction();
		Button[] buttonStroke = new Button[11];
		buttonStroke[0] = new Button("1");
		buttonStroke[1] = new Button("3");
		buttonStroke[2] = new Button("5");
		buttonStroke[3] = new Button("7");
		buttonStroke[4] = new Button("9");
		buttonStroke[5] = new Button("11");
		buttonStroke[6] = new Button("13");
		buttonStroke[7] = new Button("15");
		buttonStroke[8] = new Button("17");
		buttonStroke[9] = new Button("��");
		buttonStroke[10] = new Button("��");
		drawWidth = new Label("3", Label.CENTER);
		drawCap = new Label("��", Label.CENTER);
		drawWidth.setBackground(Color.lightGray);
		drawCap.setBackground(Color.lightGray);
		paneStroke.add(drawWidth);
		for (int i = 0; i < buttonStroke.length; i++) {
			paneStroke.add(buttonStroke[i]);
			buttonStroke[i].addMouseListener(buttonCursor);
			buttonStroke[i].addActionListener(buttonStrokeAction);
			if (i <= 8) {
				buttonStroke[i].setName("width");
			} else {
				buttonStroke[i].setName("cap");
			}
			if (i == 8) {
				paneStroke.add(drawCap);
			}
		}
		pane.add(paneStroke);
		// ��������ɫѡ������ӵ�������
		this.add(pane, BorderLayout.NORTH);

		// ����ͼ����ӵ�������
		this.add(palette);

		// ���ô��� ICON ͼ��
		this.setIconImage(Toolkit.getDefaultToolkit().createImage(
				"images/palette.png"));

		// ���ô��ڵĴ�С
		this.setSize(new Dimension(400, 430));
		// ���ô���λ�ã�������Ļ������
		this.setLocationRelativeTo(null);
		// ��ʾ����
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new beginDraw();
	}

	class ButtonColorAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Color color_temp = ((Button) e.getSource()).getBackground();
			nonceColor.setBackground(color_temp);
			palette.setColor(color_temp);
		}

	}

	class ButtonCursor extends MouseAdapter {

		public void mouseEntered(MouseEvent e) {
			((Button) e.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		public void mouseExited(MouseEvent e) {
			((Button) e.getSource())
					.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}

	class ButtonStrokeAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Button button_temp = (Button) e.getSource();
			String name = button_temp.getName();
			if (name.equalsIgnoreCase("width")) {
				drawWidth.setText(button_temp.getLabel());
				palette.setStroke(Float.parseFloat(button_temp.getLabel()));
			} else if (name.equalsIgnoreCase("cap")) {
				drawCap.setText(button_temp.getLabel());
				if (button_temp.getLabel().equals("��")) {
					palette.setStroke(BasicStroke.CAP_SQUARE);
				} else if (button_temp.getLabel().equals("��")) {
					palette.setStroke(BasicStroke.CAP_ROUND);
				}
			}
		}

	}

}

class Palette extends Panel implements MouseListener, MouseMotionListener {
	// ��� X �����λ��
	private int mouseX = 0;

	// ��һ�� X ����λ��
	private int oldMouseX = 0;

	// ��� Y �����λ��
	private int mouseY = 0;

	// ��һ�� Y ����λ��
	private int oldMouseY = 0;

	// ��ͼ��ɫ
	private Color color = null;

	// ������ʽ
	private BasicStroke stroke = null;

	// ����ͼ��
	private BufferedImage image = null;

	/**
	 * ����һ��������
	 * 
	 */
	public Palette() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		// Ĭ�Ϻ�ɫ����
		color = new Color(0, 0, 0);
		// ����Ĭ�ϻ�����ʽ
		stroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		// ���� 1280 * 1024 �� RGB ����ͼ��
		image = new BufferedImage(1280, 1024, BufferedImage.TYPE_INT_RGB);
		// ������ɫ
		image.getGraphics().setColor(Color.white);
		// ������
		image.getGraphics().fillRect(0, 0, 1280, 1024);
	}

	/**
	 * ��д paint ��ͼ����
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// ת��Ϊ Graphics2D
		Graphics2D g2d = (Graphics2D) g;

		// ��ȡ����ͼ�� Graphics2D
		Graphics2D bg = image.createGraphics();

		// ͼ�ο����
		bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// ���û�ͼ��ɫ
		bg.setColor(color);

		// ���û�����ʽ
		bg.setStroke(stroke);

		// ���ߣ�����һ���㵽�µĵ�
		bg.drawLine(oldMouseX, oldMouseY, mouseX, mouseY);

		// �������е�ͼ�λ���������
		g2d.drawImage(image, 0, 0, this);
	}

	/**
	 * ��д update ����
	 */
	public void update(Graphics g) {
		this.paint(g);
	}

	/**
	 * @return stroke
	 */
	public BasicStroke getStroke() {
		return stroke;
	}

	/**
	 * @param stroke
	 *            Ҫ���õ� stroke
	 */
	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}

	/**
	 * ���û��ʴ�ϸ
	 * 
	 * @param width
	 */
	public void setStroke(float width) {
		this.stroke = new BasicStroke(width, stroke.getEndCap(),
				stroke.getLineJoin());
	}

	/**
	 * ���û��ʶ˵��װ��
	 * 
	 * @param cap
	 *            �ο� java.awt.BasicStroke �ྲ̬�ֶ�
	 */
	public void setStroke(int cap) {
		this.stroke = new BasicStroke(stroke.getLineWidth(), cap,
				stroke.getLineJoin());
	}

	/**
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            Ҫ���õ� color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	public void mouseClicked(MouseEvent mouseEvent) {
	}

	/**
	 * ��갴��
	 */
	public void mousePressed(MouseEvent mouseEvent) {
		this.oldMouseX = this.mouseX = mouseEvent.getX();
		this.oldMouseY = this.mouseY = mouseEvent.getY();
		repaint();
	}

	public void mouseReleased(MouseEvent mouseEvent) {
	}

	/**
	 * ����������
	 */
	public void mouseEntered(MouseEvent mouseEvent) {
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	/**
	 * ����˳�����
	 */
	public void mouseExited(MouseEvent mouseEvent) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * ����϶�
	 */
	public void mouseDragged(MouseEvent mouseEvent) {
		this.oldMouseX = this.mouseX;
		this.oldMouseY = this.mouseY;
		this.mouseX = mouseEvent.getX();
		this.mouseY = mouseEvent.getY();
		repaint();
	}

	public void mouseMoved(MouseEvent mouseEvent) {
	}

}