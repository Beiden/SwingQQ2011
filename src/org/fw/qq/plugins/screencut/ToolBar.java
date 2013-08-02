package org.fw.qq.plugins.screencut;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.fw.utils.Config;
import org.fw.utils.ImageHelper;
import org.fw.utils.ProjectPath;

public class ToolBar extends JFrame implements ActionListener {

	private static final long serialVersionUID = 4716824854669427393L;

	ToolBar toolBar;
	JButton saveBtn, cancelBtn, completeBtn;
	JFileChooser fileDialog;// �ļ�ѡ����
	CutScreen cutScreen;
	

	public ToolBar(CutScreen cutScreen) {
		this.cutScreen = cutScreen;
		this.toolBar = this;
		this.setSize(120, 30);
		init();
		this.setUndecorated(true);
	}

	protected void init() {
		
		ImageHelper imageHelper = new ImageHelper();
		Config cfg = new Config(ProjectPath.getProjectPath()
				+ "cfg/pluginsScreenCut.properties");
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));

		JPanel panelOther = new JPanel();
		panelOther.setLayout(new GridLayout(1, 2));
		panelOther.setBorder(new EmptyBorder(0, 0, 0, 0));
		Image saveImage =imageHelper.getFWImage(
				cfg.getProperty("saveImage", "skin/myplugin/screencut/save.png"));
		saveBtn = new JButton(new ImageIcon(saveImage));
		saveBtn.setToolTipText("�����ͼ");
		saveBtn.setBorder(BorderFactory.createEmptyBorder());
		saveBtn.addActionListener(this);
		//������꾭��ʱ��ť��ʽ
		setButtonStyle(saveBtn);

		Image cancelImage = imageHelper.getFWImage(
				cfg.getProperty("cancelImage", "skin/myplugin/screencut/wrong.png"));
		cancelBtn = new JButton(new ImageIcon(cancelImage));
		cancelBtn.setToolTipText("�˳���ͼ");
		cancelBtn.addActionListener(this);
		cancelBtn.setBorder(BorderFactory.createEmptyBorder());
		//������꾭��ʱ��ť��ʽ
		setButtonStyle(cancelBtn);
		
		
		Image completeImage = imageHelper.getFWImage(
				cfg.getProperty("completeImage", "skin/myplugin/screencut/right.png"));
		completeBtn = new JButton("ȷ��", new ImageIcon(completeImage));
		completeBtn.setToolTipText("��ɽ�ͼ");
		completeBtn.addActionListener(this);
		completeBtn.setBorder(BorderFactory.createEmptyBorder());
		//������꾭��ʱ��ť��ʽ
		setButtonStyle(completeBtn);

		panelOther.add(saveBtn);
		panelOther.add(cancelBtn);

		panel.add(panelOther);
		panel.add(completeBtn);

		this.add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == completeBtn) {
			// �����ͼ
			toolBar.setVisible(false);
			cutScreen.setVisible(false);
			//����ͼ�����ڼ�������
			MyClipboard.setClipboardImage(cutScreen.getTargetImg());
		} else if (e.getSource() == cancelBtn) {
			//���ؿ��ܻ���ʾ�ĵ����˵�
			cutScreen.getToolMenu().setVisible(false);
			toolBar.setVisible(false);
			cutScreen.setVisible(false);
		} else if (e.getSource() == saveBtn) {
			//���ؿ��ܻ���ʾ�ĵ����˵�
			cutScreen.getToolMenu().setVisible(false);
			savePic(this,cutScreen);
			toolBar.setVisible(false);
			cutScreen.setVisible(false);
		}
	}
	//����ͼƬ
	public void savePic(JFrame toolBar,CutScreen cutScreen) {
		// ��ȡ���ص�ͼƬ
		Image image = cutScreen.getTargetImg();
		// ���ϴα����Ŀ¼��ʼ
		fileDialog = new JFileChooser(
				);
		String[] picExt = ".jpg,.gif,.png,.bmp,.jpeg".split(",");
		// ѭ�����ļ��������б���ӹ�����
		for (int i = 0; i < picExt.length; i++) {
			MyFileFilter filter = new MyFileFilter();
			filter.addFilter(picExt[i], picExt[i]);
			fileDialog.addChoosableFileFilter(filter);
		}

		fileDialog.setDialogTitle("����ͼƬ");
		int returnVal = fileDialog.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {// ����������水ť
			try {
				// ��ȡ��ǰ�ļ��ĺ�׺
				String fileExt = fileDialog.getFileFilter().getDescription()
						.replace(".", "");
				// ��ȡҪ������ļ�·��
				String fileName = fileDialog.getSelectedFile().toString();
				// ���û�������ļ���
				if (fileName.length() <= 0) {
					return;
				}
				boolean hasExt = false;
				// ����û��Ƿ������׺
				for (int i = 0; i < picExt.length; i++) {
					if (fileName.endsWith(picExt[i])) {
						hasExt = true;
					}
				}
				// ���û�к�׺��,����Ϊ�Զ��û�����
				if (!hasExt) {
					fileName = fileName + "." + fileExt;
				}
				// ��ͼƬ����д���ļ�
				ImageIO.write((RenderedImage) image, fileExt,
						new File(fileName));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			toolBar.setVisible(false);
			cutScreen.setVisible(false);
		} 
	}
	
	public void setButtonStyle(final JButton btn){
		btn.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				btn.setBorder(BorderFactory.createEtchedBorder());
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				btn.setBorder(BorderFactory.createEmptyBorder());
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

