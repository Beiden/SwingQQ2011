package org.fw.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Config {

	private static Logger log = Logger.getLogger(Config.class);
	
	private String path;

	private Properties config;
	
	

	public Config() {
	}

	public Config(String path) {
		config = new Properties();
		setPath(path);
	}

	/**
	 * ���� Properties ���е������б�����Ԫ�ضԣ�д�������
	 * 
	 * @param comments
	 */
	public void saveConfig(String comments) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			config.store(fos, comments);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("��ȡ�����ļ�ʧ��");
		}
		if (fos != null) {
			try {
				fos.close();
			} catch (Exception e) {
				log.info("�޷��ر�������");
				e.printStackTrace();
			}
		}
	}

	public void savePropertie(String key, String value, String comments) {
		setPropertie(key, value);
		saveConfig(comments);
	}

	public void savePropertie(String key, String value) {
		savePropertie(key, value, null);
	}

	private void setPropertie(String key, String value) {
		config.setProperty(key, value);
	}
	/**
	 * ��ȡ����ֵ
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return config.getProperty(key);
	}
	/**
	 * ��ȡ����ֵ��Ϊ���򷵻�Ĭ��ֵ
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		value = value != null ? value : defaultValue;
		return value;
	}

	public void remove(Object key) {
		config.remove(key);
	}

	public void removeAndSave(Object key) {
		remove(key);
		saveConfig();
	}

	public void saveConfig() {
		saveConfig(null);
	}

	/**
	 * ���������ļ�·��
	 * 
	 * @param path
	 */
	private void setPath(String path) {
		this.path = path;
		checkExist();
		loadConfig();
	}

	public String getPath() {
		return path;
	}

	/**
	 * ���������ļ�
	 * 
	 */
	private void loadConfig() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			config.clear();
			config.load(fis);
		} catch (Exception e) {
			log.info("���������ļ�ʱ�����쳣");
			e.printStackTrace();
		}
		if (fis != null) {
			try {
				fis.close();
			} catch (Exception e) {
				log.info("�޷��ر�������");
				e.printStackTrace();
			}
		}
	}

	/**
	 * ����Ƿ���ڸ��ļ�
	 * 
	 */
	private void checkExist() {
		File file = new File(path);
		File parent = file.getParentFile();
		if (!parent.exists())
			parent.mkdirs();
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.info("�����µ������ļ�ʧ��");
				e.printStackTrace();
			}
		}
	}
	
}
