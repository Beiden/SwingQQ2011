package org.fw.qq.plugins.filetransfer;

import java.io.Serializable;

public class FileCarrier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8211632526317273714L;
	/**
	 * �ļ���
	 */
	private String fileName;
	/**
	 * ƫ����
	 */
	private long pos;
	/**
	 * ��ǰ�鳤��
	 */
	private long curBlockLength;
	/**
	 * �ļ��ܳ���
	 */
	private long totalLength;
	/**
	 * ��������
	 */
	private byte[] content;

	public long getCurBlockLength() {
		return curBlockLength;
	}
	public void setCurBlockLength(long curBlockLength) {
		this.curBlockLength = curBlockLength;
	}
	public long getPos() {
		return pos;
	}
	public void setPos(long pos) {
		this.pos = pos;
	}
	public long getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(long totalLength) {
		this.totalLength = totalLength;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
