package org.fw;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;

public class OpaqueButton extends JButton {

	private static final long serialVersionUID = 3005299449610532547L;

	private float alphaper;// ����͸����

	private int arcWidth;// Բ�ǳ���

	private int arcHeight;// Բ�ǿ��

	private boolean isMouseOver;// ������

	Paint oldPiant;
	
	public OpaqueButton() {
		init();
	}
	
	

	private void init() {
		alphaper = 0.3f;
		arcWidth = 5;
		arcHeight = 5;
		Border border = BorderFactory.createEmptyBorder(0, 1, 0, 1);
		this.setBorder(border);
		// ȥ��������Χ���߿�
		this.setUI(new BasicButtonUI());
		// ���ð�ť͸��
		this.setContentAreaFilled(false);
	}

	public OpaqueButton(Icon icon) {
		super(icon);
		init();
	}

	public OpaqueButton(String text) {
		super(text);
		init();
	}

	public OpaqueButton(String text, Icon icon) {
		super(text, icon);
		init();
	}


	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if(oldPiant == null){
			oldPiant = g2.getPaint();
		}
		
		if (isMouseOver) {
			// ��������
			GradientPaint highlight = new GradientPaint(new Point2D.Float(
					0, 0), new Color(255, 255, 240, (int) (255 * this
					.getAlphaper())), new Point2D.Float(
					this.getWidth() - 1, this.getHeight() - 1), new Color(
							255, 255, 240, (int) (255 * this.getAlphaper())));
			g2.setPaint(highlight);
			g2.fillRoundRect(0, 1, this.getWidth() - 2,
					this.getHeight() - 2, arcWidth, arcHeight);
			g2.drawRoundRect(0, 1, this.getWidth() - 2,
					this.getHeight() - 2, arcWidth, arcHeight);
		}
		
		super.paintComponent(g);
	}

	public float getAlphaper() {
		return alphaper;
	}

	public void setAlphaper(float alphaper) {
		this.alphaper = alphaper;
		this.repaint();
	}

	public int getArcHeight() {
		return arcHeight;
	}

	public void setArcHeight(int arcHeight) {
		this.arcHeight = arcHeight;
		this.repaint();
	}

	public int getArcWidth() {
		return arcWidth;
	}

	public void setArcWidth(int arcWidth) {
		this.arcWidth = arcWidth;
		this.repaint();
	}

	public boolean isMouseOver() {
		return isMouseOver;
	}

	public void setMouseOver(boolean isMouseOver) {
		this.isMouseOver = isMouseOver;
		this.repaint();
	}


}
