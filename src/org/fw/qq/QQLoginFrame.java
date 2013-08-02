package org.fw.qq;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.fw.CanDeleteJComboBox;
import org.fw.DropDownComponent;
import org.fw.FramePanel;
import org.fw.HyperLinkFLabel;
import org.fw.OpaqueButton;
import org.fw.StatusListJList;
import org.fw.image.ReSizeImageIcon;
import org.fw.utils.Config;
import org.fw.utils.ImageHelper;
import org.fw.utils.ProjectPath;

public class QQLoginFrame extends JFrame{

	private static final long serialVersionUID = 2217628931342766510L;

	FramePanel panel;// �������
	ImageHelper imageHelper;
	Config basicCFG;// ȫ������
	Config qqLoginFrameUICFG;// ȫ������
	Image bgImage;//����ͼƬ
	JLabel headLabel;//ͷ��
	CanDeleteJComboBox userNameJCB;//�û���
	JPasswordField passWord;//����
	HyperLinkFLabel registerLabel;//ע��
	HyperLinkFLabel findPasswrodLabel;//�һ�
	StatusListJList statusList;// ״̬�б�
	DropDownComponent statusDrop;// ״̬������ť
	JCheckBox rememberCB;//��ס����
	JCheckBox autoLoginCB;//�Զ���¼
	JPanel bottomPanel;//�ײ��������
	OpaqueButton setBtn;//����
	OpaqueButton loginBtn;//��¼
	
	DataInputStream din;
	DataOutputStream dout;
	Socket socket;
	
	static QQLoginFrame instance;
	
	public static QQLoginFrame getInstance(){
		if(instance == null){
			return new QQLoginFrame();
		}else{
			return instance;
		}
	}
	
	private QQLoginFrame(){
		instance = this;
		initParamter();
	}

