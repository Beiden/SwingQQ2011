package org.fw.utils;

public class ProjectPath {

	/**
	 * ��ȡ��Ŀ·��
	 * @return
	 */
	public static String getProjectPath(){
		return System.getProperty("user.dir")+System.getProperty("file.separator");
	}
}
