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
	// 画板
	private Palette palette = null;

	// 显示当前颜色的面板
	private Panel nonceColor = null;

	// 画笔粗细
	private Label drawWidth = null;

	// 画笔端点的装饰
	private Label drawCap = null;

	// 选取颜色按钮的监听事件类
	private ButtonColorAction buttonColorAction = null;

	// 鼠标进入按钮后光标样式的监听事件类
	private ButtonCursor buttonCursor = null;

	// 画笔样式的监听事件
	private ButtonStrokeAction buttonStrokeAction = null;

	public beginDraw() {
		// 设置标题栏文字
		super("画板互动");
		// 构造一个画图板
		palette = new Palette();
		Panel pane = new Panel(new GridLayout(2, 1));
		// 画笔颜色选择器
		Panel paneColor = new Panel(new GridLayout(1, 13));
		// 12 个颜色选择按钮
		Button[] buttonColor = new Button[12];
		Color[] color = { Color.black, Color.blue, Color.cyan, Color.darkGray,
				Color.gray, Color.green, Color.magenta, Color.orange,
				Color.pink, Color.red,

				Color.white, Color.yellow };
		// 显示当前颜色的面板
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
		// 画笔颜色选择器
		Panel paneStroke = new Panel(new GridLayout(1, 13));
		// 控制画笔样式
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
		buttonStroke[9] = new Button("■");
		buttonStroke[10] = new Button("●");
		drawWidth = new Label("3", Label.CENTER);
		drawCap = new Label("●", Label.CENTER);
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
		// 将画笔颜色选择器添加到窗体中
		this.add(pane, BorderLayout.NORTH);

		// 将画图板添加到窗体中
		this.add(palette);

		// 设置窗体 ICON 图标
		this.setIconImage(Toolkit.getDefaultToolkit().createImage(
				"images/palette.png"));

		// 设置窗口的大小
		this.setSize(new Dimension(400, 430));
		// 设置窗口位置，处于屏幕正中央
		this.setLocationRelativeTo(null);
		// 显示窗口
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
				if (button_temp.getLabel().equals("■")) {
					palette.setStroke(BasicStroke.CAP_SQUARE);
				} else if (button_temp.getLabel().equals("●")) {
					palette.setStroke(BasicStroke.CAP_ROUND);
				}
			}
		}

	}

}

class Palette extends Panel implements MouseListener, MouseMotionListener {
	// 鼠标 X 坐标的位置
	private int mouseX = 0;

	// 上一次 X 坐标位置
	private int oldMouseX = 0;

	// 鼠标 Y 坐标的位置
	private int mouseY = 0;

	// 上一次 Y 坐标位置
	private int oldMouseY = 0;

	// 画图颜色
	private Color color = null;

	// 画笔样式
	private BasicStroke stroke = null;

	// 缓存图形
	private BufferedImage image = null;

	/**
	 * 构造一个画板类
	 * 
	 */
	public Palette() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		// 默认黑色画笔
		color = new Color(0, 0, 0);
		// 设置默认画笔样式
		stroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND);
		// 建立 1280 * 1024 的 RGB 缓存图象
		image = new BufferedImage(1280, 1024, BufferedImage.TYPE_INT_RGB);
		// 设置颜色
		image.getGraphics().setColor(Color.white);
		// 画背景
		image.getGraphics().fillRect(0, 0, 1280, 1024);
	}

	/**
	 * 重写 paint 绘图方法
	 */
	public void paint(Graphics g) {
		super.paint(g);

		// 转换为 Graphics2D
		Graphics2D g2d = (Graphics2D) g;

		// 获取缓存图形 Graphics2D
		Graphics2D bg = image.createGraphics();

		// 图形抗锯齿
		bg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// 设置绘图颜色
		bg.setColor(color);

		// 设置画笔样式
		bg.setStroke(stroke);

		// 画线，从上一个点到新的点
		bg.drawLine(oldMouseX, oldMouseY, mouseX, mouseY);

		// 将缓存中的图形画到画板上
		g2d.drawImage(image, 0, 0, this);
	}

	/**
	 * 重写 update 方法
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
	 *            要设置的 stroke
	 */
	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}

	/**
	 * 设置画笔粗细
	 * 
	 * @param width
	 */
	public void setStroke(float width) {
		this.stroke = new BasicStroke(width, stroke.getEndCap(),
				stroke.getLineJoin());
	}

	/**
	 * 设置画笔端点的装饰
	 * 
	 * @param cap
	 *            参考 java.awt.BasicStroke 类静态字段
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
	 *            要设置的 color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	public void mouseClicked(MouseEvent mouseEvent) {
	}

	/**
	 * 鼠标按下
	 */
	public void mousePressed(MouseEvent mouseEvent) {
		this.oldMouseX = this.mouseX = mouseEvent.getX();
		this.oldMouseY = this.mouseY = mouseEvent.getY();
		repaint();
	}

	public void mouseReleased(MouseEvent mouseEvent) {
	}

	/**
	 * 鼠标进入棋盘
	 */
	public void mouseEntered(MouseEvent mouseEvent) {
		this.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	/**
	 * 鼠标退出棋盘
	 */
	public void mouseExited(MouseEvent mouseEvent) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * 鼠标拖动
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