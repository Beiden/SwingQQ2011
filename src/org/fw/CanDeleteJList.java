package org.fw;

import org.fw.cellrender.CanDeleteCellRenderer;
import org.fw.data.CanDeleteItem;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class CanDeleteJList extends JList{

	private static final long serialVersionUID = -6286055495667023494L;

	private List<CanDeleteItem> itemList = new ArrayList<CanDeleteItem>();

	private JLabel iconLabel, nameLabel, numberLabel;

//	private JButton delBtn;

	private OpaquePanel contentPanel;// ״̬���

	public CanDeleteJList() {
		initComponents();
	}

	private void initComponents() {
		//���õ�Ԫ�񲼾�
		contentPanel = new OpaquePanel();
		contentPanel.setLayout(new GridBagLayout());
		iconLabel = new JLabel();
		addWithGridBag(iconLabel, contentPanel, 0, 0, 1, 2,
				GridBagConstraints.WEST, GridBagConstraints.BOTH, 0, 0);
		nameLabel = new JLabel();
		addWithGridBag(nameLabel, contentPanel, 1, 0, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 1, 0);
		numberLabel = new JLabel();
		addWithGridBag(numberLabel, contentPanel, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, 1, 0);
//		delBtn = new JButton();
//		addWithGridBag(delBtn, contentPanel, 2, 0, 1, 2,
//				GridBagConstraints.CENTER, GridBagConstraints.NONE, 0, 0);
//		opacify(contentPanel);
		
		
		//���õ�Ԫ����Ⱦ��
		setCellRenderer(new CanDeleteCellRenderer(contentPanel,iconLabel,nameLabel,numberLabel));
		
		//�������
		setModelData();
	}
	
	/**
	 * �����������
	 * @param item ��������
	 */
	public void addCanDeleteItem(String icon,String name,String number){
		itemList.add(new CanDeleteItem(icon,name,number));
		setModelData();
	}
	
	/**
	 * ��������ģ��
	 *
	 */
	private void setModelData() {
		setModel(new DefaultListModel());
		DefaultListModel mod = (DefaultListModel) getModel();
		for(CanDeleteItem item : itemList){
			mod.addElement(item);
		}
	}

	/**
	 * ���ֲ�������
	 * 
	 * @param comp
	 * @param cont
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param anchor
	 * @param fill
	 * @param weightx
	 * @param weighty
	 */
	private void addWithGridBag(Component comp, Container cont, int x, int y,
			int width, int height, int anchor, int fill, int weightx,
			int weighty) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.anchor = anchor;
		gbc.fill = fill;
		gbc.weightx = weightx;
		gbc.weighty = weighty;
		cont.add(comp, gbc);
	}
	
	
	public static void main(String[] args){
		CanDeleteJList list = new CanDeleteJList();
		
		list.addCanDeleteItem("image/bg.jpg","��ˮ","786074249");
		list.addCanDeleteItem("image/bgImage.jpg","��ˮ","786074249");
		list.addCanDeleteItem("image/head.png","��ˮ","786074249");
		list.addCanDeleteItem("image/leaf.jpg","��ˮ","786074249");
		list.addCanDeleteItem("image/head.png","��ˮ","786074249");
		list.addCanDeleteItem("image/head.png","��ˮ","786074249");
		list.addCanDeleteItem("image/head.png","��ˮ","786074249");
		list.addCanDeleteItem("image/head.png","��ˮ","786074249");
		list.addCanDeleteItem("image/head.png","��ˮ","786074249");
		JScrollPane pain = new JScrollPane(list,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JFrame frame = new JFrame("StatusListJList");
		pain.setOpaque(false);
		frame.getContentPane().add(pain);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);
	}
	
}
