package org.fw.test;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.fw.OpaqueButton;
import org.fw.OpaquePanel;
import org.fw.cellrender.FriendJTreeCellRenderer;
import org.fw.data.FriendJTreeItem;
import org.fw.utils.Config;
import org.fw.utils.ProjectPath;

public class TestJTree extends JFrame{

	private static final long serialVersionUID = -6786533183043245749L;
	protected OpaquePanel contentPanel;//����
	protected JLabel iconLabel;//ͷ��
	protected JLabel nickNameLabel;//�ǳ�
	protected OpaqueButton currentAppBtn;//��ǰӦ��
	protected JLabel signatureLabel;//����ǩ��
	protected List<OpaqueButton> applyList;//Ӧ���б�
	
	public TestJTree(){
//		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
//
//		try {
//			UIManager.setLookAndFeel(lookAndFeel);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			e.printStackTrace();
//		}
//		UIManager.getDefaults().put("Tree.lineTypeDashed", Boolean.TRUE); 
		Config cfg = new Config(ProjectPath.getProjectPath()
				+ "cfg/basic.properties");
		UIManager.getDefaults().put("Tree.leafIcon", null);
		UIManager.getDefaults().put("Tree.openIcon", new ImageIcon(cfg.getProperty("JTree.openIcon", "skin/default/openIcon.png")));
	    UIManager.getDefaults().put("Tree.closedIcon", new ImageIcon(cfg.getProperty("JTree.closeIcon", "skin/default/closeIcon.png")));
	    
		contentPanel = new OpaquePanel();
		contentPanel.setOpaque(false);
		iconLabel = new JLabel();
		nickNameLabel = new JLabel();
		currentAppBtn = new OpaqueButton();
		signatureLabel = new JLabel();
		applyList = null;
		contentPanel.add(iconLabel);
		contentPanel.add(nickNameLabel);
		contentPanel.add(currentAppBtn);
		contentPanel.add(signatureLabel);
		
		FriendJTreeItem i1 = new FriendJTreeItem("�ҵĺ���[0/2]",2,"skin/default/head.png",
				"��ˮ1","������","786074249","skin/default/music.png","ʲô״����",null,"",1,"0.0.0.0",8080);
		FriendJTreeItem i2 = new FriendJTreeItem("�ҵĺ���[0/2]",3,"skin/default/head.png",
				"��ˮ2","������","786074249","skin/default/music.png","ʲô״����",null,"",1,"0.0.0.0",8080);
		FriendJTreeItem i3 = new FriendJTreeItem("�ҵĺ���[0/2]",3,"skin/default/head.png",
				"��ˮ3","������","786074249","skin/default/music.png","ʲô״����",null,"",1,"0.0.0.0",8080);
		FriendJTreeItem i4 = new FriendJTreeItem("�ҵĺ���[0/2]",3,"skin/default/head.png",
				"��ˮ4","������","786074249","skin/default/music.png","ʲô״����",null,"",1,"0.0.0.0",8080);
		FriendJTreeItem i5 = new FriendJTreeItem("�ҵĺ���[0/2]",3,"skin/default/head.png",
				"��ˮ5","������","786074249","skin/default/music.png","ʲô״����",null,"",1,"0.0.0.0",8080);
		DefaultMutableTreeNode  root = new DefaultMutableTreeNode(i1);
		DefaultMutableTreeNode in2 = new DefaultMutableTreeNode(i2);
		DefaultMutableTreeNode in3 = new DefaultMutableTreeNode(i3);
		DefaultMutableTreeNode in4 = new DefaultMutableTreeNode(i4);
		DefaultMutableTreeNode in5 = new DefaultMutableTreeNode(i5);
		root.isRoot();
		root.add(in2);
		in2.add(in4);
		in2.add(in3);
		root.add(in5);
		JTree jtree  = new JTree(); 
		DefaultTreeModel model = new DefaultTreeModel(root,true);
		jtree.setModel(model);
		
//		UIManager.getDefaults().put("Tree.lineTypeDashed", Boolean.TRUE); 
//		Config cfg = new Config(ProjectPath.getProjectPath()
//				+ "cfg/basic.properties");
		FriendJTreeCellRenderer jcell = new FriendJTreeCellRenderer(contentPanel,iconLabel,nickNameLabel,currentAppBtn,signatureLabel,applyList,null);
		jcell.setLeafIcon(null);
		jcell.setClosedIcon(new ImageIcon(cfg.getProperty("JTree.closeIcon", "skin/default/closeIcon.png")));
		jcell.setOpenIcon(new ImageIcon(cfg.getProperty("JTree.openIcon", "skin/default/openIcon.png")));
		jtree.setCellRenderer(jcell);
		   
//		  jScrollPane1.getViewport().add(jTree1,   null);//ע�⣺******
//		jScrollPane1.getViewport().removeAll(); 
		//ȥ����
		jtree.putClientProperty("JTree.lineStyle",   "None");
		
		//������ʾ
//		jtree.setRootVisible(false);
		JScrollPane j = new JScrollPane();
		j.getViewport().add(jtree,   null);
		
		this.getContentPane().add(j);
		this.setSize(400,200);
		this.setVisible(true);
	}
	
	public static void main(String args[]){
		TestJTree t = new TestJTree();
		t.setDefaultCloseOperation(3);
		
	}
}
