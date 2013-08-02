package org.fw.qq.plugins.screencut;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class MyClipboard {
	/**
	 * ��ͼƬ���浽��������
	 * 
	 * @param image
	 *            Ҫ�����ͼƬ
	 */
	public static void setClipboardImage(final Image image) {
		Transferable trans = new Transferable() {
			// ���� DataFlavor ��������飬ָʾ�������ṩ���ݵ� flavor
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.imageFlavor };
			}

			// ���ش˶����Ƿ�֧��ָ�������� flavor
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return DataFlavor.imageFlavor.equals(flavor);
			}

			// ����һ�����󣬸ö����ʾ��Ҫ�����������
			public Object getTransferData(DataFlavor flavor)
					throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor))
					return image;
				throw new UnsupportedFlavorException(flavor);
			}

		};
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(trans, null);
	}

	/**
	 * ��ͼƬ���浽��������
	 * 
	 * @param image
	 *            Ҫ�����ͼƬ
	 */
	public static void setClipboardString(final String str) {
		Transferable trans = new Transferable() {
			// ���� DataFlavor ��������飬ָʾ�������ṩ���ݵ� flavor
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { DataFlavor.stringFlavor };
			}

			// ���ش˶����Ƿ�֧��ָ�������� flavor
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return DataFlavor.stringFlavor.equals(flavor);
			}

			// ����һ�����󣬸ö����ʾ��Ҫ�����������
			public Object getTransferData(DataFlavor flavor)
					throws UnsupportedFlavorException, IOException {
				if (isDataFlavorSupported(flavor))
					return str;
				throw new UnsupportedFlavorException(flavor);
			}

		};
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(trans, null);
	}
	
	/**
	 * �Ӽ������ȡͼƬ
	 * 
	 * @return �������е�ͼƬ
	 * @throws Exception
	 */
	public static Image getImageFromClipboard() throws Exception {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// ��ȡ���а��е�����
		Transferable pic = clipboard.getContents(null);
		if (pic != null) {
			// ��������Ƿ���ͼƬ����
			if (pic.isDataFlavorSupported(DataFlavor.imageFlavor))
				return (Image) pic.getTransferData(DataFlavor.imageFlavor);
		}
		return null;
	}
	
	/**
	 * �Ӽ������ȡ�ַ���
	 * @return �������е��ַ���
	 * @throws Exception
	 */
	public static String getTestFromClipboard() throws Exception {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		// ��ȡ���а��е�����
		Transferable str = clipboard.getContents(null);
		if (str != null) {
			// ��������Ƿ����ı�����
			if (str.isDataFlavorSupported(DataFlavor.stringFlavor))
				return (String) str.getTransferData(DataFlavor.stringFlavor);
		}
		return null;
	}

}
