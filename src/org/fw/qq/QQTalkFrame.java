package org.fw.qq;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;

import javax.media.rtp.SessionAddress;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.fw.FramePanel;
import org.fw.OpaqueButton;
import org.fw.OpaquePanel;
import org.fw.qq.plugins.screencut.CutScreen;
import org.fw.qq.plugins.sound.Capture;
import org.fw.qq.plugins.sound.Playback;
import org.fw.qq.plugins.video.Receiver;
import org.fw.qq.plugins.video.Sender;
import org.fw.utils.Config;
import org.fw.utils.ImageHelper;
import org.fw.utils.ProjectPath;
import org.fw.utils.SocketUtil;
import org.fw.utils.StringUtil;

public class QQTalkFrame extends JFrame {

	private static final long serialVersionUID = -6751143131744575886L;

	QQMainFrame owner;

	FramePanel panel;// �������
	ImageHelper imageHelper;
	Config basicCFG;// ȫ������
	JLabel headLabel;// ͷ��
	JLabel nameLabel;// �ǳ�
	JLabel signatureLabel;// ǩ��
	JLabel friendShow;// ����qq��
	JLabel myShow;// �ҵ�qq��
	JTextArea msgArea;// ������Ϣ��ʾ����
	JScrollPane msgScroll;// ������Ϣ�������
	JTextArea sayArea;// ������Ϣ���봰��
	JScrollPane sayScroll;// ������Ϣ�������
	OpaqueButton closeBtn;// �ر�
	OpaqueButton sendBtn;// ����
	JLabel adLabel;// �����Ϣ��ǩ
	OpaquePanel topToolPanel;// ͷ��������
	OpaqueButton videoTalkBtn;// ��Ƶ
	OpaqueButton voiceTalkBtn;// ����
	OpaqueButton sendFileBtn;// �����ļ�
	OpaqueButton talkGroupBtn;// ������
	OpaquePanel bottomToolPanel;// �ײ�������
	OpaqueButton fontBtn;// ����
	OpaqueButton faceBtn;// ����
	OpaqueButton magicFaceBtn;// ħ������
	OpaqueButton shakeBtn;// ���ڶ���
	OpaqueButton shakeOneBtn;// ��һ��
	OpaqueButton sendImgBtn;// ����ͼƬ
	OpaqueButton musicShareBtn;// ���ַ���
	OpaqueButton giftBtn;// ����
	OpaqueButton screenCutBtn;// ��Ļ��ͼ
	OpaqueButton sousouBtn;// ����
	OpaqueButton historyMsgBtn;// ��Ϣ��¼
	OpaquePanel btnPanel;// ��ť���
	OpaquePanel btnInnerPanel;// ��ť�����
	Image bgImage;// ����ͼƬ

	UDPTalkClient client;// ����
	Boolean isCall;// �Ƿ�����

	Socket voiceSocketClient;
	ServerSocket voiceSocketServer;
	Playback voicePlayer;
	Capture voiceCap;

	Sender videoSender;
	Receiver videoReceiver;

	// ��ͼ����
	private CutScreen cutScreen;
	public CutScreen getCutScreen() {
		return cutScreen;
	}
	// ���촰����ʵ��
	private static QQTalkFrame tf;
	public static QQTalkFrame getInstance() {
		if (tf == null) {
			tf = new QQTalkFrame("");
		}
		return tf;
	}
	String fNickName;
	String fRemarkName;
	String fSignature;
	String fStatue;
	String fIp;
	String fHeadInfo;
	String fIcon;
	String fQQNumber;
	int fReceiveMsgPort;// �Է�������Ϣ�˿�
	int fSendMsgPort;// �Է�������Ϣ�˿�
	int mSendMsgPort;// �Լ�������Ϣ�˿�
	int mReceiveMsgPort;// �Լ�������Ϣ�˿�
	String msg;
	String mNickName;
	String mSignature;
	String mStatue;
	String mIp;
	String mIcon;
	String mQQNumber;

