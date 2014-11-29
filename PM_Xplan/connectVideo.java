import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.CaptureDeviceInfo;
import javax.media.CaptureDeviceManager;
import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.DataSource;
import javax.media.protocol.PushBufferDataSource;
import javax.media.util.BufferToImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import jmapps.jmstudio.CaptureDialog;
import jmapps.ui.PlayerFrame;
import jmapps.util.CDSWrapper;
import jmapps.util.JMFUtils;

public class connectVideo extends PlayerFrame {
    public static String picUrl="";
    public boolean flag=false; 
    
    
    public connectVideo() {
        super(null, "��Ƶ���񴰿�");
    }
    
    DataSource dataSource;
    private CaptureDeviceInfo infor;
    private MediaLocator mediaLocator;
    String str1 = "vfw:Logitech USB Video Camera:0";
    String str2 = "vfw:Microsoft WDM Image Capture (Win32):0"; 
    private String url = "vfw:Microsoft WDM Image Capture (Win32):0";
    private Component com;
    private JPanel panel;
    private int captureCount = 0;
    FrameGrabbingControl controlGrabber;
    public void play() {
        if (mediaPlayerCurrent.getState() != mediaPlayerCurrent.Started) {
            mediaPlayerCurrent.start();
        }
    }

    public void init() throws NoPlayerException, IOException {
        // setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        String nameCaptureDeviceAudio = null;
        String nameCaptureDeviceVideo = null;
        CaptureDialog dialogCapture = new CaptureDialog(this, null);
        dialogCapture.show();
        if (dialogCapture.getAction() == CaptureDialog.ACTION_CANCEL)
            return;
        CaptureDeviceInfo cdi = dialogCapture.getAudioDevice();
        if (cdi != null && dialogCapture.isAudioDeviceUsed())
            nameCaptureDeviceAudio = cdi.getName();
        cdi = dialogCapture.getVideoDevice();
        if (cdi != null && dialogCapture.isVideoDeviceUsed())
            nameCaptureDeviceVideo = cdi.getName();
        dataSource = JMFUtils.createCaptureDataSource(nameCaptureDeviceAudio,
                dialogCapture.getAudioFormat(), nameCaptureDeviceVideo,
                dialogCapture.getVideoFormat());
        DataSource cdswrapper = new CDSWrapper(
                (PushBufferDataSource) dataSource);
        dataSource = cdswrapper;
        dataSource.connect();
        open(dataSource);
        JPanel controlPanel = new JPanel();
        controlPanel.setName("controlPnael is here");
        add(BorderLayout.SOUTH, controlPanel);
        
        JButton playStop = new JButton("stop");
        // add(BorderLayout.SOUTH,playControl);
        playStop.addActionListener(new ActionListener() {
            // @Override
            public void actionPerformed(ActionEvent arg0) {
                mediaPlayerCurrent.stop();
            }
        });
        controlPanel.add(BorderLayout.EAST, playStop);
        JButton playStart = new JButton("start");
        // add(BorderLayout.SOUTH,playControl);
        playStart.addActionListener(new ActionListener() {

            // @Override
            public void actionPerformed(ActionEvent arg0) {
                // mediaPlayerCurrent.stop();
                if (mediaPlayerCurrent.getState() != mediaPlayerCurrent.Started) {
                    mediaPlayerCurrent.start();
                }
            }
        });
        controlPanel.add(BorderLayout.WEST, playStart);
        addWindowListener(new WindowAdapter() {
            // @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerCurrent.close();
                dataSource.disconnect();
                System.out.println("exit.....");
                this.windowClosed(e);
                //System.exit(0);
            }
        });
    }

    public static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }
        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent
        // Pixels
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the
        // screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null), image
                    .getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
            System.err.println("The system does not have a screen!");
            System.exit(-1);
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image
                    .getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }

    public MediaLocator autoDetect() {// �Զ�ʶ���ܺ���
        MediaLocator ml = null; // ��Ƶ�ɼ��豸��Ӧ��MediaLocator
        VideoFormat currentFormat = null;// �û����ƻ����Ƶ�ɼ��豸֧�ֵĸ�ʽ
        Format setFormat = null;// �û�������Ƶ�ɼ��豸����ĸ�ʽ
        Format[] videoFormats = null;// ��Ƶ�ɼ��豸֧�ֵ����и�ʽ
        System.out.println(" AutoDetect for VFW");// VFW:΢���� Video for Windows
        // ��õ�ǰ�����豸�б�
        Vector deviceList = CaptureDeviceManager.getDeviceList(null);
        CaptureDeviceInfo device = CaptureDeviceManager.getDevice(url); 
        if (deviceList != null) {
            // �����豸�б����ҳ������豸����
            for (int i = 0; i < deviceList.size(); i++) {
                try {
                    CaptureDeviceInfo di = (CaptureDeviceInfo) deviceList
                            .elementAt(i);
                    // ����豸������vfw��ͷ
                    if (di.getName().startsWith("vfw:")) {
                        // �������֧��RGB��ʽ
                        videoFormats = di.getFormats();
                        for (int j = 0; j < videoFormats.length; j++) {
                            // ����ֻ��Ҫ��һ��RGB��ʽ
                            if (videoFormats[j] instanceof RGBFormat) {
                                currentFormat = (RGBFormat) videoFormats[i];
                                break;
                            }
                        }
                        if (currentFormat == null) {
                            System.err.println("Search For RGBFormat Failed");
                            System.exit(-1);
                        }
                        // ͨ���豸�����MediaLocator���������Ҫ
                        ml = di.getLocator();
                    }
                } catch (Exception npe) {
                    System.err.println("Unable to get Processor for device");
                    System.exit(-1);
                }
            }
        } else {
            System.err.println("No Capture Device OK");
            System.exit(-1);
        }
        mediaLocator = ml;
        return ml;// ���ؿ��õ��豸medialocator
    }

    public static void main(String[] args) throws NoPlayerException,
            IOException {
        connectVideo demo = new connectVideo();
        demo.setSize(100, 100);
        demo.autoDetect();
        demo.init();
        demo.play();
        demo.setVisible(true);
    }
}