package org.fw.qq;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.fw.ChangeBackgroundPanel;
import org.fw.DragAndDropJTree;
import org.fw.DropDownComponent;
import org.fw.FramePanel;
import org.fw.NormalBorder;
import org.fw.OpaqueButton;
import org.fw.OpaquePanel;
import org.fw.OpaqueTextField;
import org.fw.StatusListJList;
import org.fw.cellrender.FriendJTreeCellRenderer;
import org.fw.data.FriendJTreeItem;
import org.fw.image.ReSizeImageIcon;
import org.fw.utils.Config;
import org.fw.utils.ImageHelper;
import org.fw.utils.ProjectPath;
import org.fw.utils.SocketUtil;
import org.fw.utils.StringUtil;

public class QQMainFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 4080683254866865202L;

	FramePanel panel;// �������

	JPanel contentPanel;// �����������

	JPanel statuePanel;// ״̬���

	JPanel leftStatuePanel;// ״̬����������

	JPanel rightStatuePanel;// ״̬����Ҳ�����

	ChangeBackgroundPanel changeBackgroundPanel;// �ı䱳��Ƥ��

	JLabel headLabel;// ͷ��

	JLabel nameLabel;// �ǳ�

	OpaqueTextField statueTextField;// ״̬�ı���

	OpaqueTextField signatureTextField;// ����ǩ��

	StatusListJList statusList;// ״̬�б�

	DropDownComponent statusDrop;// ״̬������ť

	JLabel weatherLabel;// ����

	JPanel topToolPanel;// ͷ���������

	JPanel leftTopToolPanel;// ͷ����������������

	JPanel rightTopToolPanel;// ͷ����������ұ�����

	OpaqueButton qzoneBtn; // �ռ䰴ť

	OpaqueButton microBlogBtn;// ΢����ť

	OpaqueButton mailBtn;// �ʼ���ť

	OpaqueButton schoolFriendBtn;// У�Ѱ�ť

	OpaqueButton shopCartBtn;// ���ﳵ��ť

	OpaqueButton moneyBtn;// Ǯ����ť

	OpaqueButton sousouBtn;// ���Ѱ�ť

	OpaqueButton infoBtn;// ��Ѷ��ť

	OpaqueButton messageBoxBtn;// ��Ϣ���Ӱ�ť

	OpaqueButton setBGBtn;// ���ñ�����ɫ

	JTextField searchTextField;// ������

	JTabbedPane mainTabbedPane;// Tab���

	JScrollPane jScrollPaneFriend;// ���������

	DragAndDropJTree dadJTreeFriend;// ������

	DragAndDropJTree dadJTreeGroup;// Ⱥ��

	OpaquePanel contentJTree;// ����

	JLabel iconLabel;// ͷ��

	JLabel nickNameLabel;// �ǳ�

	OpaqueButton currentAppBtn;// ��ǰӦ��

	JLabel signatureLabel;// ����ǩ��

	List<OpaqueButton> applyList;// Ӧ���б�

	JPanel bottomPanel;// �ײ����

	JPanel leftBottomPanel;// �ײ�������

	JPanel rightBottomPanel;// �ײ��Ҳ�

	OpaqueButton menuBtn;// �˵���ť

	JPopupMenu mainPopupMenu;// ϵͳ���˵�

	JMenuItem jExitItem;// �˳�

	OpaqueButton phoneLifeBtn;// �ֻ����ť

	OpaqueButton qqGameBtn;// qq��Ϸ��ť

	OpaqueButton catBtn;// qq���ﰴť

	OpaqueButton musicBtn;// qq���ְ�ť

	OpaqueButton qqLiveBtn;// qqLive��ť

	OpaqueButton qqBrowerBtn;// qq�������ť

	OpaqueButton qqHomeManagerBtn;// qq��ȫ�ܼҰ�ť

	OpaqueButton sysBtn;// ϵͳ���ð�ť

	OpaqueButton hornBtn;// ���Ȱ�ť

	OpaqueButton safeBtn;// ��ȫ��ť

	OpaqueButton findBtn;// ���Ұ�ť

	OpaqueButton boxBtn;// Ӧ�ú���

	Config basicCFG;// ȫ������

	Config qqMainFrameUICFG;// ��������

	Image bgImage;// ���屳��ͼƬ

	ImageIcon menuBtnIcon, menuBtnMouseOnIcon;// �˵���ťͼ��

	Insets insets;// ���

	TrayIcon trayIcon = null;// ϵͳ����ͼ��

	ActionListener listener;// ϵͳ���̵����˵�������

	PopupMenu popupTrayIcon;// ϵͳ���̵����˵�

	MenuItem exitItem;// �˳���ť

	MenuItem openItem;// ������尴ť

	MenuItem onlineItem;// �������ϲ˵���

	MenuItem qmeItem;// Q�Ұɲ˵���

	MenuItem leaveItem;// �뿪�˵���

	MenuItem busyItem;// æµ�˵���

	MenuItem dontcallItem;// ������Ų˵���

	MenuItem hideItem;// ����˵���

	MenuItem offlineItem;// ���߲˵���

	ImageHelper imageHelper;

	DataInputStream din;

	DataOutputStream dout;

	Socket socket;// �������ͨ�ŵĽ���

	String ip = "127.0.0.1";

	static int port = 6000;

	String qqNumber;

	DatagramSocket serverSocket = null;

	DatagramPacket serverPacket;

	InetAddress remoteHost;

	int remotePort;

	ArrayList<QQTalkFrame> hasOpenTalkList = new ArrayList<QQTalkFrame>();// �Ѿ��򿪵����촰��

	public QQMainFrame() {
		initParamter("");
	}

	public QQMainFrame(String info) {
		initParamter(info);
	}

	private void initParamter(String info) {
		imageHelper = new ImageHelper();
		basicCFG = new Config(ProjectPath.getProjectPath() + "cfg/basic.properties");
		qqMainFrameUICFG = new Config(ProjectPath.getProjectPath() + "cfg/QQMainFrameUI.properties");

		String[] users = null;

		users = info.split("#");

		// �Լ�
		String[] self = users[0].split(",");
		qqNumber = self[7];
		ip = self[4];
		port = Integer.parseInt(self[8]);

		// UDP����Socket
		try {
			serverSocket = new DatagramSocket(port);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		Thread thread = new Thread(this);
		thread.start();

		System.out.println(self[0] + " �����˿�: " + port);
		// fix me ����Ļ���ʹ��ͬһ�������ļ��������⣬Ӧ���ǲ�ͬ�ĺ����Ӧ���õ��ļ���
		// ͷ��
		qqMainFrameUICFG.savePropertie("QQMainFrame.headLabelImg", "skin/default/" + self[6]);
		// �ǳ�
		qqMainFrameUICFG.savePropertie("QQMainFrame.nameLabel", self[0]);
		// ����ǩ��
		qqMainFrameUICFG.savePropertie("QQMainFrame.signatureTextField", self[2]);
		// �����б���ڵ�
		DefaultMutableTreeNode root = getFriendDefaultMutableTreeNode(self);
		// ����ڵ�
		ArrayList<DefaultMutableTreeNode> secroot = new ArrayList<DefaultMutableTreeNode>();

		String tmp_group_name = "";
		int tmp_member_count = -1;
		// ��ȡ����ӷ���
		for (int i = 1; i < users.length; i++) {
			String[] friend = users[i].split(",");
			if (tmp_group_name == "") {// ��һ��
				tmp_member_count++;
				tmp_group_name = friend[5];
				secroot.add(getFriendDefaultMutableTreeNode(friend));
				secroot.get(tmp_member_count).add(getFriendDefaultMutableTreeNode(friend));
			} else if (tmp_group_name.equals(friend[5])) {// ����ͬһ��
				secroot.get(tmp_member_count).add(getFriendDefaultMutableTreeNode(friend));
			} else {// ��ʼ�µ�һ��
				tmp_member_count++;
				tmp_group_name = friend[5];
				secroot.add(getFriendDefaultMutableTreeNode(friend));
				secroot.get(tmp_member_count).add(getFriendDefaultMutableTreeNode(friend));
			}
		}
		// ��������ӵ����ڵ���
		for (DefaultMutableTreeNode d : secroot) {
			root.add(d);
		}

		// �����
		panel = new FramePanel(true);
		panel.setParent(this);
		panel.setTitle("  " + basicCFG.getProperty("AppTitle", "QQ2011"));
		panel.setEnnableMaxButton(true);
		this.setTitle(self[0]);
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.CENTER);
		// ����ͼƬ
		bgImage = imageHelper.getFWImage(basicCFG.getProperty("BackgroundImage", "skin/default/bg.jpg"));
		panel.setBackground(bgImage);

		listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(exitItem)) {
					System.exit(0);
				} else if (e.getSource().equals(openItem)) {
					panel.getParent().setVisible(true);
					panel.getParent().setExtendedState(JFrame.NORMAL);
				} else if (e.getSource().equals(onlineItem)) {
					setStatusDrop(1, "StatusList.onlineImg", "skin/default/online.png", "��������", "��������");
				} else if (e.getSource().equals(qmeItem)) {
					setStatusDrop(2, "StatusList.QmeImg", "skin/default/Qme.png", "Q�Ұ�", "Q�Ұ�");
				} else if (e.getSource().equals(leaveItem)) {
					setStatusDrop(3, "StatusList.leaveImg", "skin/default/leave.png", "�뿪", "�뿪");
				} else if (e.getSource().equals(busyItem)) {
					setStatusDrop(4, "StatusList.busyImg", "skin/default/busy.png", "æµ", "æµ");
				} else if (e.getSource().equals(dontcallItem)) {
					setStatusDrop(5, "StatusList.dontcallImg", "skin/default/dontcall.png", "�������", "�������");
				} else if (e.getSource().equals(hideItem)) {
					setStatusDrop(6, "StatusList.hideImg", "skin/default/hide.png", "����", "����");
				} else if (e.getSource().equals(offlineItem)) {
					setStatusDrop(7, "StatusList.offlineImg", "skin/default/offline.png", "����", "����");
				} else if (e.getSource().equals(jExitItem)) {
					System.exit(0);
				}
			}
		};

		// �����������
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setOpaque(false);

		// =========״̬���
		insets = new Insets(0, 5, 0, 0);
		statuePanel = new JPanel();

		statuePanel.setLayout(new BorderLayout());
		statuePanel.setOpaque(false);

		// ״̬������
		leftStatuePanel = new JPanel();
		leftStatuePanel.setLayout(new GridBagLayout());
		leftStatuePanel.setOpaque(false);

		headLabel = new JLabel();
		headLabel.setIcon(new ReSizeImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.headLabelImg", "skin/default/head.png")).getReSizeImageIcon(40f));
		headLabel.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.headLabelTip", "�޸ĸ�������"));
		headLabel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		addWithGridBag(headLabel, leftStatuePanel, 0, 0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 0);
		statusList = new StatusListJList();
		statusList.addStatusItem("��������", basicCFG.getProperty("StatusList.onlineImg", "skin/default/online.png"));
		statusList.addStatusItem("Q�Ұ�", basicCFG.getProperty("StatusList.QmeImg", "skin/default/Qme.png"));
		statusList.addStatusItem("�뿪", basicCFG.getProperty("StatusList.leaveImg", "skin/default/leave.png"));
		statusList.addStatusItem("æµ", basicCFG.getProperty("StatusList.busyImg", "skin/default/busy.png"));
		statusList.addStatusItem("�������", basicCFG.getProperty("StatusList.dontcallImg", "skin/default/dontcall.png"));
		statusList.addStatusItem("����", basicCFG.getProperty("StatusList.hideImg", "skin/default/hide.png"));
		statusList.addStatusItem("����", basicCFG.getProperty("StatusList.offlineImg", "skin/default/offline.png"));
		statusDrop = new DropDownComponent(statusList);
		statusList.addPropertyChangeListener("selectedStatus", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				statusDrop.hidePopup();
				String selectedStatus = (String) evt.getNewValue();
				int value = Integer.parseInt(selectedStatus.split("��")

				[0]);
				ImageIcon icon = imageHelper.getFWImageIcon

				(selectedStatus.split("��")[1]);
				String status = selectedStatus.split("��")[2];
				setStatusDrop(value, icon.toString(), icon.toString

				(), status, status);
				// statusDrop.setArrowValue(value, icon, status);
				// statueTextField.setText("["+status+"]");
			}
		});
		addWithGridBag(statusDrop, leftStatuePanel, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);
		nameLabel = new JLabel(qqMainFrameUICFG.getProperty("QQMainFrame.nameLabel", "QQ�û�"));
		nameLabel.setFont(new Font("Dialog", Font.BOLD, 13));
		addWithGridBag(nameLabel, leftStatuePanel, 2, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);
		statueTextField = new OpaqueTextField(qqMainFrameUICFG.getProperty("QQMainFrame.statueTextField", "[��������]"));
		statueTextField.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.statueTextFieldTip", "��������"));
		statueTextField.setBorder(null);
		statueTextField.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				statueTextField.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				statueTextField.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		addWithGridBag(statueTextField, leftStatuePanel, 3, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);
		signatureTextField = new OpaqueTextField(qqMainFrameUICFG.getProperty("QQMainFrame.signatureTextField", "�༭����ǩ��"));
		signatureTextField.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.signatureTextFieldTip", "�༭����ǩ��"));
		signatureTextField.setBorder(null);
		signatureTextField.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
				signatureTextField.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				signatureTextField.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		addWithGridBag(signatureTextField, leftStatuePanel, 1, 1, GridBagConstraints.RELATIVE, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 1.0, 0);
		signatureTextField.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				signatureTextField.setOpaque(true);
			}

			public void focusLost(FocusEvent e) {
				signatureTextField.setOpaque(false);
			}
		});

		// ״̬����Ҳ�
		rightStatuePanel = new JPanel();
		rightStatuePanel.setOpaque(false);

		weatherLabel = new JLabel();
		weatherLabel.setIcon(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.weatherLabelImg", "skin/default/sun.png")));
		weatherLabel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		rightStatuePanel.add(weatherLabel);

		statuePanel.add(leftStatuePanel, BorderLayout.WEST);
		statuePanel.add(rightStatuePanel, BorderLayout.EAST);

		// ==========���״̬��嵽�����������
		panel.addContainWithGridBag(statuePanel, 0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.BOTH, 1.0, 0);

		// ͷ���������
		insets = new Insets(0, 0, 0, 0);
		topToolPanel = new JPanel();
		topToolPanel.setOpaque(false);
		topToolPanel.setLayout(new BorderLayout());
		leftTopToolPanel = new JPanel();
		leftTopToolPanel.setOpaque(false);
		rightTopToolPanel = new JPanel();
		rightTopToolPanel.setOpaque(false);
		topToolPanel.add(leftTopToolPanel, BorderLayout.WEST);
		topToolPanel.add(rightTopToolPanel, BorderLayout.EAST);
		qzoneBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.qzoneBtnImg", "skin/default/qzone.png")));
		qzoneBtn.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "qzoneBtn", "qzoneBtn", JOptionPane.ERROR_MESSAGE);
			}

			public void mouseEntered(MouseEvent e) {
				qzoneBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				qzoneBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		leftTopToolPanel.add(qzoneBtn);
		microBlogBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.microBlogBtnImg", "skin/default/microblog.png")));
		microBlogBtn.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "microBlogBtn", "microBlogBtn", JOptionPane.ERROR_MESSAGE);
			}

			public void mouseEntered(MouseEvent e) {
				microBlogBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				microBlogBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		leftTopToolPanel.add(microBlogBtn);
		mailBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.mailBtnImg", "skin/default/mail.png")));
		mailBtn.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "mailBtn", "mailBtn", JOptionPane.ERROR_MESSAGE);
			}

			public void mouseEntered(MouseEvent e) {
				mailBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				mailBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		leftTopToolPanel.add(mailBtn);
		schoolFriendBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.schoolFriendBtnImg", "skin/default/schoolfriend.png")));
		schoolFriendBtn.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "schoolFriendBtn", "schoolFriendBtn", JOptionPane.ERROR_MESSAGE);
			}

			public void mouseEntered(MouseEvent e) {
				schoolFriendBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				schoolFriendBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		leftTopToolPanel.add(schoolFriendBtn);
		shopCartBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.shopCartBtnImg", "skin/default/shopcart.png")));
		shopCartBtn.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "shopCartBtn", "shopCartBtn", JOptionPane.ERROR_MESSAGE);
			}

			public void mouseEntered(MouseEvent e) {
				shopCartBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				shopCartBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		leftTopToolPanel.add(shopCartBtn);
		moneyBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.moneyBtnImg", "skin/default/money.png")));
		moneyBtn.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "moneyBtn", "moneyBtn", JOptionPane.ERROR_MESSAGE);
			}

			public void mouseEntered(MouseEvent e) {
				moneyBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				moneyBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		leftTopToolPanel.add(moneyBtn);
		sousouBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.sousouBtnImg", "skin/default/sousou.png")));
		sousouBtn.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "sousouBtn", "sousouBtn", JOptionPane.ERROR_MESSAGE);
			}

			public void mouseEntered(MouseEvent e) {
				sousouBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				sousouBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		leftTopToolPanel.add(sousouBtn);
		infoBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.infoBtnImg", "skin/default/info.png")));
		infoBtn.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "infoBtn", "infoBtn", JOptionPane.ERROR_MESSAGE);
			}

			public void mouseEntered(MouseEvent e) {
				infoBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				infoBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		leftTopToolPanel.add(infoBtn);
		messageBoxBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.messageBoxBtnImg", "skin/default/messagebox.png")));
		messageBoxBtn.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "messageBox", "messageBox", JOptionPane.ERROR_MESSAGE);
			}

			public void mouseEntered(MouseEvent e) {
				messageBoxBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				messageBoxBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		rightTopToolPanel.add(messageBoxBtn);
		setBGBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.setBGBtnImg", "skin/default/setbg.png")));
		setBGBtn.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (changeBackgroundPanel == null) {
					changeBackgroundPanel = new ChangeBackgroundPanel(e.getLocationOnScreen(), setBGBtn.getHeight(),

					panel);
				} else {
					changeBackgroundPanel.setNewLocation(e.getLocationOnScreen());
				}
			}

			public void mouseEntered(MouseEvent e) {
				setBGBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				setBGBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		rightTopToolPanel.add(setBGBtn);
		// ==========���ͷ��������嵽�����������
		panel.addContainWithGridBag(topToolPanel, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0, 0);

		// ������
		searchTextField = new JTextField(qqMainFrameUICFG.getProperty("QQMainFrame.searchTextField", "������ϵ��"));
		searchTextField.setBorder(new NormalBorder(1, 1, Color.gray));
		// ===========��������򵽴����������
		panel.addContainWithGridBag(searchTextField, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0, 0);

		// tab����

		// ========�����б�

		// �޸�����+/-��
		UIManager.put("Tree.collapsedIcon", imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.FriendJTree.openIcon")));
		UIManager.put("Tree.expandedIcon", imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.FriendJTree.closeIcon")));
		// ȥ����
		UIManager.put("Tree.paintLines", false);

		dadJTreeFriend = new DragAndDropJTree();

		contentJTree = new OpaquePanel();
		contentJTree.setOpaque(false);
		iconLabel = new JLabel();// ͷ��
		iconLabel.setOpaque(false);
		nickNameLabel = new JLabel();// �ǳ�
		nickNameLabel.setOpaque(false);
		currentAppBtn = new OpaqueButton();// ��ǰӦ��
		signatureLabel = new JLabel();
		signatureLabel.setOpaque(false);
		applyList = null;

		// ��������ģ��
		DefaultTreeModel model = new DefaultTreeModel(root, true);
		dadJTreeFriend.setModel(model);
		// ������Ⱦ
		FriendJTreeCellRenderer jcell = new FriendJTreeCellRenderer(contentJTree, iconLabel, nickNameLabel, currentAppBtn, signatureLabel, applyList, dadJTreeFriend);
		dadJTreeFriend.setCellRenderer(jcell);

		// �����뵽�������
		jScrollPaneFriend = new JScrollPane();
		jScrollPaneFriend.getViewport().add(dadJTreeFriend, null);
		jScrollPaneFriend.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));
		dadJTreeFriend.setMinimumSize(new Dimension(this.getWidth(), this.getHeight()));

		dadJTreeFriend.expandAll();
		dadJTreeFriend.closeAll();
		// =========Ⱥ
		dadJTreeGroup = new DragAndDropJTree();

		mainTabbedPane = new JTabbedPane();
		mainTabbedPane.addTab("", imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.FriendTabTitleImg", "skin/default/friend.png")), jScrollPaneFriend,
				qqMainFrameUICFG.getProperty("QQMainFrame.FriendTabTitleTip", "�ҵĺ���"));

		// row�߶���Ϊ��֮��,������Զ�Ϊ��������ݷ�����ʵĸ߶�
		dadJTreeFriend.setRowHeight(0);

		dadJTreeFriend.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) dadJTreeFriend.getLastSelectedPathComponent();
					if (node.getLevel() == 2)
						beginTalk(node);
				}
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {

			}

		});
		// ==========���tab��嵽����
		panel.addContainWithGridBag(mainTabbedPane, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0, 5.0);

		// =============�ײ�
		bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BorderLayout());
		leftBottomPanel = new JPanel();
		leftBottomPanel.setLayout(new GridBagLayout());
		leftBottomPanel.setOpaque(false);
		menuBtnIcon = imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.menuBtnImg", "skin/default/menu_1.png"));
		menuBtnMouseOnIcon = imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.menuBtnMouseOnImg", "skin/default/menu_2.png"));
		menuBtn = new OpaqueButton(menuBtnIcon);
		menuBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.menuBtnTip", "���˵�"));
		menuBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {
				if (mainPopupMenu == null) {
					mainPopupMenu = new JPopupMenu();
					jExitItem = new JMenuItem("�˳�", imageHelper.getFWImageIcon

					(qqMainFrameUICFG.getProperty("QQMainFrame.jExitItemImg", "skin/default/jexititem.png")));
					jExitItem.addActionListener(listener);
					mainPopupMenu.add(jExitItem);
					mainPopupMenu.setLocation(menuBtn.getLocationOnScreen

					().x, menuBtn.getLocationOnScreen().y - 25);
					mainPopupMenu.setVisible(true);
				} else {
					mainPopupMenu.setLocation(menuBtn.getLocationOnScreen

					().x, menuBtn.getLocationOnScreen().y - 25);
					mainPopupMenu.setVisible(true);
				}
			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				menuBtn.setIcon(menuBtnMouseOnIcon);
				menuBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				menuBtn.setIcon(menuBtnIcon);
				menuBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(menuBtn, leftBottomPanel, 0, 0, 1, 2, GridBagConstraints.SOUTH, GridBagConstraints.NONE, 2.0, 2.0);
		phoneLifeBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.phoneLifeBtnImg", "skin/default/phonelife.png")));
		phoneLifeBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.phoneLifeBtnTip", "�ֻ�����"));
		phoneLifeBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				phoneLifeBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				phoneLifeBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(phoneLifeBtn, leftBottomPanel, 1, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		qqGameBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.qqGameBtnImg",

		"skin/default/qqgame.png")));
		qqGameBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.qqGameBtnTip", "QQ��Ϸ"));
		qqGameBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				qqGameBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				qqGameBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(qqGameBtn, leftBottomPanel, 2, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		catBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.catBtnImg", "skin/default/cat.png")));
		catBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.catBtnTip", "QQ����"));
		catBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {
			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				catBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				catBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(catBtn, leftBottomPanel, 3, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		musicBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.musicBtnImg", "skin/default/music.png")));
		musicBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.musicBtnTip", "QQ����"));
		musicBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				musicBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				musicBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(musicBtn, leftBottomPanel, 4, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		qqLiveBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.qqLiveBtnImg",

		"skin/default/qqlive.png")));
		qqLiveBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.qqLiveBtnTip", "QQLive"));
		qqLiveBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				qqLiveBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				qqLiveBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(qqLiveBtn, leftBottomPanel, 5, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		qqBrowerBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.qqBrowerBtnImg", "skin/default/qqbrower.png")));
		qqBrowerBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.qqBrowerBtnTip", "QQ�����"));
		qqBrowerBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				qqBrowerBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				qqBrowerBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(qqBrowerBtn, leftBottomPanel, 6, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		qqHomeManagerBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.qqHomeManagerBtnImg", "skin/default/qqhomemanager.png")));
		qqHomeManagerBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.qqHomeManagerBtnTip", "QQ��ȫ�ܼ�"));
		qqHomeManagerBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				qqHomeManagerBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				qqHomeManagerBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(qqHomeManagerBtn, leftBottomPanel, 7, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		sysBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.sysBtnImg", "skin/default/sys.png")));
		sysBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.sysBtnTip", "��ϵͳ����"));
		sysBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				sysBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				sysBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(sysBtn, leftBottomPanel, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		hornBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.hornBtnImg", "skin/default/horn.png")));
		hornBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.hornBtnTip", "����Ϣ������"));
		hornBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				hornBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				hornBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(hornBtn, leftBottomPanel, 2, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		safeBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.safeBtnImg", "skin/default/safe.png")));
		safeBtn.setText("��ȫ");
		safeBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.safeBtnTip", "�򿪰�ȫ��ͨ"));
		safeBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				safeBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				safeBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(safeBtn, leftBottomPanel, 3, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		findBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.findBtnImg", "skin/default/find.png")));
		findBtn.setText("����");
		findBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.findBtnTip", "������ϵ��"));
		findBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {

			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				findBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				findBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		addWithGridBag(findBtn, leftBottomPanel, 5, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);

		rightBottomPanel = new JPanel();
		rightBottomPanel.setOpaque(false);
		boxBtn = new OpaqueButton(imageHelper.getFWImageIcon(qqMainFrameUICFG.getProperty("QQMainFrame.boxBtnImg", "skin/default/box.png")));
		boxBtn.setToolTipText(qqMainFrameUICFG.getProperty("QQMainFrame.boxBtnTip", "Ӧ�ú���"));
		boxBtn.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent mouseevent) {
				try {
					socket = new Socket();
					socket.connect(new InetSocketAddress("127.0.0.1", 11111));

					din = new DataInputStream(socket.getInputStream());
					dout = new DataOutputStream(socket.getOutputStream());
					dout.writeUTF("bye");
					dout.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent mouseevent) {
				boxBtn.setMouseOver(true);
			}

			@Override
			public void mouseExited(MouseEvent mouseevent) {
				boxBtn.setMouseOver(false);
			}

			@Override
			public void mousePressed(MouseEvent mouseevent) {

			}

			@Override
			public void mouseReleased(MouseEvent mouseevent) {

			}

		});
		rightBottomPanel.add(boxBtn);

		bottomPanel.add(leftBottomPanel, BorderLayout.WEST);
		bottomPanel.add(rightBottomPanel, BorderLayout.EAST);
		panel.addBottomWithGridBag(bottomPanel, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 1.0, 1.0);

		// ==========ϵͳ����

		if (SystemTray.isSupported()) {

			SystemTray tray = SystemTray.getSystemTray();

			Image image = imageHelper.getFWImage(basicCFG.getProperty("QQSystemTrayIcon", "skin/default/qqtrayicon.png"));

			popupTrayIcon = new PopupMenu();
			onlineItem = new MenuItem("��������");
			onlineItem.addActionListener(listener);
			qmeItem = new MenuItem("Q�Ұ�");
			qmeItem.addActionListener(listener);
			leaveItem = new MenuItem("�뿪");
			leaveItem.addActionListener(listener);
			busyItem = new MenuItem("æµ");
			busyItem.addActionListener(listener);
			dontcallItem = new MenuItem("�������");
			dontcallItem.addActionListener(listener);
			hideItem = new MenuItem("����");
			hideItem.addActionListener(listener);
			offlineItem = new MenuItem("����");
			offlineItem.addActionListener(listener);
			exitItem = new MenuItem("�˳�");
			exitItem.addActionListener(listener);
			openItem = new MenuItem("�������");
			openItem.addActionListener(listener);
			popupTrayIcon.add(onlineItem);
			popupTrayIcon.add(openItem);
			popupTrayIcon.add(qmeItem);
			popupTrayIcon.add(leaveItem);
			popupTrayIcon.add(dontcallItem);
			popupTrayIcon.add(hideItem);
			popupTrayIcon.add(offlineItem);
			popupTrayIcon.addSeparator();
			popupTrayIcon.add(openItem);
			popupTrayIcon.add(exitItem);
			trayIcon = new TrayIcon(image, basicCFG.getProperty("QQSystemTrayTip", "QQ"), popupTrayIcon);
			trayIcon.addActionListener(listener);

			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println(e);
			}
		} else {
			JOptionPane.showMessageDialog(null, "�Բ���,����ϵͳ��֧��ϵͳ����");
		}

		this.getContentPane().add(panel);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);
		this.pack();
		this.setSize(260, 610);
	}

	private void beginTalk(DefaultMutableTreeNode node) {
		if (node == null || node.getUserObject() == null)
			return;

		String getTalkPortCommand = "[talk]#getip#" + ((FriendJTreeItem) node.getUserObject()).getQqNumber();
		String talkPortInfo = SocketUtil.connectWithServer(getTalkPortCommand);
		String talkIp = talkPortInfo.split("#")[2];
		String talkPort = talkPortInfo.split("#")[3];
		int talkSelfPort = SocketUtil.getAvailablePort();
		String initInfo = ((FriendJTreeItem) node.getUserObject()).getNickName() + "," + ((FriendJTreeItem) node.getUserObject()).getRemarkName() + ","
				+ ((FriendJTreeItem) node.getUserObject()).getSignature() + "," + ((FriendJTreeItem) node.getUserObject()).getStatue() + "," + talkIp + ","
				+ ((FriendJTreeItem) node.getUserObject()).getHeadInfo() + "," + ((FriendJTreeItem) node.getUserObject()).getIcon() + ","
				+ ((FriendJTreeItem) node.getUserObject()).getQqNumber() + "," + talkPort + "," + talkSelfPort + "," + "" + "," + "true," + nameLabel.getText() + ","
				+ signatureTextField.getText() + "," + "1," + ip + "," + headLabel.getIcon() + "," + qqNumber + "," + "" + "," + port;
		if(StringUtil.isNull(talkPort)){
			JOptionPane.showMessageDialog(this,((FriendJTreeItem) node.getUserObject()).getNickName()+ "������,��������!");
			return;
		}
		System.out.println("��ʼ��" + ((FriendJTreeItem) node.getUserObject()).getNickName() + "�ڶ˿�" + talkPort + "����");
		QQTalkFrame talk = new QQTalkFrame(initInfo);
		talk.setOwner(QQMainFrame.this);
		if (!hasOpenTalkList.contains(talk)) {
			hasOpenTalkList.add(talk);
			talk.setVisible(true);
		} else {
			QQTalkFrame existTalk = getTalkFrame(talk.getTalkQQF(), talk.getTalkQQM());
			existTalk.setVisible(true);
		}

	}

	private DefaultMutableTreeNode getFriendDefaultMutableTreeNode(String[] self) {
		FriendJTreeItem item = new FriendJTreeItem(self[5], 2, "skin/default/head/" + self[6], self[0], self[1], self[7], "", self[2], null, "", Integer.parseInt(self[3]),
				self[4], Integer.parseInt(self[8]));
		return new DefaultMutableTreeNode(item);
	}

	/**
	 * ���ֲ�������
	 * 
	 * @param comp
	 * @param cont
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param anchor
	 * @param fill
	 * @param weightx
	 * @param weighty
	 */
	private void addWithGridBag(Component comp, Container cont, int x, int y, int width, int height, int anchor, int fill, double weightx, double weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.insets = insets;
		gbc.anchor = anchor;
		gbc.fill = fill;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		cont.add(comp, gbc);
	}

	/**
	 * ����״̬
	 * 
	 * @param code
	 *            ״̬��
	 * @param icon
	 *            ͼ��·��
	 * @param defaultIcon
	 *            Ĭ��ͼ��·��
	 * @param statue
	 *            ״̬
	 * @param statuetxt
	 *            �ı�����ʾ��״̬
	 */
	private void setStatusDrop(int code, String icon, String defaultIcon, String statue, String

	statuetxt) {
		statusDrop.setArrowValue(code, imageHelper.getFWImageIcon(basicCFG.getProperty(icon, defaultIcon)), statue);
		statueTextField.setText("[" + statuetxt + "]");
	}

	@Override
	public void run() {

		if (serverSocket == null)
			return;

		while (true) {
			try {
				byte[] dataBuf = new byte[512];
				serverPacket = new DatagramPacket(dataBuf, 512);
				serverSocket.receive(serverPacket);
				remoteHost = serverPacket.getAddress();
				remotePort = serverPacket.getPort();

			} catch (Exception e) {

			}

			String datagram = new String(serverPacket.getData()).trim();
			String requestVoiceTalkIp = null;
			int requestVoicePort = 0;
			String requestVideoTalkIp = null;
			int requestVideoPort = 0;
			if (datagram.startsWith("[voicetalk]#server-transmit#")) {
				System.out.println("��鵽��������");
				requestVoiceTalkIp = datagram.split("#")[2];
				requestVoicePort = Integer.parseInt(datagram.split("#")[3]);
				datagram = datagram.substring(datagram.indexOf("#[") + 1);
			} else if (datagram.startsWith("[videotalk]#server-transmit#")) {
				System.out.println("��鵽��Ƶ����");
				requestVideoTalkIp = datagram.split("#")[2];
				requestVideoPort = Integer.parseInt(datagram.split("#")[3]);
				datagram = datagram.substring(datagram.indexOf("#[") + 1);
			}

			System.out.println("�յ��������ݰ�:" + datagram);
			String headInfo = datagram.substring(1, datagram.indexOf("]"));
			String[] infos = headInfo.split(",");
			datagram = datagram.substring(datagram.indexOf("]") + 1);

			String info = infos[0] + "," + infos[1] + "," + infos[2] + "," + infos[3] + "," + infos[4] + "," + infos[5] + "," + infos[6] + "," + infos[7] + "," + infos[8] + ","
					+ SocketUtil.getAvailablePort() + "," + datagram + ",true," + nameLabel.getText() + "," + signatureTextField.getText() + ",1," + ip + "," + headLabel.getIcon()
					+ "," + qqNumber + "," + infos[9] + "," + port;
			System.out.println("���������Ϣ:" + info);

			if (isTalkExist(infos[7], qqNumber)) {
				if ((getTalkFrame(infos[7], qqNumber) != null)) {
					getTalkFrame(infos[7], qqNumber).appendTalkMsg(datagram);
					getTalkFrame(infos[7], qqNumber).setVisible(true);
					if (!StringUtil.isNull(requestVoiceTalkIp) && requestVoicePort != 0) {
						getTalkFrame(infos[7], qqNumber).acceptVoiceTalk(requestVoiceTalkIp, requestVoicePort);
					}
					if (!StringUtil.isNull(requestVideoTalkIp) && requestVideoPort != 0) {
						getTalkFrame(infos[7], qqNumber).acceptVideoTalk(requestVideoTalkIp, requestVideoPort);
					}
				}
			} else {
				QQTalkFrame talk2 = new QQTalkFrame(info);
				talk2.setOwner(QQMainFrame.this);
				talk2.setVisible(true);
				talk2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				if (!getHasOpenTalkList().contains(talk2)) {
					getHasOpenTalkList().add(talk2);
				}
				if (!StringUtil.isNull(requestVoiceTalkIp) && requestVoicePort != 0) {
					talk2.acceptVoiceTalk(requestVoiceTalkIp, requestVoicePort);
				}
				if (!StringUtil.isNull(requestVideoTalkIp) && requestVideoPort != 0) {
					talk2.acceptVideoTalk(requestVideoTalkIp, requestVideoPort);
				}
			}
		}
	}

	public ArrayList<QQTalkFrame> getHasOpenTalkList() {
		return hasOpenTalkList;
	}

	public QQTalkFrame getTalkFrame(String fQQ, String mQQ) {
		for (QQTalkFrame talk : getHasOpenTalkList()) {
			if (talk.getTalkQQF().equals(fQQ) && talk.getTalkQQM().equals(mQQ)) {
				return talk;
			}
		}
		return null;
	}

	public boolean isTalkExist(String fQQ, String mQQ) {
		for (QQTalkFrame talk : getHasOpenTalkList()) {
			if (talk.getTalkQQF().equals(fQQ) && talk.getTalkQQM().equals(mQQ)) {
				System.out.println("�������촰��");
				return true;
			}
		}
		return false;
	}
}