	/*
	 * cfg��ʽ:
	 * �Է��ǳ�(0)���Է���ע(1)���Է�����ǩ��(2)���Է�״̬(3)���Է�ip(4),���ڵ�(������ʾ�ҵĺ���)(5),�Է�ͷ��(6)���Է�qq��(7)���Է�������Ϣ�˿�(8),�Լ�������Ϣ�˿�(9),��������(10),�Ƿ�����(11),
	 * �Լ����ǳ�(12),�Լ�����ǩ��(13),�Լ�״̬(14),�Լ�ip(15),�Լ�ͷ��(16),�Լ�qq��(17),�Է�������Ϣ�˿�(18),�Լ�������Ϣ�˿�(19)
	 * ��ˮ,��,������,1,127.0.0.1,�ҵĺ���,head.png,786074249,6000,6002,��ð�,true,����
	 */
	public QQTalkFrame(String cfg) {
		imageHelper = new ImageHelper();
		basicCFG = new Config(ProjectPath.getProjectPath() + "cfg/basic.properties");

		String[] cfgs = cfg.split(",");
		tf = this;
		fNickName = cfgs[0];
		fRemarkName = cfgs[1];
		fSignature = cfgs[2];
		fStatue = cfgs[3];
		fIp = cfgs[4];
		fHeadInfo = cfgs[5];
		fIcon = cfgs[6];
		fQQNumber = cfgs[7];
		fReceiveMsgPort = StringUtil.isNull(cfgs[8]) ? 0 : Integer.parseInt(cfgs[8]);
		mSendMsgPort = StringUtil.isNull(cfgs[9]) ? 0 : Integer.parseInt(cfgs[9]);
		msg = cfgs[10];
		mNickName = cfgs[12];
		mSignature = cfgs[13];
		mStatue = cfgs[14];
		mIp = cfgs[15];
		mIcon = cfgs[16];
		mQQNumber = cfgs[17];
		mSendMsgPort = StringUtil.isNull(cfgs[18]) ? 0 : Integer.parseInt(cfgs[18]);
		mReceiveMsgPort = StringUtil.isNull(cfgs[19]) ? 0 : Integer.parseInt(cfgs[19]);

		panel = new FramePanel(imageHelper.getFWImageIcon(fIcon));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 1, 5));
		panel.setParent(this);
		bgImage = imageHelper.getFWImage(basicCFG.getProperty("BackgroundImage", "skin/default/background/pink.jpg"));
		panel.setBackground(bgImage);
		panel.setTitle("<html>" + (fRemarkName.equals("") ? fNickName : fRemarkName) + "(" + fQQNumber + ")" + "<br/>" + fSignature + "</html>");
		panel.setEnnableMaxButton(true);
		// ͨ�Ų���
		try {
			isCall = new Boolean(cfgs[11]);
		} catch (Exception e) {
			isCall = false;
		}

		initServerClient();

		// ͷ��������
		topToolPanel = new OpaquePanel();
		topToolPanel.setLayout(new FlowLayout());
		videoTalkBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.vedioTalkImg", "skin/default/vediotalk.png")));
		videoTalkBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				startVideoTalk();
			}

			public void mouseEntered(MouseEvent e) {
				videoTalkBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				videoTalkBtn.setMouseOver(false);
			}
		});
		topToolPanel.add(videoTalkBtn);
		voiceTalkBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.voiceTalkImg", "skin/default/voicetalk.png")));
		voiceTalkBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				startVoiceTalk();
			}

			public void mouseEntered(MouseEvent e) {
				voiceTalkBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				voiceTalkBtn.setMouseOver(false);
			}
		});
		topToolPanel.add(voiceTalkBtn);
		sendFileBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.sendFileImg", "skin/default/sendFile.png")));
		sendFileBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				sendFileBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				sendFileBtn.setMouseOver(false);
			}
		});
		topToolPanel.add(sendFileBtn);
		talkGroupBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.talkGroupImg", "skin/default/talkgroup.png")));
		talkGroupBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				talkGroupBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				talkGroupBtn.setMouseOver(false);
			}
		});
		topToolPanel.add(talkGroupBtn);
		panel.addContainWithGridBag(topToolPanel, 0, 0, 2, 1, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, 1.0, 0);
		// ������Ϣ��ʾ����
		msgArea = new JTextArea();
		msgArea.setLineWrap(true);
		msgArea.setRows(15);
		try {
			msgArea.append(msg.trim());
		} catch (Exception e) {
			System.out.println("����");
		}
		msgScroll = new JScrollPane(msgArea);
		panel.addContainWithGridBag(msgScroll, 0, 1, 2, 7, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0, 1.0);
		// ����QQ��
		friendShow = new JLabel(imageHelper.getFWImageIcon("skin/qqshow/qqshow_g_1.png"));
		panel.addContainWithGridBag(friendShow, 2, 1, 1, 7, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, 0, 1.0);
		// �ײ�������
		bottomToolPanel = new OpaquePanel();
		panel.addContainWithGridBag(bottomToolPanel, 0, 8, 2, 1, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, 1.0, 0);
		fontBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.fontImg", "skin/default/font.png")));
		fontBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				fontBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				fontBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(fontBtn);
		faceBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.faceImg", "skin/default/face.png")));
		faceBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				faceBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				faceBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(faceBtn);
		magicFaceBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.magicFaceImg", "skin/default/magicface.png")));
		magicFaceBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				magicFaceBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				magicFaceBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(magicFaceBtn);
		shakeBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.shakeImg", "skin/default/shake.png")));
		shakeBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				shakeBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				shakeBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(shakeBtn);
		shakeOneBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.shakeOneImg", "skin/default/shakeone.png")));
		shakeOneBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				shakeOneBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				shakeOneBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(shakeOneBtn);
		sendImgBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.sendImgImg", "skin/default/sendImg.png")));
		sendImgBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				sendImgBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				sendImgBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(sendImgBtn);
		musicShareBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.musicShareImg", "skin/default/musicshare.png")));
		musicShareBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				musicShareBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				musicShareBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(musicShareBtn);
		giftBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.giftImg", "skin/default/gift.png")));
		giftBtn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				giftBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				giftBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(giftBtn);
		screenCutBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.screenCutImg", "skin/default/screencut.png")));
		screenCutBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				cutScreen = new CutScreen();
				cutScreen.setVisible(true);
			}

			public void mouseEntered(MouseEvent e) {
				screenCutBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				screenCutBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(screenCutBtn);
		sousouBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.sousouImg", "skin/default/sousou_t.png")));
		sousouBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				sousouBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				sousouBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(sousouBtn);
		historyMsgBtn = new OpaqueButton(imageHelper.getFWImageIcon(basicCFG.getProperty("QQTalkFrame.historyMsgImg", "skin/default/historymsg.png")));
		sousouBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				historyMsgBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				historyMsgBtn.setMouseOver(false);
			}
		});
		bottomToolPanel.add(historyMsgBtn);
		// ������Ϣ���봰��
		sayArea = new JTextArea();
		sayArea.setLineWrap(true);
		sayArea.setRows(10);
		sayScroll = new JScrollPane(sayArea);
		panel.addContainWithGridBag(sayScroll, 0, 9, 2, 3, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0, 1.0);
		// �ҵ�qq��
		myShow = new JLabel(imageHelper.getFWImageIcon("skin/qqshow/qqshow_b_1.png"));
		panel.addContainWithGridBag(myShow, 2, 8, 1, 5, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, 0, 1.0);

		btnPanel = new OpaquePanel();
		btnPanel.setLayout(new BorderLayout());

		adLabel = new JLabel("���ǹ��");
		btnPanel.add(adLabel, BorderLayout.WEST);

		// �رհ�ť
		closeBtn = new OpaqueButton("�ر�");
		closeBtn.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				QQTalkFrame.this.setVisible(false);
			}

			public void mouseEntered(MouseEvent e) {
				closeBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				closeBtn.setMouseOver(false);
			}

		});
		// ���Ͱ�ť
		sendBtn = new OpaqueButton("����");
		sendBtn.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				sendTalkMsg();

			}

			private void sendTalkMsg() {
				initServerClient();

				if (client != null) {
					Date now = new Date();
					DateFormat d1 = DateFormat.getDateInstance();
					// ��������
					String msg = "[" + mNickName + "," + "," + mSignature + "," + mStatue + "," + mIp + ",," + mIcon + "," + mQQNumber + "," + mReceiveMsgPort + "," + mSendMsgPort
							+ "]" + mNickName + " " + d1.format(now) + System.getProperty("line.separator") + sayArea.getText();
					System.out.println("�ͻ��˷�����Ϣ" + msg);
					if (client.sendMsg(msg)) {
						msgArea.append(System.getProperty("line.separator") + msg.substring(msg.indexOf("]") + 1));
					}
				}

				sayArea.setText("");
			}

			public void mouseEntered(MouseEvent e) {
				sendBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				sendBtn.setMouseOver(false);
			}

		});
		btnInnerPanel = new OpaquePanel();
		btnInnerPanel.setLayout(new FlowLayout());
		btnInnerPanel.add(closeBtn);
		btnInnerPanel.add(sendBtn);
		btnPanel.add(btnInnerPanel, BorderLayout.EAST);
		panel.addContainWithGridBag(btnPanel, 0, 12, 2, 1, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, 1.0, 0);
		this.getContentPane().add(panel);
		this.setUndecorated(true);
		this.setSize(535, 514);
		this.setTitle("��" + fNickName + "������");
	}

	private void initServerClient() {
		if (isCall) {
			if (client == null) {
				client = new UDPTalkClient(mSendMsgPort, fIp, fReceiveMsgPort);
				client.start();
			}
		}
	}

	private void startVideoTalk() {
		int videoPort = SocketUtil.getAvailablePort() + 6000;

		System.out.println("��ʼ������Ƶ����socket " + videoPort);
		String videoTalkCommand = "[videotalk]#request#" + fQQNumber + "#" + fIp + "#" + mQQNumber + "#" + mIp + "#" + videoPort + "#[" + mNickName + "," + "," + mSignature + ","
				+ mStatue + "," + mIp + ",," + mIcon + "," + mQQNumber + "," + mReceiveMsgPort + "," + mSendMsgPort + "]";

		String requestResult = SocketUtil.connectWithServer(videoTalkCommand);
		if (requestResult.equals("[videotalk]#server-transmit#accept")) {

			try {
				SessionAddress local = new SessionAddress(InetAddress.getByName(mIp), videoPort);
				SessionAddress target = new SessionAddress(InetAddress.getByName(fIp), videoPort + 10000);
				videoSender = new Sender(local, target);
//				SessionAddress local2 = new SessionAddress(InetAddress.getByName(mIp), videoPort + 10000+10);
//				SessionAddress target2 = new SessionAddress(InetAddress.getByName(fIp), videoPort + 10);
//				videoReceiver = new Receiver(local2, target2);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

		}
	}
	
	public void acceptVideoTalk(String requestIp, int requestPort) {
		if (JOptionPane.showConfirmDialog(this, "�Ƿ����" + requestIp + "����Ƶ����?") == JOptionPane.YES_OPTION) {

			try {
				System.out.println("�ͻ��˽�����Ƶ���󣬿�ʼ��Ƶ : " + requestIp + " " + requestPort + " " + mIp);
				SessionAddress local = new SessionAddress(InetAddress.getByName(mIp),requestPort+10000);
				SessionAddress target = new SessionAddress(InetAddress.getByName(requestIp),requestPort);		
				videoReceiver = new Receiver(local,target);
//				SessionAddress local2 = new SessionAddress(InetAddress.getByName(mIp),requestPort+10);
//				SessionAddress target2 = new SessionAddress(InetAddress.getByName(requestIp),requestPort+10000+10);		
//				videoSender = new Sender(local2,target2);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		String acceptVoiceCommand = "[videotalk]#requestaccpet#" + requestIp + "#" + requestPort + "#" + mQQNumber + "#" + mReceiveMsgPort;
		SocketUtil.connectWithServer(acceptVoiceCommand);
	}

	public void stopVoiceTalk() {
		if (voiceSocketClient != null) {
			try {
				voiceSocketClient.close();
				if (voiceCap != null)
					voiceCap.stop();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (voiceSocketServer != null) {
			try {
				voiceSocketServer.close();
				if (voicePlayer != null)
					voicePlayer.stop();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void acceptVoiceTalk(String requestIp, int requestPort) {
		if (JOptionPane.showConfirmDialog(this, "�Ƿ����" + requestIp + "����������?") == JOptionPane.YES_OPTION) {

			try {
				System.out.println("�ͻ��˽����������󣬿�ʼ���� : " + requestIp + " " + requestPort);

				voiceSocketClient = new Socket(requestIp.trim(), requestPort);
				voiceCap = new Capture(voiceSocketClient);
				voiceCap.start();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String acceptVoiceCommand = "[voicetalk]#requestaccpet#" + requestIp + "#" + requestPort + "#" + mQQNumber + "#" + mReceiveMsgPort;
		SocketUtil.connectWithServer(acceptVoiceCommand);

	}

	private void startVoiceTalk() {
		int voicePort = SocketUtil.getAvailablePort() + 10;
		try {

			System.out.println("��ʼ������������socket" + voicePort);
			String voiceTalkCommand = "[voicetalk]#request#" + fQQNumber + "#" + fIp + "#" + mQQNumber + "#" + mIp + "#" + voicePort + "#[" + mNickName + "," + "," + mSignature
					+ "," + mStatue + "," + mIp + ",," + mIcon + "," + mQQNumber + "," + mReceiveMsgPort + "," + mSendMsgPort + "]";

			String requestResult = SocketUtil.connectWithServer(voiceTalkCommand);
			if (requestResult.equals("[voicetalk]#server-transmit#accept")) {
				voiceSocketServer = new ServerSocket(voicePort);
				Socket cli = voiceSocketServer.accept();

				voicePlayer = new Playback(cli);
				voicePlayer.start();
				JOptionPane.showMessageDialog(this, "��ʼ����!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getTalkMsg() {
		return msgArea.getText();
	}
	public void displayTalkMsg(String msg) {
		msgArea.setText(msg);
	}

	public void appendTalkMsg(String msg) {
		msgArea.append(System.getProperty("line.separator") + msg);
	}

	public String getTalkQQF() {
		return fQQNumber;
	}
	public String getTalkQQM() {
		return mQQNumber;
	}
	public static void main(String[] args) {
		QQTalkFrame t = new QQTalkFrame("��ˮ,��,������,1,127.0.0.1,�ҵĺ���,head.png,786074249,6000,6002,��ð�");
		t.setVisible(true);
		t.setDefaultCloseOperation(3);
	}

	class UDPTalkClient extends Thread {

		DatagramSocket clientSocket;
		DatagramPacket clientPacket;
		InetAddress remoteHost;
		int remotePort;
		int localport;
		String serverIp;
		int serverPort;
		/**
		 * 
		 * @param localport
		 *            ���ض˿�
		 * @param serverIp
		 *            ������ip
		 * @param serverPort
		 *            �������˿�
		 */
		public UDPTalkClient(int localport, String serverIp, int serverPort) {
			this.localport = localport;
			this.serverIp = serverIp;
			this.serverPort = serverPort;
			try {
				remoteHost = InetAddress.getByName(serverIp);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			remotePort = serverPort;
			System.out.println("client" + localport + "|" + serverIp + "|" + serverPort);
			try {
				clientSocket = new DatagramSocket(localport);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		public boolean sendMsg(String msg) {
			try {
				byte[] dataBuf = new byte[msg.length()];

				System.out.println("�ͻ��˷��͵��˿�" + remotePort);
				dataBuf = (msg).getBytes();
				System.out.println("�ı�˿�" + remotePort);
				clientPacket = new DatagramPacket(dataBuf, dataBuf.length, remoteHost, remotePort);
				clientSocket.send(clientPacket);
			} catch (Exception e) {
				msgArea.append("��������ԭ��,���ղŷ��͵�[" + msg + "]ʧ����");
				return false;
			}
			return true;
		}

		public void run() {
			while (true) {
				try {
					byte[] dataBuf = new byte[512];
					clientPacket = new DatagramPacket(dataBuf, 512);
					clientSocket.receive(clientPacket);
					String datagram = new String(clientPacket.getData()).trim();
					System.out.println("�ͻ����յ�����:" + datagram);
					String headInfo = datagram.substring(1, datagram.indexOf("]"));
					String[] infos = headInfo.split(",");
					remoteHost = InetAddress.getByName(infos[4]);// �����ip
					remotePort = Integer.parseInt(infos[8]);// ����˶˿�
					System.out.println("���ڸı�˿�" + remotePort);
					msgArea.append(datagram.substring(datagram.indexOf("]") + 1));
				} catch (Exception e) {
					msgArea.append("Ŀǰֻ֧��˫��ͬʱ�������죡");
					break;
				}
			}
		}

		public void closeBind() {
			clientSocket.close();
			clientSocket = null;
		}

	}

	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (this == o) {
			return true;
		}
		if (o instanceof QQTalkFrame) {
			return ((QQTalkFrame) o).getTalkQQF().equals(getTalkQQF()) && ((QQTalkFrame) o).getTalkQQM().equals(getTalkQQM());
		}
		return false;
	}

	public QQMainFrame getOwner() {
		return owner;
	}

	public void setOwner(QQMainFrame owner) {
		this.owner = owner;
	}

	
}
