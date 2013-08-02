package org.fw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.fw.event.MoveMouseListener;
import org.fw.qq.QQTalkFrame;
import org.fw.utils.Config;
import org.fw.utils.ImageHelper;
import org.fw.utils.ProjectPath;

public class FramePanel extends JPanel {

	private static final long serialVersionUID = 9159162439583812390L;

	private static Logger log = Logger.getLogger(FramePanel.class);

	protected String title;// ��������

	protected Image bgImage;// ����ͼƬ

	protected JLabel titleLabel;// �����ǩ

	protected MoveMouseListener mml;// ����¼�
	
	protected JPanel headPanel;//ͷ�����
	protected ImageIcon headIcon;//ͷ��ͼƬ

	protected OpaqueButton minButton;// ��С����ť

	protected OpaqueButton maxButton;// ��󻯰�ť

	protected OpaqueButton closeButton;// �رհ�ť

	protected Icon minIcon;// ��С����ťͼ��

	protected Icon maxIcon;// ��󻯰�ťͼ��

	protected Icon closeIcon;// �رհ�ťͼ��

	protected Icon normalIcon;// ��ͨ��ťͼ��

	protected JFrame parent;// ������

	protected JComponent contain;// �������Ҫ����

	protected JComponent bottom;// ����ĵײ�

	protected Boolean canMove;// �Ƿ�����ƶ�����

	protected Boolean canResize;// �Ƿ���Ըı䴰�ڴ�С

	protected Boolean ennableMaxButton;// �Ƿ�������󻯴���

	protected int hgap= 5;// BorderLayoutˮƽ���

	protected int vgap=5;// BorderLayout��ֱ���

	private int iwidth;// ����ͼƬ���

	private int iheight;// ����ͼƬ�߶�
	
	private Boolean isMain=false;//�Ƿ���������

	Config basicCFG;// ȫ������
	
	ImageHelper imageHelper;
	MouseListener minMaxListener;

	public FramePanel() {
		init();
	}
	
	public FramePanel(Boolean isMain){
		this.isMain = isMain;
		init();
	}

	/**
	 * ָ���ļ�������Panel
	 * 
	 * @param file
	 *            ͼƬ�ļ�
	 */
	public FramePanel(File file) {
		try {
			bgImage = ImageIO.read(file);
		} catch (IOException e) {
			log.info("��ȡͼƬ�ļ�ʧ��!");
			bgImage = null;
			e.printStackTrace();
		}
		init();
	}

	/**
	 * ָ��ͼƬ·��������Panel
	 * 
	 * @param imagePath
	 *            ͼƬ·��
	 */
	public FramePanel(String imagePath) {
		try {
			bgImage = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			log.info("��ȡͼƬ�ļ�ʧ��!");
			bgImage = null;
			e.printStackTrace();
		}
		init();
	}

	/**
	 * ָ��ͼƬ������Panel
	 * 
	 * @param image
	 *            ͼƬ
	 */
	public FramePanel(Image image) {
		this.bgImage = image;
		init();
	}
	/**
	 * 
	 * @param headImage ͷ��ͼƬ 
	 */
	public FramePanel(ImageIcon headImage){
		this.headIcon = headImage;
		imageHelper = new ImageHelper();
		initParameters();
		initComponents(headIcon);
		initListeners();
	}

	private void init() {
		imageHelper = new ImageHelper();
		initParameters();
		initComponents(headIcon);
		initListeners();
	}

	private void initParameters() {
	
		basicCFG = new Config(ProjectPath.getProjectPath()
				+ "cfg/basic.properties");
		
		if (canMove == null) {
			canMove = true;
		}
		if (canResize == null) {
			canResize = true;
		}
		title = "�´���";
		ennableMaxButton = false;
		hgap = 5;
		vgap = 5;
		iwidth = 0;
		iheight = 0;
		minIcon = imageHelper.getFWImageIcon(basicCFG.getProperty("MinIcon",
				"skin/default/min.png"));
		maxIcon = imageHelper.getFWImageIcon(basicCFG.getProperty("MaxIcon",
				"skin/default/max.png"));
		normalIcon = imageHelper.getFWImageIcon(basicCFG.getProperty("NormalIcon",
				"skin/default/normal.png"));
		closeIcon = imageHelper.getFWImageIcon(basicCFG.getProperty("CloseIcon",
				"skin/default/close.png"));
		
	}

