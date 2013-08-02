package org.fw;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.fw.cellrender.CanDeleteCellRenderer;
import org.fw.data.CanDeleteItem;

public class CanDeleteJComboBox extends JComboBox{

	private static final long serialVersionUID = -5633769966703909074L;

	private List<CanDeleteItem> itemList = new ArrayList<CanDeleteItem>();

	private JLabel iconLabel, nameLabel, numberLabel;

	private OpaquePanel contentPanel;// ״̬���
	
	
	public CanDeleteJComboBox(){
		this.setEditable(true);
		
		initComponents();
	}
	
	private void initComponents() {
		
		//���õ�Ԫ�񲼾�
		contentPanel = new OpaquePanel();
		contentPanel.setLayout(new GridBagLayout());
		iconLabel = new JLabel();
		
		nameLabel = new JLabel();
		
		numberLabel = new JLabel();
		
		
		
		//���õ�Ԫ����Ⱦ��
		setRenderer(new CanDeleteCellRenderer(contentPanel,iconLabel,nameLabel,numberLabel));
		
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
		this.setModel(new DefaultComboBoxModel());
		DefaultComboBoxModel model = (DefaultComboBoxModel)this.getModel();
		for(CanDeleteItem item : itemList){
			model.addElement(item);
		}
	}
	
	public static void main(String args[]){
		final CanDeleteJComboBox list = new CanDeleteJComboBox();
		list.addCanDeleteItem("image/bg.jpg","��ˮ","786074249");
		list.addCanDeleteItem("image/bgImage.jpg","��ˮ","123456799");
		list.addCanDeleteItem("image/head.png","��ˮ","56464566");
		list.addCanDeleteItem("image/leaf.jpg","��ˮ","94984656");
		list.addCanDeleteItem("image/head.png","��ˮ","25564948");
		list.addCanDeleteItem("image/head.png","��ˮ","15649462");
		list.addCanDeleteItem("image/head.png","��ˮ","789456325");
		list.addCanDeleteItem("image/head.png","��ˮ","895465498");
		list.addCanDeleteItem("image/head.png","��ˮ","648991618");
		list.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				System.out.println(((CanDeleteItem)list.getSelectedItem()).getNumber());
			}
			
		});
		
		JFrame frame = new JFrame("StatusListJList");
		frame.getContentPane().add(list);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);
	}
}