	private void initParamter() {
		imageHelper = new ImageHelper();
		basicCFG = new Config(ProjectPath.getProjectPath()
				+ "cfg/basic.properties");
		qqLoginFrameUICFG = new Config(ProjectPath.getProjectPath()
				+ "cfg/QQLoginFrameUI.properties");
		panel = new FramePanel();
		panel.setIsMain(true);
		bgImage =imageHelper.getFWImage(
				basicCFG.getProperty("LoginBackgroundImage", "skin/default/background/loginbg.png"));
		panel.setBackground(bgImage);
		panel.setParent(this);
		panel.setTitle("  " + basicCFG.getProperty("AppTitle", "QQ2011"));
		Insets insets = new Insets(5,5,5,5);
		headLabel = new JLabel();
		headLabel.setIcon(new ReSizeImageIcon(qqLoginFrameUICFG.getProperty("QQLoginFrameUI.headLabelImg")).getReSizeImageIcon(80));
		panel.addContainWithGridBag(headLabel,0,0,2,3,GridBagConstraints.CENTER,GridBagConstraints.NONE,0,0,insets);
		
		insets = new Insets(2,5,0,5);
		
		userNameJCB = new CanDeleteJComboBox();
		userNameJCB.setPreferredSize(new Dimension(180,25));
		
		int Count = Integer.parseInt(qqLoginFrameUICFG.getProperty("QQLoginFrameUI.userNameJCBDataCount","0"));
		
		for(int i=0;i<Count;i++){
			String itemValue = qqLoginFrameUICFG.getProperty("QQLoginFrameUI.userNameJCBData_"+(i+1));
			String[] itemValues = itemValue.split("��");
			userNameJCB.addCanDeleteItem(itemValues[0],itemValues[1],itemValues[2]);
		}
		panel.addContainWithGridBag(userNameJCB,2,0,4,1,GridBagConstraints.CENTER,GridBagConstraints.NONE,0,0,insets);
		
		
		passWord = new JPasswordField();
		passWord.setBorder(null);
		passWord.setPreferredSize(new Dimension(180,25));
		panel.addContainWithGridBag(passWord,2,1,4,1,GridBagConstraints.CENTER,GridBagConstraints.NONE,0,0,insets);
		
		registerLabel = new HyperLinkFLabel("ע��","http://www.baidu.com");
		registerLabel.addMouseListener(registerLabel);
		findPasswrodLabel = new HyperLinkFLabel("�һ�","http://www.baidu.com");
		findPasswrodLabel.addMouseListener(findPasswrodLabel);
		panel.addContainWithGridBag(registerLabel,6,0,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE,0,0,insets);
		panel.addContainWithGridBag(findPasswrodLabel,6,1,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE,0,0,insets);
		
		insets = new Insets(2,0,0,0);
		statusList = new StatusListJList();
		statusList.addStatusItem("��������", basicCFG.getProperty(
				"StatusList.onlineImg", "skin/default/online.png"));
		statusList.addStatusItem("Q�Ұ�", basicCFG.getProperty(
				"StatusList.QmeImg", "skin/default/Qme.png"));
		statusList.addStatusItem("�뿪", basicCFG.getProperty(
				"StatusList.leaveImg", "skin/default/leave.png"));
		statusList.addStatusItem("æµ", basicCFG.getProperty(
				"StatusList.busyImg", "skin/default/busy.png"));
		statusList.addStatusItem("�������", basicCFG.getProperty(
				"StatusList.dontcallImg", "skin/default/dontcall.png"));
		statusList.addStatusItem("����", basicCFG.getProperty(
				"StatusList.hideImg", "skin/default/hide.png"));
		statusList.addStatusItem("����", basicCFG.getProperty(
				"StatusList.offlineImg", "skin/default/offline.png"));
		statusDrop = new DropDownComponent(statusList);
		statusList.addPropertyChangeListener("selectedStatus",
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						statusDrop.hidePopup();
						String selectedStatus = (String) evt.getNewValue();
						int value = Integer
								.parseInt(selectedStatus.split("��")[0]);
						ImageIcon icon = imageHelper.getFWImageIcon(selectedStatus
								.split("��")[1]);
						String status = selectedStatus.split("��")[2];
						
						setStatusDrop(value,icon.toString(),icon.toString(),status,status);
						statusDrop.setArrowValue(value, icon, status);
					}
				});
		panel.addContainWithGridBag(statusDrop,2,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE,0,0,insets);
		
		rememberCB = new JCheckBox("��ס����");
		rememberCB.setOpaque(false);
		panel.addContainWithGridBag(rememberCB,3,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE,0,0,insets);
		
		autoLoginCB = new JCheckBox("�Զ���¼");
		autoLoginCB.setOpaque(false);
		panel.addContainWithGridBag(autoLoginCB,4,2,1,1,GridBagConstraints.CENTER,GridBagConstraints.NONE,0,0,insets);
		
		bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		bottomPanel.setLayout(new BorderLayout());
		insets = new Insets(0,5,5,5);
		setBtn = new OpaqueButton("����");
		setBtn.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				
			}

			public void mouseEntered(MouseEvent e) {
				setBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				setBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}
			
		});
		bottomPanel.add(setBtn,BorderLayout.WEST);
		loginBtn = new OpaqueButton("��ȫ��¼");
		loginBtn.addMouseListener(new MouseListener(){

			@SuppressWarnings("deprecation")
			public void mouseClicked(MouseEvent e) {
				String number = userNameJCB.getSelectedItem().toString();
				String password = passWord.getText();
				if(number.trim().length()<=0){
					JOptionPane.showMessageDialog(null,"�û�������Ϊ��");
					return;
				}
				if(password.trim().length()<=0){
					JOptionPane.showMessageDialog(null,"���벻��Ϊ��");
					return;
				}
				instance.setVisible(false);
				QQLoginingFrame qq = QQLoginingFrame.getInstance(userNameJCB.getSelectedItem().toString(),password,instance);
				double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth() - qq.getWidth();
				double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - qq.getHeight();
				qq.setLocation((int)width/2,(int)height/2);
			}

			public void mouseEntered(MouseEvent e) {
				loginBtn.setMouseOver(true);
			}

			public void mouseExited(MouseEvent e) {
				loginBtn.setMouseOver(false);
			}

			public void mousePressed(MouseEvent e) {
				
			}

			public void mouseReleased(MouseEvent e) {
				
			}
			
		});
		bottomPanel.add(loginBtn,BorderLayout.EAST);
		panel.addBottomWithGridBag(bottomPanel,0,0,1,1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,1.0,0,insets);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(panel,BorderLayout.CENTER);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("QQ2011��¼");
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension wind  = this.getPreferredSize();
		this.setLocation((int)(screen.getWidth()-wind.getWidth())/2, (int)(screen.getHeight()-wind.getHeight())/2);

	}
	
	
	/**
	 * ����״̬
	 * @param code ״̬��
	 * @param icon ͼ��·��
	 * @param defaultIcon Ĭ��ͼ��·��
	 * @param statue ״̬
	 * @param statuetxt �ı�����ʾ��״̬
	 */
	private void setStatusDrop(int code, String icon, String defaultIcon, String statue, String statuetxt) {
		statusDrop.setArrowValue(code, imageHelper.getFWImageIcon(basicCFG.getProperty(
				icon, defaultIcon)), statue);
	}
	
	public static void main(String[] args){
		
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		QQLoginFrame login = new QQLoginFrame();
		login.setSize(339,225);
		login.setVisible(true);
		login.setDefaultCloseOperation(3);
		
	}

}
