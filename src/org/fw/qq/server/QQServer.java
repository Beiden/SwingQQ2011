package org.fw.qq.server;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket ;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.fw.OpaqueButton;
import org.fw.utils.HibernateSessionFactory;
import org.hibernate.Session;

public class QQServer extends JFrame implements Runnable{

	private static final long serialVersionUID = 7239140109059498834L;
	
	protected ServerSocket  serverSocket;//�����߳�
	protected Socket socket;//����ͨ���߳�
	protected int serverNum = 0;//���е��߳���
	protected int maxNum = 10;
	protected int port;//�����˿�
	protected JLabel statueLabel;//״̬��Ϣ
	protected JTextArea infoArea;//��Ϣ����
	protected OpaqueButton beginButton;//������ť
	Thread thread ;
	
	public QQServer(int port,int maxNum){
		this.port = port;
		this.maxNum = maxNum;
		thread = new Thread(this);
		statueLabel = new JLabel("δ����");
		infoArea = new JTextArea();
		beginButton = new OpaqueButton("����");
		beginButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				thread.start();
				statueLabel.setText("������");
				Session session  = HibernateSessionFactory.getSession();
			}
		});
		this.setLayout(new BorderLayout());
		this.add(statueLabel,BorderLayout.NORTH);
		this.add(infoArea,BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		bottom.add(beginButton);
		bottom.setOpaque(false);
		this.add(bottom,BorderLayout.SOUTH);
		this.pack();
		this.setSize(180,400);
		this.setVisible(true);
		this.setDefaultCloseOperation(3);
		this.setTitle("QQ2011���׷�������");
	}

	
	
	public static void main(String[] args){
		QQServer server = new QQServer(11111,10);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth() - server.getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight() - server.getHeight();
		server.setLocation((int)width/2,(int)height/2);
	}



	public void run() {
		try {
			serverSocket = new ServerSocket(port,10);
			infoArea.append("��������ʼ��"+port+"�˿��ϼ���...\n");

			boolean flag = true;
			while(flag){
				socket = serverSocket.accept();
				InetAddress clientAddress=socket.getInetAddress();
				infoArea.append("����:"+clientAddress+"\n");
				ServerThread thread = new ServerThread(socket,serverNum,infoArea);
				thread.start();
				serverNum++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
