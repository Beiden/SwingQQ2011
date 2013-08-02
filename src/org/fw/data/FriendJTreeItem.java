package org.fw.data;

import java.util.List;

public class FriendJTreeItem {

	public static final int BIG_ICON = 1;// ��ͷ��

	public static final int NORMAL_ICON = 2;// ��׼ͷ��

	public static final int SMALL_ICON = 3;// Сͷ��

	private int displayStyle;// ��ʾ��ʽ

	private String icon;// ͷ��

	private String nickName;// �ǳ�

	private String remarkName;// ��ע

	private String qqNumber;// QQ����

	private String currentApp;// ��ǰӦ��

	private String signature;// ����ǩ��

	private List<String> applyList;// Ӧ���б�

	private String tip;// ��ʾ��Ϣ
	
	private int statue;//״̬
	
	private String ip;//ip��ַ
	private int port;//�˿�
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	private String headInfo;//���ڵ�(������ʾ�ҵĺ���)

	public FriendJTreeItem() {

	}

	public FriendJTreeItem(String headInfo,int displayStyle, String icon, String nickName,
			String remarkName, String qqNumber, String currentApp,
			String signature, List<String> applyList, String tip,int statue,String ip,int port) {
		this.headInfo = headInfo;
		this.displayStyle = displayStyle;
		this.icon = icon;
		this.nickName = nickName;
		this.remarkName = remarkName;
		this.qqNumber = qqNumber;
		this.currentApp = currentApp;
		this.signature = signature;
		this.applyList = applyList;
		this.tip = tip;
		this.statue = statue;
		this.ip = ip;
		this.port = port;
	}

	public List<String> getApplyList() {
		return applyList;
	}

	public void setApplyList(List<String> applyList) {
		this.applyList = applyList;
	}

	public String getCurrentApp() {
		return currentApp;
	}

	public void setCurrentApp(String currentApp) {
		this.currentApp = currentApp;
	}

	public int getDisplayStyle() {
		return displayStyle;
	}

	public void setDisplayStyle(int displayStyle) {
		this.displayStyle = displayStyle;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public String getRemarkName() {
		return remarkName;
	}

	public void setRemarkName(String remarkName) {
		this.remarkName = remarkName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public int getStatue() {
		return statue;
	}

	public void setStatue(int statue) {
		this.statue = statue;
	}

	public String getHeadInfo() {
		return headInfo;
	}

	public void setHeadInfo(String headInfo) {
		this.headInfo = headInfo;
	}

	public String toString(){
		//  �ǳƣ���ע������ǩ����״̬��ip,���ڵ�(������ʾ�ҵĺ���),ͷ��qq�ţ��Է��˿ں�
		return nickName+","+remarkName+","+signature+","+statue+","+ip+","+headInfo+","+icon+","+qqNumber+","+port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
