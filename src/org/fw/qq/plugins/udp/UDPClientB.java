package org.fw.qq.plugins.udp;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * 
 * <br/>
 * Title: UDPClientB.java<br/>
 * E-Mail: 176291935@qq.com<br/>
 * QQ: 176291935<br/>
 * Http: iaiai.iteye.com<br/>
 * Create time: 2013-1-29 ����11:11:56<br/>
 * <br/>
 * @author ����
 * @version 0.0.1
 */
public class UDPClientB {

    public static void main(String[] args) {
        try {
            //��server��������
            SocketAddress target = new InetSocketAddress("192.168.98.130", 2008);
            DatagramSocket client = new DatagramSocket();
            String message = "I am UDPClientB 192.168.85.129";
            byte[] sendbuf = message.getBytes();
            DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
            client.send(pack);
            //����server�Ļظ�����
            byte[] buf = new byte[1024];
            DatagramPacket recpack = new DatagramPacket(buf, buf.length);
            client.receive(recpack);
            //����server�ظ������ݣ�Ȼ���������еĵ�ַ��˿ڷ������󣨴򶴣�
            String receiveMessage = new String(recpack.getData(), 0, recpack.getLength());
            String[] params = receiveMessage.split(",");
            String host = params[0].substring(5);
            String port = params[1].substring(5);
            System.out.println(host + ":" + port);
            sendMessage(host, port, client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //��UPDClientA��������(��NAT�ϴ��)
    private static void sendMessage(String host, String port, DatagramSocket client) {
        try {
            SocketAddress target = new InetSocketAddress(host, Integer.parseInt(port));
            for (;;) {
                String message = "I am master 192.168.85.129 count test";
                byte[] sendbuf = message.getBytes();
                DatagramPacket pack = new DatagramPacket(sendbuf, sendbuf.length, target);
                client.send(pack);
                //����UDPClientA�ظ�������
                receive(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //�յ�UDPClientA�Ļظ����ݣ���͸�����
    private static void receive(DatagramSocket client) {
        try {
            for (;;) {
                //�����յ������ݴ�ӡ
                byte[] buf = new byte[1024];
                DatagramPacket recpack = new DatagramPacket(buf, buf.length);
                client.receive(recpack);
                String receiveMessage = new String(recpack.getData(), 0, recpack.getLength());
                System.out.println(receiveMessage);

                //�ǵ������յ�ַ��˿ڣ�Ȼ�������µ�ַ�������ݵ�UPDClientA,�����������Ϳ����ˡ�
                int port = recpack.getPort();
                InetAddress address = recpack.getAddress();
                String reportMessage = "I am master 192.168.85.129 count test";

                //������Ϣ
                sendMessage(reportMessage, port, address, client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(String reportMessage, int port, InetAddress address, DatagramSocket client) {
        try {
            byte[] sendBuf = reportMessage.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, port);
            client.send(sendPacket);
            System.out.println("send success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