	private void initComponents(ImageIcon headImage) {
		
		this.setLayout(new BorderLayout(5,5));
		
		// ����
		headPanel = new JPanel(new BorderLayout(5, 5));
		headPanel.setOpaque(false);
		
		if(headIcon != null){
			titleLabel = new JLabel(title,new ImageIcon("skin/default/head/head_1.png"),SwingConstants.CENTER);
		}else{
			titleLabel = new JLabel(title);
		}
		headPanel.add(titleLabel, BorderLayout.WEST);

		if (parent != null) {
			JPanel headButtonPanel = new JPanel();
			headButtonPanel.setLayout(new FlowLayout());
			headButtonPanel.setOpaque(false);
			if (minButton == null) {
				minButton = new OpaqueButton(minIcon);
				minButton.setToolTipText(basicCFG.getProperty("MinIconTip",
				"��С��"));
				minButton.addMouseListener(new MouseListener() {

					public void mouseClicked(MouseEvent mouseevent) {
						parent.setExtendedState(JFrame.ICONIFIED);// ��С��
					}

					public void mouseEntered(MouseEvent mouseevent) {
						minButton.setMouseOver(true);
					}

					public void mouseExited(MouseEvent mouseevent) {
						minButton.setMouseOver(false);
					}

					public void mousePressed(MouseEvent mouseevent) {
					}

					public void mouseReleased(MouseEvent mouseevent) {
					}
				});
			}
			if (ennableMaxButton) {
				if (maxButton == null) {
					maxButton = new OpaqueButton(maxIcon);
					maxButton.setToolTipText(basicCFG.getProperty("MaxIconTip",
					"���"));
					maxButton.addMouseListener(new MouseListener() {

						public void mouseClicked(MouseEvent mouseevent) {

							if (parent.getExtendedState() == JFrame.NORMAL) {
								parent.setExtendedState(JFrame.MAXIMIZED_BOTH);// ���
								maxButton.setIcon(normalIcon);
								maxButton.setToolTipText(basicCFG.getProperty("NormalIconTip",
								"��ԭ"));
								return;
							} else if (parent.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
								parent.setExtendedState(JFrame.NORMAL);
								maxButton.setIcon(maxIcon);
								maxButton.setToolTipText(basicCFG.getProperty("MaxIconTip",
								"���"));
								return;
							}
						}

						public void mouseEntered(MouseEvent mouseevent) {
							maxButton.setMouseOver(true);
						}

						public void mouseExited(MouseEvent mouseevent) {
							maxButton.setMouseOver(false);
						}

						public void mousePressed(MouseEvent mouseevent) {
						}

						public void mouseReleased(MouseEvent mouseevent) {
						}
					});
				}
			}
			if (closeButton == null) {
				closeButton = new OpaqueButton(closeIcon);
				closeButton.setToolTipText(basicCFG.getProperty("CloseIconTip",
				"�ر�"));
				closeButton.addMouseListener(new MouseListener() {

					public void mouseClicked(MouseEvent mouseevent) {
						if(isMain){
							parent.setVisible(false);
							System.exit(0);
						}else{
							parent.setVisible(false);
						}
						
						if(parent instanceof QQTalkFrame){
							
						}
					}

					public void mouseEntered(MouseEvent mouseevent) {
						closeButton.setMouseOver(true);
						parent.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}

					public void mouseExited(MouseEvent mouseevent) {
						closeButton.setMouseOver(false);
					}

					public void mousePressed(MouseEvent mouseevent) {
					}

					public void mouseReleased(MouseEvent mouseevent) {
					}
				});
			}
			headButtonPanel.add(minButton);
			if (ennableMaxButton) {
				headButtonPanel.add(maxButton);
			}
			headButtonPanel.add(closeButton);

			headPanel.add(headButtonPanel, BorderLayout.EAST);
		}
		this.add(headPanel, BorderLayout.NORTH);
		
		minMaxListener = new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					if (parent.getExtendedState() == JFrame.NORMAL) {
						parent.setExtendedState(JFrame.MAXIMIZED_BOTH);// ���
						maxButton.setIcon(normalIcon);
						maxButton.setToolTipText(basicCFG.getProperty("NormalIconTip",
						"��ԭ"));
						return;
					} else if (parent.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
						parent.setExtendedState(JFrame.NORMAL);
						maxButton.setIcon(maxIcon);
						maxButton.setToolTipText(basicCFG.getProperty("MaxIconTip",
						"���"));
						return;
					}
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
			
		};
		
