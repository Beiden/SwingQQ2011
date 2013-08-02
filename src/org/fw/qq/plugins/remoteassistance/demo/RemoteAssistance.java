package org.fw.qq.plugins.remoteassistance.demo;

import java.awt.AWTException;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import org.fw.qq.plugins.remoteassistance.Client;
import org.fw.qq.plugins.remoteassistance.Server;
import org.fw.qq.plugins.remoteassistance.StringUtil;

public class RemoteAssistance extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -502914245090231502L;
	private JRadioButton serverJRB;
	private JRadioButton clientJRB;
	private ButtonGroup btnGroup;
	private JLabel infoJL;
	private JButton okBtn;

	public RemoteAssistance() {

		initComponent();
		registerListeners();

	}

	private void registerListeners() {
		okBtn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (serverJRB.isSelected()) {
					createServer();
				} else if (clientJRB.isSelected()) {
					createClient();
				}
			}

			private void createClient() {
				JFrame frame = new JFrame("Զ�̿���");
				String ip = JOptionPane.showInputDialog("������Զ������IP");
				if ("null".equals(ip)) {
					System.exit(0);
				}
				while (ip.isEmpty()) {
					ip = JOptionPane.showInputDialog("������Զ������IP");
				}
				while (!StringUtil.isValidIP(ip)) {
					ip = JOptionPane.showInputDialog("������Ϸ�IP");
				}

				String port = JOptionPane.showInputDialog("����Զ�����Ӷ˿�");
				if ("null".equals(port)) {
					System.exit(0);
				}
				while (port.isEmpty()) {
					port = JOptionPane.showInputDialog("������Զ�����Ӷ˿�");
				}
				while (!StringUtil.isValidPort(port)) {
					port = JOptionPane.showInputDialog("������Ϸ��˿�");
				}
				JOptionPane.showMessageDialog(null, "��ʼԶ������!");

				Client client = new Client(ip, Integer.parseInt(port));
				JScrollPane jsp = new JScrollPane(client, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				frame.add(jsp);
				frame.setDefaultCloseOperation(3);
				frame.setSize(800, 600);
				frame.setVisible(true);
			}

			private void createServer() {
				Server server;
				try {

					String ip = JOptionPane.showInputDialog("�����뱾��IP");
					if ("null".equals(ip)) {
						System.exit(0);
					}
					while (ip.isEmpty()) {
						ip = JOptionPane.showInputDialog("�����뱾��IP");
					}
					while (!StringUtil.isValidIP(ip)) {
						ip = JOptionPane.showInputDialog("������Ϸ�IP");
					}

					String port = JOptionPane.showInputDialog("�����뱾�ض˿�");
					if ("null".equals(port)) {
						System.exit(0);
					}
					while (port.isEmpty()) {
						port = JOptionPane.showInputDialog("�����뱾�ض˿�");
					}
					while (!StringUtil.isValidPort(port)) {
						port = JOptionPane.showInputDialog("������Ϸ��˿�");
					}
					while (!StringUtil.isPortUsed(Integer.parseInt(port))) {
						port = JOptionPane.showInputDialog("�˿ڱ�ռ��,�����������˿�");
					}
					server = new Server(ip, Integer.parseInt(port));
					server.start();

					JFrame frame = new JFrame("Զ�����ӱ����ƶ�");
					frame.add(server);
					frame.setSize(300, 85);
					frame.setDefaultCloseOperation(3);
					frame.setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (AWTException e) {
					e.printStackTrace();
				}
			}

		});
	}

	private void initComponent() {
		infoJL = new JLabel("��ѡ��");
		btnGroup = new ButtonGroup();
		serverJRB = new JRadioButton("��������");
		serverJRB.setSelected(true);
		clientJRB = new JRadioButton("���ƶ�");
		btnGroup.add(serverJRB);
		btnGroup.add(clientJRB);
		okBtn = new JButton("ȷ��");
		setLayout(new FlowLayout());
		this.add(infoJL);
		this.add(serverJRB);
		this.add(clientJRB);
		this.add(okBtn);
	}

	public static void main(String[] args) {
		RemoteAssistance demo = new RemoteAssistance();
		demo.setTitle("Զ��Э��");
		demo.setDefaultCloseOperation(3);
		demo.setSize(320, 240);
		demo.setVisible(true);
	}

}
