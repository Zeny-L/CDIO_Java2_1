
import javax.media.*;
import javax.swing.JFrame;

import java.io.*;
import java.awt.*;

public class captureVideo extends JFrame implements ControllerListener {
	private CaptureDeviceInfo infor;
	private MediaLocator mediaLocator;
	private String str1 = "vfw:Logitech USB Video Camera:0";
	private String str2 = "vfw:Microsoft WDM Image Capture (Win32):0";
	private String url = "vfw:Microsoft WDM Image Capture (Win32):0";
	private Component com;
	private Panel panel;
	Player player;
	public captureVideo() {
		infor = CaptureDeviceManager.getDevice("vfw:Microsoft WDM Image Capture (Win32):0");
		mediaLocator = infor.getLocator();
		play();
	}
	
	public void play() {
		try {
			player = Manager.createPlayer(mediaLocator);// ����mediaLocator����Player
			player.addControllerListener(this);
			player.realize();
			// System.out.println("infor:"+infor);
			// System.out.println("mediaLocator:"+mediaLocator);
		} catch (NoPlayerException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		} catch (IOException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	}
	
	public void createComponent() {
		setTitle("��Ƶ�ź�");
		// addWindowListener(new WinClose());
		setBounds(100, 100, 650, 520);
		panel = new Panel();
		if ((com = player.getVisualComponent()) != null) {
			panel.add(com);
		}
		add(panel);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public synchronized void controllerUpdate(ControllerEvent arg0) {
		// TODO �Զ����ɷ������
		if (arg0 instanceof RealizeCompleteEvent) {
			System.out.println("realized");
			createComponent(); // ����������ӵ�Panel��
			player.prefetch();
		}
		if (arg0 instanceof PrefetchCompleteEvent) {
			player.start();
			System.out.println("prefetched");
		}
	}
	/*
	public static void main(String[] arg0) {
		//System.out.println(CaptureDeviceManager.getDeviceList(null).size());
		 CaptureVideo video = new CaptureVideo();
		 video.play();
	}
	*/
}