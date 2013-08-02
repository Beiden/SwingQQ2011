package org.fw;

import java.awt.Component;
import java.awt.Container;
import java.util.List;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.fw.cellrender.ImageIconCellRenderer;
import org.fw.data.ImageIconItem;

public class StatusListJList extends JList implements ListSelectionListener {

	private static final long serialVersionUID = -1404348552297425898L;


	private List<ImageIconItem> statusList = new ArrayList<ImageIconItem>();

	private JLabel statusLabel, iconLabel;// ״̬��ǩ,״̬ͼ��

	private OpaquePanel statueComp;// ״̬���

	protected String status;// ״̬��|״̬ͼ��|״̬����

	protected DropDownComponent drop_down_comp;// ������ť���

	public StatusListJList() {
		initComponents();
	}

	public StatusListJList(DropDownComponent drop_down_comp) {
		this.drop_down_comp = drop_down_comp;
		initComponents();

	}

	private void initComponents() {
		status = "";

		// ������Ԫ���
		statueComp = new OpaquePanel();
		// ͼ��
		iconLabel = new JLabel();
		// ����״̬
		statusLabel = new JLabel();
		
		statueComp.add(iconLabel);
		statueComp.add(statusLabel);

		opacify(statueComp);
		// ���õ�Ԫ����Ⱦ
		setCellRenderer(new ImageIconCellRenderer(statusLabel,iconLabel,statueComp));
		// �������
		setModelData();
		this.addListSelectionListener(this);

	}

	/**
	 * ���״̬��
	 * 
	 * @param status
	 *            ״̬����
	 * @param icon
	 *            ״̬ͼ��
	 * @param tooltip
	 *            ��ʾ
	 */
	public void addStatusItem(String status, String icon) {
		statusList.add(new ImageIconItem(status, icon));
		setModelData();
	}

	/**
	 * ����Opaqueֵ
	 * 
	 * @param prototype
	 */
	private void opacify(Container prototype) {
		Component[] comps = prototype.getComponents();
		for (int i = 0; i < comps.length; i++) {
			if (comps[i] instanceof JComponent)
				((JComponent) comps[i]).setOpaque(false);
		}
	}

	private void setModelData() {
		setModel(new DefaultListModel());
		DefaultListModel mod = (DefaultListModel) getModel();
		// ��������
		for (ImageIconItem item : statusList) {
			mod.addElement(item);
		}
	}

	

	public static void main(String[] args) {
		StatusListJList list = new StatusListJList();
		list.addStatusItem("QQ", "image/qq.png");
		list.addStatusItem("S", "image/qq.png");
		list.addStatusItem("QQ", "image/qq.png");
		JScrollPane pain = new JScrollPane(list,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JFrame frame = new JFrame("StatusListJList");
		pain.setOpaque(false);
		frame.getContentPane().add(pain);
		frame.pack();
		frame.setVisible(true);
	}

	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			
			for (int i = 0; i < getModel().getSize(); i++) {
				if (getSelectionModel().isSelectedIndex(i)) {

					String oldstatus = status;
					firePropertyChange("selectedStatus", oldstatus, i
							+ "��"
							+ ((ImageIconItem) getModel().getElementAt(i))
									.getIcon().toString()
							+ "��"
							+ ((ImageIconItem) getModel().getElementAt(i))
									.getStatus());
					return;
				}
			}
			
		}
	}
}
