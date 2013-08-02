package org.fw.qq.plugins.udp;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 
 * <br/>
 * Title: UDPClientA.java<br/>
 * E-Mail: 176291935@qq.com<br/>
 * QQ: 176291935<br/>
 * Http: iaiai.iteye.com<br/>
 * Create time: 2013-1-29 ����11:11:56<br/>
 * <br/>
 * @author ����
 * @version 0.0.1
 */
public class UDPClientA {

    public static void main(String[] args) {
        try {
            // ��server��������
            SocketAddress target = new InetSocketAddress("192.168.98.130", 2008);
            DatagramSocket client = new DatagramSocket();
            String message = "I am UPDClinetA 192.168.85.132";
            byte[] sendbuf = message.getBytes();
            DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
            client.send(pack);
            // ��������Ļظ�,���ܲ���server�ظ��ģ��п�������UPDClientB��������
            receive(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //������������
    private static void receive(DatagramSocket client) {
        try {
            for (;;) {
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                client.receive(packet);
                String receiveMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println(receiveMessage);
                int port = packet.getPort();
                InetAddress address = packet.getAddress();
                String reportMessage = "tks";
                //��ȡ���յ��������ݺ�ȡ����ַ��˿�,Ȼ���û�ȡ����ַ��˿ڻظ�����
                sendMessaage(reportMessage, port, address, client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //�ظ�����
    private static void sendMessaage(String reportMessage, int port, InetAddress address, DatagramSocket client) {
        try {
            byte[] sendBuf = reportMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            client.send(sendPacket);
            System.out.println("��Ϣ���ͳɹ�!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
