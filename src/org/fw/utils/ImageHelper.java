package org.fw.utils;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageHelper {

	/**
	 * ��ȡͼƬ
	 * @param path ͼƬ·��
	 * @return
	 */
	public Image getFWImage(String path){
		ImageIcon image = new ImageIcon(path);
		return image.getImage();
	}
	/**
	 * ��ȡͼƬicon
	 * @param path ͼƬ·��
	 * @return
	 */
	public ImageIcon getFWImageIcon(String path){
		ImageIcon image = new ImageIcon(path);
		return image;
	}
}
