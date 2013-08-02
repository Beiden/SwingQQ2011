package org.fw.test;

import java.net.*;

import java.awt.event.*;

import java.awt.*;

import java.io.*;

import javax.swing.*;

public class MutilCast {

	public static void main(String[] args)

	{

		MulticastSocket s = null;

		InetAddress group = null;

		JPanel northPanel = new JPanel();

		JPanel southPanel = new JPanel();

		JPanel namePanel = new JPanel(new FlowLayout());

		JLabel sendLabel = new JLabel("�������ݣ�");

		JLabel nameChangeLabel = new JLabel("���Լ�������֣�");

		final JTextField nameSpace = new JTextField(20);

		final JTextArea messageArea = new JTextArea("", 20, 20);

		final JTextField sendField = new JTextField(30);

		JScrollPane message = new JScrollPane(messageArea);

		JButton sendButton = new JButton("����");

		JButton saveButton = new JButton("�����¼");

		JButton clearUpButton = new JButton("������");

		Box centerBox = Box.createVerticalBox();

		namePanel.add(nameChangeLabel);

		namePanel.add(nameSpace);

		centerBox.add(namePanel);

		centerBox.add(message);

		// ʵ���鲥���ݴ��͵�������Ϣ

		try {

			group = InetAddress.getByName("228.9.6.18");

		} catch (UnknownHostException e1) {

			System.out.println("�鲥��ַ��ʧ��");

		}

		try {

			s = new MulticastSocket(6789);

		} catch (IOException e1) {

			System.out.println("�鲥��ַ����ʧ��");

		}

		try {

			s.joinGroup(group);

		} catch (IOException e1) {

			System.out.println("�鲥��ַ����ʧ��");

		}

		// endʵ���鲥���ݴ��͵�������Ϣ

		// �����͡���ťʵ����Ϣ���ܵķ��Ͳ���

		class SendMsg implements ActionListener

		{

			String msg = null;

			MulticastSocket s = null;

			InetAddress group = null;

			public SendMsg(MulticastSocket s, InetAddress group)

			{

				this.s = s;

				this.group = group;

			}

			public void actionPerformed(ActionEvent e)

			{

				try

				{

					// �������Ϊ�� ������ʾ��Ϣ

					if (nameSpace.getText().isEmpty())

						new JOptionPane().showMessageDialog(null, "��һ��Ҫȡ������");

					else

					{

						// ������ֲ�Ϊ�� ���������ӵ����ݱ�ͷ

						msg = (nameSpace.getText() + "˵��" + sendField.getText());

						DatagramPacket data =

						new DatagramPacket(msg.getBytes(),
								msg.getBytes().length, group, 6789);

						s.send(data);

					}
				}

				catch (IOException e1) {

					System.out.println("����ʧ��");

				}

			}

		}

		// ʵ�����ݱ��Ľ����߳�

		class recevMsg extends Thread

		{

			MulticastSocket s = null;

			public recevMsg(MulticastSocket s) {

				this.s = s;

			};

			public void run()

			{

				byte[] buf = new byte[100];

				DatagramPacket recv = new DatagramPacket(buf, buf.length);

				try

				{

					while (true)

					{

						s.receive(recv);

						String str = new String(recv.getData());

						String line = System.getProperty("line.separator");

						messageArea.append(str);

						messageArea.append(line);

					}

				}

				catch (IOException e1)

				{

					System.out.println("����ʧ��");

				}

			}

		}

		// �����¼�ı��� �����ڵ�ǰλ���µ�"Note.txt"�ļ���

		class SaveMsg implements ActionListener

		{

			String msg = null;

			String line = System.getProperty("line.separator");

			public void actionPerformed(ActionEvent e)

			{

				try

				{

					msg = messageArea.getText();

					FileOutputStream Note = new FileOutputStream("Note.txt");

					messageArea.append("��¼�Ѿ�������Note.txt");

					Note.write(msg.getBytes());

					messageArea.append(line);

					Note.close();

				}

				catch (IOException e1) {

					System.out.println("����ʧ��");

				}

			}

		}

		// �������ϵ������¼

		class ClearMsg implements ActionListener

		{

			public void actionPerformed(ActionEvent e)

			{

				try

				{

					messageArea.setText("");

				}

				catch (Exception e1) {

					System.out.println("���ʧ��");

				}

			}

		}

		JFrame mutilCastFrame = new JFrame("�鲥���칤��");

		northPanel.add(sendLabel);

		northPanel.add(sendField);

		northPanel.add(sendButton);

		southPanel.add(saveButton);

		southPanel.add(clearUpButton);

		mutilCastFrame.getContentPane().add(northPanel, "North");

		mutilCastFrame.getContentPane().add(southPanel, "South");

		mutilCastFrame.getContentPane().add(centerBox, "Center");

		mutilCastFrame.setDefaultCloseOperation(mutilCastFrame.EXIT_ON_CLOSE);

		sendButton.addActionListener(new SendMsg(s, group));

		saveButton.addActionListener(new SaveMsg());

		clearUpButton.addActionListener(new ClearMsg());

		mutilCastFrame.setSize(500, 500);

		mutilCastFrame.setLocation(200, 200);
		mutilCastFrame.show();

		recevMsg r = new recevMsg(s);

		r.start();

	}

}
