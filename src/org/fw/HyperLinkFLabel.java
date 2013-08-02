package org.fw;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JLabel;

import org.apache.log4j.Logger;

/**
 *  �����ӱ�ǩ
 * @author Administrator
 *
 */
public class HyperLinkFLabel extends JLabel implements MouseListener {

	private static final long serialVersionUID = -2654237969531133307L;

	private static Logger log = Logger.getLogger(HyperLinkFLabel.class);

	private String url;// �����ӵ�ַ
	
	private boolean isMouseIn;// ����Ƿ�����ǩ
	
	public HyperLinkFLabel() {
		super();
		initParameters();
	}
	
	/**
	 * ͨ����ʾ�ı�,�����ӵ�ַ������Label
	 * 
	 * @param text
	 *            ��ʾ�ı�
	 * @param url
	 *            �����ӵ�ַ
	 */
	public HyperLinkFLabel(String text, String url) {
		super(text);
		this.url = url;
		isMouseIn = false;
	}
	
	/**
	 * ������ʼ��
	 */
	private void initParameters(){
		url = "";
		isMouseIn = false;
	}
	
	public void paint(Graphics g) {
		if(isMouseIn){
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
			g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
		}else{
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		super.paint(g);
	}

	public void mouseClicked(MouseEvent e) {
		try {
			if(url.trim().length()==0){
				return;
			}
			Runtime.getRuntime().exec("cmd.exe /c start " + url);
		} catch (IOException ioe) {
			log.info("�����������!");
			ioe.printStackTrace();
		}
	}

	public void mouseEntered(MouseEvent e) {
		isMouseIn = true;
		this.repaint();
	}

	public void mouseExited(MouseEvent e) {
		isMouseIn = false;
		this.repaint();
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

}