		headPanel.addMouseListener(minMaxListener);
		// �м�
		contain = new JPanel();
		contain.setOpaque(false);
		contain.setLayout(new GridBagLayout());
		if (contain != null) {
			this.add(contain, BorderLayout.CENTER);
		}
		// �ײ�
		bottom = new JPanel();
		bottom.setOpaque(false);
		bottom.setLayout(new GridBagLayout());
		if (bottom != null) {
			this.add(bottom, BorderLayout.SOUTH);
		}
	}

	private void initListeners() {
		if (canResize || canMove) {
			mml = new MoveMouseListener(this);
			mml.setCanMove(canMove);
			mml.setCanResize(canResize);
			this.addMouseListener(mml);
			this.addMouseMotionListener(mml);
		}
	}

	protected void paintComponent(Graphics g) {
		if (bgImage != null) {
			processBackground(g);
		}
	}

	/**
	 * �����Ƿ�����϶�
	 * 
	 * @param canDrag
	 *            �Ƿ�����϶�
	 */
	public void setDraggable(Boolean canDrag) {
		this.canMove = canDrag;
		if (!canDrag) {
			this.removeMouseListener(mml);
			this.removeMouseMotionListener(mml);
		} else {
			this.addMouseListener(mml);
			this.addMouseMotionListener(mml);
		}
	}

	/**
	 * ���ñ���
	 * 
	 * @param title
	 *            ����
	 */
	public void setTitle(String title) {
		this.title = title;
		titleLabel.setText(title);
	}

	public void setTitle(String title,ImageIcon icon){
		this.title = title;
		titleLabel.setIcon(icon);
		titleLabel.setText(title);
		headPanel.repaint();
	}
	
	/**
	 * ���ñ���ͼƬ
	 * 
	 * @param image
	 *            Ҫ�õı���ͼƬ
	 */
	public void setBackground(Image image) {
		this.bgImage = image;
		iwidth = bgImage.getHeight(this);
		iheight = bgImage.getHeight(this);
		this.repaint();
	}
	/**
	 * ѭ��������ͼƬ
	 * 
	 * @param g
	 */
	private void processBackground(Graphics g) {
		if (bgImage == null) {
			g.setColor(Color.blue);
			g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 5, 5);
		} else {
			int width = getWidth();
			int height = getHeight();
			iwidth = bgImage.getHeight(this);
			iheight = bgImage.getHeight(this);
			int x = 0;
			int y = 0;
			g.drawImage(bgImage, x, y, null);
		}
	}

	public JFrame getParent() {
		return parent;
	}

	/**
	 * ���ð��������Ĵ���,����ڵ���С���Ȱ�ť�����Ƹô��ڵ���С����
	 * 
	 * @param parent
	 *            ����
	 */
	public void setParent(JFrame parent) {
		this.parent = parent;
		initComponents(headIcon);
	}

	/**
	 * �Ƿ���ʾ��󻯰�ť
	 * 
	 * @param ennableMaxButton
	 */
	public void setEnnableMaxButton(Boolean ennableMaxButton) {
		this.ennableMaxButton = ennableMaxButton;
		if (this.getParent() != null) {
			initComponents(headIcon);
		}
	}

	/**
	 * ���ò��ֵ�ˮƽ����ļ��
	 * 
	 * @param hgap
	 *            ���ֵ
	 */
	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	/**
	 * ���ò��ֵĴ�ֱ����ļ��
	 * 
	 * @param vgap
	 *            ���ֵ
	 */
	public void setVgap(int vgap) {
		this.vgap = vgap;
	}


//	/**
//	 * �������ĵײ�����
//	 * 
//	 * @param bottom
//	 *            �ײ�����,������Ҳ��panel
//	 */
//	public void setBottom(JComponent bottom) {
//		this.bottom = bottom;
//		if (bottom instanceof JPanel) {
//			bottom.setOpaque(false);
//		}
//		initComponents();
//	}

	public Boolean getCanResize() {
		return canResize;
	}

	public void setCanResize(Boolean canResize) {
		this.canResize = canResize;
	}

	public Boolean getCanMove() {
		return canMove;
	}

	public void setCanMove(Boolean canMove) {
		this.canMove = canMove;
	}
	
	/**
	 * ������������м䲿��
	 * @param comp
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param anchor
	 * @param fill
	 * @param weightx
	 * @param weighty
	 */
	public void addContainWithGridBag(Component comp,  int x, int y,
			int width, int height, int anchor, int fill, double weightx,
			double weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.anchor = anchor;
		gbc.fill = fill;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		contain.add(comp, gbc);
	}
	
	public void addContainWithGridBag(Component comp,  int x, int y,
			int width, int height, int anchor, int fill, double weightx,
			double weighty,Insets insets) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.anchor = anchor;
		gbc.fill = fill;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.insets = insets;
		contain.add(comp, gbc);
	}
	/**
	 * �����������ײ�����
	 * @param comp
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param anchor
	 * @param fill
	 * @param weightx
	 * @param weighty
	 */
	public void addBottomWithGridBag(Component comp,  int x, int y,
			int width, int height, int anchor, int fill, double weightx,
			double weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.anchor = anchor;
		gbc.fill = fill;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		bottom.add(comp, gbc);
	}
	public void addBottomWithGridBag(Component comp,  int x, int y,
			int width, int height, int anchor, int fill, double weightx,
			double weighty,Insets insets) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.anchor = anchor;
		gbc.fill = fill;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		gbc.insets = insets;
		bottom.add(comp, gbc);
	}

	public Boolean getIsMain() {
		return isMain;
	}

	public void setIsMain(Boolean isMain) {
		this.isMain = isMain;
	}
}
