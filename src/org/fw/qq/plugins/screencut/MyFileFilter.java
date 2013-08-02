package org.fw.qq.plugins.screencut;


import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileFilter;

/**
 * �ļ�������
 * @author ��ˮ
 *
 */
public class MyFileFilter extends FileFilter{

	private String desc;
	
	private ArrayList<String> filters = new ArrayList<String>();
	
	public MyFileFilter()
	{
		
	}
	

	public MyFileFilter(String filter)
	{
		filters.add(filter);
	}
	

	public MyFileFilter(String filter,String desc)
	{
		filters.add(filter);
		this.desc = desc;
	}
	
	@Override
	public boolean accept(File arg0) {
		String fileName = arg0.getName().toLowerCase();
		//���С�ڵ���0��ʾ�����ļ�
		if(fileName.length()==0)
		{
			return true;
		}
		if(arg0.isDirectory())
		{//��ʾ�ļ���
			return true;
		}
		//ѭ��ƥ������ļ�
		for(int i=0;i<filters.size();i++){
			String fileExt = filters.get(i);
			if(fileName.endsWith(fileExt)){
				return true;
			}
		}
		return false;
	}
	/**
	 * ��ӹ�����
	 * @param str ���������� ����".jpg"
	 */
	public void addFilter(String str){
		this.filters.add(str);
	}
	/**
	 * ��ӹ�����
	 * @param str ���������� ����".jpg"
	 * @param desc �˹����������������磺"JPG Images" 
	 */
	public void addFilter(String str,String desc)
	{
		this.filters.add(str);
		this.desc = desc;
	}
	/**
	 * ��ӹ�����
	 * @param str �ݶ�� ���������� ����:{".jpg",".gif"}
	 * @param desc �˹����������������磺"JPG and GIF Images"
	 */
	public void addFilter(String[] str,String desc){
		for(int i=0;i<str.length;i++)
		{
			System.out.println(str.length);
			this.filters.add(str[i]);
		}
		this.desc = desc;
	}
	/**
	 * �Ƴ�������
	 * @param index Ҫ�Ƴ������
	 */
	public void removeFilter(int index){
		this.filters.remove(index);
	}
	
	/**
	 * ��չ�����
	 */
	public void removeAllFilter(){
		this.filters.removeAll(filters);
	}
	/**
	 * ���ù���������
	 * @param desc ����
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}


	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return desc;
	}

}
