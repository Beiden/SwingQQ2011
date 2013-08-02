package org.fw.qq.plugins.screencut;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.fw.qq.QQTalkFrame;
import org.fw.utils.Config;
import org.fw.utils.ImageHelper;
import org.fw.utils.ProjectPath;

public class CutScreen extends JFrame implements MouseListener,
		MouseMotionListener {

	private static final long serialVersionUID = 7763802628947303464L;
	// ��ͼ��״̬����
	private final int INITIALIZE = 0;// ��ʼ��
	private final int CUTTING = 1;// ��ͼ��
	private final int GETRANGE = 2;// �ѻ��һ�������С
	private final int SETRANGE = 3;// �ı������С
	private final int COMPLETE = 4;// ��ͼ���
	private final int HOTWIDTH = 5;// ����ȵ���
	private final int HOTHEIGHT = 5;// ����ȵ�߶�
	
	private ToolBar toolBar;// ������
	private JPopupMenu toolMenu;// �����˵�
	private JMenuItem completeItem, saveItem, showItem, exitItem;// �˵���
	private int startX = 0, startY = 0, endX = 0, endY = 0;// ��ͼ����ʼ����
	private int x, y;// ��굱ǰ��x,y����
	private int offsetX, offsetY;// ��굱ǰλ������������ƫ����
	private int state = INITIALIZE;
	private int width = 0;// ��ͼ����Ŀ��
	private int height = 0;// ��ͼ����ĸ߶�

	private BufferedImage bufImg;// ���ڴ��ȫ����ͼƬ
	private BufferedImage targetImg;// ���ڴ�Ž�ȡ��ͼƬ
	
	Config cfg;
	CutScreen cutScreen;
	Robot robot;
	Toolkit toolkit = Toolkit.getDefaultToolkit();

	public JPopupMenu getToolMenu() {
		return toolMenu;
	}

	public BufferedImage getTargetImg() {
		return targetImg;
	}

	@SuppressWarnings("deprecation")
	public CutScreen() {
		ImageHelper imageHelper = new ImageHelper();
		cfg = new Config(ProjectPath.getProjectPath()
				+ "cfg/pluginsScreenCut.properties");
		cutScreen = this;
		init();

		toolMenu = new JPopupMenu();
		// ����ͼƬ��Դ
		Image completeImage = imageHelper.getFWImage(
				cfg.getProperty("completeImage", "skin/myplugin/screencut/right.png"));
		Image saveImage = imageHelper.getFWImage(
				cfg.getProperty("saveImage", "skin/myplugin/screencut/save.png"));
		Image cancelImage = imageHelper.getFWImage(
				cfg.getProperty("cancelImage", "skin/myplugin/screencut/wrong.png"));

		// ����˵���
		completeItem = new JMenuItem("��ɽ�ͼ", new ImageIcon(completeImage));
		saveItem = new JMenuItem("�����ͼ", new ImageIcon(saveImage));
		showItem = new JMenuItem("���ع�����");
		exitItem = new JMenuItem("�˳���ͼ", new ImageIcon(cancelImage));

		// ��Ӳ˵�
		toolMenu.add(completeItem);
		toolMenu.add(saveItem);
		toolMenu.addSeparator();
		toolMenu.add(showItem);
		toolMenu.add(exitItem);

		// ���ò˵���꾭������ʽ
		setPopuMenuItem(completeItem);
		setPopuMenuItem(saveItem);
		setPopuMenuItem(showItem);
		setPopuMenuItem(exitItem);

		// ��ӽ��������
		toolMenu.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {

			}

			@Override
			public void focusLost(FocusEvent arg0) {
				toolMenu.setVisible(false);
			}

		});

		completeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO ��ɽ�ͼ�¼�
				state = COMPLETE;
				// �����ͼ��������
				MyClipboard.setClipboardImage(targetImg);
				toolMenu.setVisible(false);
				toolBar.setVisible(false);
				QQTalkFrame.getInstance().getCutScreen().setVisible(false);
				QQTalkFrame.getInstance().setVisible(true);
			}
		});

		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toolMenu.setVisible(false);
				toolBar.savePic(toolBar,cutScreen);

			}
		});

		showItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (toolBar.isVisible()) {
					toolBar.setVisible(false);
					showItem.setText("��ʾ������");
				} else {
					toolBar.setVisible(true);
					showItem.setText("���ع�����");
				}
				toolMenu.setVisible(false);
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toolBar.setVisible(false);
				QQTalkFrame.getInstance().getCutScreen().setVisible(false);
				QQTalkFrame.getInstance().setVisible(true);
				toolMenu.setVisible(false);
			}
		});

		this.setSize(toolkit.getScreenSize());
		this.setCursor(Cursor.CROSSHAIR_CURSOR);
		this.setResizable(false);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setUndecorated(true);
	}

	protected void init() {
		try {
			robot = new Robot();
			bufImg = robot.createScreenCapture(new Rectangle(toolkit
					.getScreenSize()));
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		if (arg0.getButton() == 3) {// ��������Ҽ�
			if (state == INITIALIZE) {
				this.setVisible(false);
				toolBar.setVisible(false);
			}
			// �ڽ�ͼ������
			if (arg0.getX() > startX && arg0.getX() < startX + width
					&& arg0.getY() > startY && arg0.getY() < startY + height) {
				if (state == GETRANGE) {
					toolMenu.setLocation(arg0.getX(), arg0.getY());
					toolMenu.setVisible(true);
				}
			}
		}
		if (arg0.getButton() == 1) {// �������
			toolMenu.setVisible(false);
		}
		if (arg0.getClickCount() == 2 && arg0.getButton() == 1) {
			if (endX - startX > 0 && endY - startY > 0) {
				success();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mousePressed(MouseEvent arg0) {
		// ��ʼ��״̬����
		if (state == INITIALIZE) {
			state = CUTTING;
			startX = arg0.getX();
			startY = arg0.getY();
		}

		if (state == GETRANGE) {
			x = arg0.getX();
			y = arg0.getY();
			offsetX = x - startX;
			offsetY = y - startY;
			// ������ȵ㰴��
			if (x > startX && x < startX + HOTWIDTH && y > startY
					&& y < startY + HOTWIDTH) {// ���Ͻ�
				this.setCursor(Cursor.NW_RESIZE_CURSOR);
				state = SETRANGE;
			} else if (x > startX + width / 2
					&& x < startX + width / 2 + HOTWIDTH && y > startY
					&& y < startY + HOTWIDTH) {// �ϱ߽�
				this.setCursor(Cursor.N_RESIZE_CURSOR);
				state = SETRANGE;
			} else if (x > startX + width - HOTWIDTH && x < startX + width
					&& y > startY && y < startY + HOTWIDTH) {// ���Ͻ�
				this.setCursor(Cursor.NE_RESIZE_CURSOR);
				state = SETRANGE;
			} else if (x > startX && x < startX + HOTWIDTH
					&& y > startY + height / 2
					&& y < startY + height / 2 + HOTHEIGHT)// ��߽�
			{
				this.setCursor(Cursor.W_RESIZE_CURSOR);
				state = SETRANGE;
			} else if (x > startX + width - HOTWIDTH && x < startX + width
					&& y > startY + height / 2
					&& y < startY + height / 2 + HOTHEIGHT)// �ұ߽�
			{
				this.setCursor(Cursor.E_RESIZE_CURSOR);
				state = SETRANGE;
			} else if (x > startX && x < startX + HOTWIDTH
					&& y > startY + height - HOTHEIGHT && y < startY + height)// ���½�
			{
				this.setCursor(Cursor.SW_RESIZE_CURSOR);
				state = SETRANGE;
			} else if (x > startX + width / 2
					&& x < startX + width / 2 + HOTWIDTH
					&& y > startY + height - HOTHEIGHT && y < startY + height)// �±߽�
			{
				this.setCursor(Cursor.S_RESIZE_CURSOR);
				state = SETRANGE;
			} else if (x > startX + width - HOTWIDTH && x < startX + width
					&& y > startY + height - HOTHEIGHT && y < startY + height)// ���½�
			{
				this.setCursor(Cursor.SE_RESIZE_CURSOR);
				state = SETRANGE;
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (state == CUTTING) {
			if (Math.abs(endX - startX) > 0 && Math.abs(endY - startY) > 0) {
				width = Math.abs(endX - startX);
				height = Math.abs(endY - startY);
				if (endX - startX < 0) {// �������Ͻ�ͼ
					startX = startX - width;
					startY = startY - height;
				}
				// ��ȡ��ͼ
				targetImg = bufImg.getSubimage(startX + 1, startY + 1,width - 1, height - 1);

				this.setCursor(Cursor.HAND_CURSOR);
				state = GETRANGE;
				repaint();
				toolBar = new ToolBar(cutScreen);
				toolBar.setLocation(x - 120, y);
				toolBar.setVisible(true);
			}
		}
		// ��ȡҪ��ͼ������ͼƬ
		if (state == GETRANGE) {

			if (width > Toolkit.getDefaultToolkit().getScreenSize().width)
				width = Toolkit.getDefaultToolkit().getScreenSize().width;
			if (height > Toolkit.getDefaultToolkit().getScreenSize().height)
				height = Toolkit.getDefaultToolkit().getScreenSize().height;

			// ��ȡ��ͼ
			targetImg = bufImg.getSubimage(startX + 1, startY + 1, width - 1,
					height - 1);
		}

		if (state == SETRANGE) {
			state = GETRANGE;
			this.setCursor(Cursor.MOVE_CURSOR);
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// ѡ���ͼ����
		if (state == CUTTING) {
			endX = arg0.getX();
			endY = arg0.getY();
			this.x = arg0.getX();
			this.y = arg0.getY();
			repaint();
		}
		// ����ڽ�ͼ������
		if (x > startX && x < startX + width && y > startY
				&& y < startY + height) {
			// ���ѻ�ȡ��ͼ״̬���£��ı��ͼ����
			if (state == GETRANGE) {
				this.x = arg0.getX();
				this.y = arg0.getY();
				startX = x - offsetX;
				startY = y - offsetY;
				repaint();
			}
		}

		if (state == SETRANGE) {

			if (this.getCursor().getType() == Cursor.NW_RESIZE_CURSOR) {// ��������Ͻ��϶�
				offsetX = arg0.getX() - startX;
				offsetY = arg0.getY() - startY;
				startX = arg0.getX();
				startY = arg0.getY();
				width -= offsetX;
				height -= offsetY;
			} else if (this.getCursor().getType() == Cursor.N_RESIZE_CURSOR) {// ��
				offsetY = arg0.getY() - startY;
				startY = arg0.getY();
				height -= offsetY;
			} else if (this.getCursor().getType() == Cursor.NE_RESIZE_CURSOR)// ���Ͻ�
			{
				offsetX = arg0.getX() - (startX + width);
				offsetY = arg0.getY() - startY;
				startY = arg0.getY();
				width += offsetX;
				height -= offsetY;

			} else if (this.getCursor().getType() == Cursor.W_RESIZE_CURSOR)// ���
			{
				// �������ʱ��startX==argo.getX();���Լ���ƫ����Ӧ���Ǳ��������ȥ�ϴ�����
				offsetX = startX;// �ϴ������ֵ
				startX = arg0.getX();// ���������ֵ
				offsetX = startX - offsetX;
				width = width - offsetX;
			} else if (this.getCursor().getType() == Cursor.E_RESIZE_CURSOR)// �ұ�
			{
				offsetX = arg0.getX() - (startX + width);
				width += offsetX;
			} else if (this.getCursor().getType() == Cursor.SW_RESIZE_CURSOR)// ���½�
			{
				offsetX = arg0.getX() - startX;
				offsetY = arg0.getY() - (startY + height);
				startX = arg0.getX();
				width -= offsetX;
				height += offsetY;
			} else if (this.getCursor().getType() == Cursor.S_RESIZE_CURSOR)// ��
			{
				offsetY = arg0.getY() - (startY + height);
				height += offsetY;
			} else if (this.getCursor().getType() == Cursor.SE_RESIZE_CURSOR)// ���½�
			{
				offsetX = arg0.getX() - (startX + width);
				offsetY = arg0.getY() - (startY + height);
				width += offsetX;
				height += offsetY;
			}
			repaint();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void mouseMoved(MouseEvent arg0) {
		// �ı䵱ǰx,y����
		if (state == CUTTING) {
			this.x = arg0.getX();
			this.y = arg0.getY();
			repaint();
		}
		// �ı������ʽ
		if (state == GETRANGE) {
			this.setCursor(Cursor.MOVE_CURSOR);
			// �����꾭���ȵ㣬�ı������ʽ
			x = arg0.getX();
			y = arg0.getY();

			if (x > startX && x < startX + HOTWIDTH && y > startY
					&& y < startY + HOTWIDTH) {// ���Ͻ�
				this.setCursor(Cursor.NW_RESIZE_CURSOR);
			} else if (x > startX + width / 2
					&& x < startX + width / 2 + HOTWIDTH && y > startY
					&& y < startY + HOTWIDTH) {// �ϱ߽�
				this.setCursor(Cursor.N_RESIZE_CURSOR);
			} else if (x > startX + width - HOTWIDTH && x < startX + width
					&& y > startY && y < startY + HOTWIDTH) {// ���Ͻ�
				this.setCursor(Cursor.NE_RESIZE_CURSOR);
			} else if (x > startX && x < startX + HOTWIDTH
					&& y > startY + height / 2
					&& y < startY + height / 2 + HOTHEIGHT)// ��߽�
			{
				this.setCursor(Cursor.W_RESIZE_CURSOR);
			} else if (x > startX + width - HOTWIDTH && x < startX + width
					&& y > startY + height / 2
					&& y < startY + height / 2 + HOTHEIGHT)// �ұ߽�
			{
				this.setCursor(Cursor.E_RESIZE_CURSOR);
			} else if (x > startX && x < startX + HOTWIDTH
					&& y > startY + height - HOTHEIGHT && y < startY + height)// ���½�
			{
				this.setCursor(Cursor.SW_RESIZE_CURSOR);
			} else if (x > startX + width / 2
					&& x < startX + width / 2 + HOTWIDTH
					&& y > startY + height - HOTHEIGHT && y < startY + height)// �±߽�
			{
				this.setCursor(Cursor.S_RESIZE_CURSOR);
			} else if (x > startX + width - HOTWIDTH && x < startX + width
					&& y > startY + height - HOTHEIGHT && y < startY + height)// ���½�
			{
				this.setCursor(Cursor.SE_RESIZE_CURSOR);
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bufImg, 0, 0, this);

		g2d.setBackground(Color.darkGray);
		g2d.fillRect(startX, startY - 35, 160, 35);
		g2d.setColor(Color.white);

		// �������Ͻǵ���ʾ��Ϣ
		if (state == CUTTING) {
			g2d.drawString(
					"��ǰ��С:" + Math.abs(x - startX) + "*" + Math.abs(y - startY),
					startX + 5, startY - 20);
		} else if (state == GETRANGE || state == SETRANGE) {
			g2d.drawString("��ǰ��С:" + width + "*" + height, startX + 5,
					startY - 20);
		}
		g2d.drawString("˫����������ɽ�ͼ��", startX + 5, startY - 5);

		// ��������߿�
		if (state == CUTTING) {
			g2d.setColor(Color.red);
			// ��������߿�
			g2d.drawRect(startX, startY, x - startX, y - startY);
			// ��������ȵ�
			g2d.setColor(Color.black);
			g2d.fillRect(startX, startY, HOTWIDTH, HOTHEIGHT);// ���Ͻ��ȵ�
			g2d.fillRect(startX + (x - startX) / 2, startY, HOTWIDTH, HOTHEIGHT);// �ϱ߽��ȵ�
			g2d.fillRect(x - HOTWIDTH, startY, HOTWIDTH, HOTHEIGHT);// ���Ͻ��ȵ�
			g2d.fillRect(startX, startY + (y - startY) / 2, HOTWIDTH, HOTHEIGHT);// ��߽��ȵ�
			g2d.fillRect(x - HOTWIDTH, startY + (y - startY) / 2, HOTWIDTH,
					HOTHEIGHT);// �ұ߽��ȵ�
			g2d.fillRect(startX, y - HOTHEIGHT, HOTWIDTH, HOTHEIGHT);// ���½��ȵ�
			g2d.fillRect(startX + (x - startX) / 2, y - HOTHEIGHT, HOTWIDTH,
					HOTHEIGHT);// �±߽��ȵ�
			g2d.fillRect(x - HOTWIDTH, y - HOTHEIGHT, HOTWIDTH, HOTHEIGHT);// ���½��ȵ�
		} else if (state == GETRANGE || state == SETRANGE) {
			g2d.setColor(Color.red);
			// ��������߿�
			g2d.drawRect(startX, startY, width, height);
			// ��������ȵ�
			g2d.setColor(Color.black);
			g2d.fillRect(startX, startY, HOTWIDTH, HOTHEIGHT);// ���Ͻ��ȵ�
			g2d.fillRect(startX + width / 2, startY, HOTWIDTH, HOTHEIGHT);// �ϱ߽��ȵ�
			g2d.fillRect(startX + width - HOTWIDTH, startY, HOTWIDTH, HOTHEIGHT);// ���Ͻ��ȵ�
			g2d.fillRect(startX, startY + height / 2, HOTWIDTH, HOTHEIGHT);// ��߽��ȵ�
			g2d.fillRect(startX + width - HOTWIDTH, startY + height / 2,
					HOTWIDTH, HOTHEIGHT);// �ұ߽��ȵ�
			g2d.fillRect(startX, startY + height - HOTHEIGHT, HOTWIDTH,
					HOTHEIGHT);// ���½��ȵ�
			g2d.fillRect(startX + width / 2, startY + height - HOTHEIGHT,
					HOTWIDTH, HOTHEIGHT);// �±߽��ȵ�
			g2d.fillRect(startX + width - HOTWIDTH,
					startY + height - HOTHEIGHT, HOTWIDTH, HOTHEIGHT);// ���½��ȵ�
			// ���ù�����λ��
			if (showItem.getText().equals("���ع�����")) {
				toolBar.setVisible(true);
			}
			toolBar.setLocation(startX + width - toolBar.getWidth(), startY
					+ height);
		}

	}

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * ��ͼ�ɹ�
	 */
	public void success() {
		state = COMPLETE;
		this.setVisible(false);
		toolBar.setVisible(false);
		MyClipboard.setClipboardImage(targetImg);
	}

	/**
	 * ���ò˵�����ʽ
	 * 
	 * @param item
	 *            �˵���
	 */
	public void setPopuMenuItem(final JMenuItem item) {
		item.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				item.setArmed(true);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				item.setArmed(false);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
	}
}
