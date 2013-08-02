package org.fw.event;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

//import org.apache.log4j.Logger;
//import org.fw.FramePanel;
/**
 * �϶�����¼�
 * @author Administrator
 *
 */
public class MoveMouseListener implements MouseListener, MouseMotionListener {

//	private static Logger log = Logger.getLogger(FramePanel.class);
	
	private int RANGE = 4;//��Ե������Χ
	private int MIN_WIDTH = 256;//��С���
	private int MIN_HEIGHT = 256;//��С�߶�
	
	JComponent target;// ������Ŀ�����
	Point start_drag;// ��ʼ����Ŀ������ǵ������ʼλ��
	Point now_loc;//Ŀ�����������λ��
	
	int width;//���ڿ��
	int height;//���ڸ߶�
	int x_left;//�����x����ֵ
	int x_right;//���ұ�x����ֵ
	int y_up;//���ϱ�y����ֵ
	int y_down;//���±�y����ֵ
	int x;//���x����
	int y;//���y����
	
	Rectangle nw,ne,sw,se;//�ĸ��ǵ�����������
	
	boolean canResize;//���Ըı��С
	boolean canMove;//�����ƶ�
	boolean Resizing;//���ڸı��С��״��
	Boolean Moving;//
	

	public MoveMouseListener(JComponent target) {
		this.target = target;
		canResize = true;
		canMove = true;
		Resizing = false;
		Moving = true;
	}

	public static JFrame getFrame(Container target) {
		if (target instanceof JFrame) {
			return (JFrame) target;
		}
		return getFrame(target.getParent());
	}
	
	/**
	 * ��ȡ��ǰ״̬���������Ļ�ϵ�׼ȷλ��
	 * @param e
	 * @return ��������
	 */
	Point getScreenLocation(MouseEvent e) {
		Point cursor = e.getPoint();
		Point target_location = this.target.getLocationOnScreen();
		return new Point((int) (target_location.getX() + cursor.getX()),
				(int) (target_location.getY() + cursor.getY()));
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		
		setParameterValues(e);
		
		setCurrentCursorType();
		
	}
	
	@SuppressWarnings("static-access")
	private void setCurrentCursorType() {
		JFrame frame = this.getFrame(target);
		Cursor cursorType = frame.getCursor();
		
		if(this.isCanResize()){
			
			if(nw.contains(x, y)){//���Ͻ�
				frame.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
			}else if(this.x_left != x && this.x_right != x && isBetween(y,this.y_up,RANGE)){//��
				frame.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
			}else if(ne.contains(x, y)){//���Ͻ�
				frame.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
			}else if(isBetween(x,this.x_right,RANGE) && this.y_up != y && this.y_down != y){//��
				frame.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
			}else if(se.contains(x, y)){//���½�
				frame.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
			}else if(this.x_left != x && this.x_right != x && isBetween(y,this.y_down,RANGE)){//��
				frame.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
			}else if(sw.contains(x, y)){//���½�
				frame.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
			}else if(isBetween(x,this.x_left,RANGE) && this.y_up != y && this.y_down != y){//��
				frame.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
			}else{
				frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
		
		if(Resizing){
			frame.setCursor(cursorType);
		}
	}

	@SuppressWarnings("static-access")
	private void setParameterValues(MouseEvent e) {
		//��ȡ���λ��
		this.start_drag = this.getScreenLocation(e);
		//��ȡ����λ��
		this.now_loc = this.getFrame(this.target).getLocation();
		
		if(this.isCanResize()){
			this.width = this.getFrame(target).getWidth();
			this.height = this.getFrame(target).getHeight();
			this.x_left = (int)now_loc.getX();
			this.x_right = x_left+width;
			this.y_up = (int)now_loc.getY();
			this.y_down = y_up+height;
			nw = new Rectangle(now_loc.x,now_loc.y,RANGE*2,RANGE*2);
			ne = new Rectangle(now_loc.x+width-RANGE*2,now_loc.y,RANGE*2,RANGE*2);
			sw = new Rectangle(now_loc.x,now_loc.y+height-RANGE*2,RANGE*2,RANGE*2);
			se = new Rectangle(now_loc.x+width-RANGE*2,now_loc.y+height-RANGE*2,RANGE*2,RANGE*2);
			x = (int)this.getScreenLocation(e).getX();
			y = (int)this.getScreenLocation(e).getY();
		}
	}

	/**
	 * x�Ƿ���y�����ҷ�Χ��
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isBetween(int x, int y,int range) {
		if(x>y-range&&x<y+range){
			return true;
		}else{
			return false;
		}
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		setParameterValues(e);
	}

	public void mouseReleased(MouseEvent e) {
		Resizing = false;
		Moving = true;
	}

	@SuppressWarnings({ "static-access", "deprecation" })
	public void mouseDragged(MouseEvent e) {
		Point current = this.getScreenLocation(e);
		Point offset = new Point(
				(int) current.getX() - (int) start_drag.getX(), (int) current
						.getY()
						- (int) start_drag.getY());
		JFrame frame = this.getFrame(target);
		//�жϵ�ǰ״̬
		if(this.getFrame(target).getCursor().getType() == Cursor.DEFAULT_CURSOR){
			Moving = true;
			Resizing = false;
		}else{
			Moving = false;
			Resizing = true;
		}
		
		//��������ƶ�
		if(this.isCanMove() && Moving && !Resizing ){
			Point new_location = new Point((int) (this.now_loc.getX() + offset
					.getX()), (int) (this.now_loc.getY() + offset.getY()));
			frame.setLocation(new_location);
		}
		//������Ըı��С
		if(this.isCanResize() && !Moving && Resizing){
			JFrame jFrame = this.getFrame(target);
		
			if(jFrame.getCursorType() == Cursor.NW_RESIZE_CURSOR){
				int px = now_loc.x + width;
				int py = now_loc.y + height;
				jFrame.setSize(checkMin(px-e.getXOnScreen(),MIN_WIDTH),checkMin(py-e.getYOnScreen(),MIN_HEIGHT));
				jFrame.setLocation(e.getXOnScreen(),e.getYOnScreen());
			}else if(jFrame.getCursorType() == Cursor.N_RESIZE_CURSOR){
				int py = now_loc.y+height;
				jFrame.setSize(checkMin(this.width,MIN_WIDTH),checkMin(py-e.getYOnScreen(),MIN_HEIGHT));
				jFrame.setLocation(this.now_loc.x,e.getYOnScreen());
			}else if(jFrame.getCursorType() == Cursor.NE_RESIZE_CURSOR){
				int py = now_loc.y+height;
				this.width = e.getXOnScreen() - now_loc.x;
				jFrame.setSize(checkMin(this.width,MIN_WIDTH),checkMin(py-e.getYOnScreen(),MIN_HEIGHT));
				jFrame.setLocation(this.now_loc.x,e.getYOnScreen());
			}else if(jFrame.getCursorType() == Cursor.E_RESIZE_CURSOR){
				this.width = e.getXOnScreen() - now_loc.x;
				jFrame.setSize(checkMin(this.width,MIN_WIDTH),checkMin(this.height,MIN_HEIGHT));
			}else if(jFrame.getCursorType() == Cursor.SE_RESIZE_CURSOR){
				this.width = e.getXOnScreen()- now_loc.x;
				this.height =e.getYOnScreen()- now_loc.y;
				jFrame.setSize(checkMin(this.width,MIN_WIDTH),checkMin(this.height,MIN_HEIGHT));
			}else if(jFrame.getCursorType() == Cursor.S_RESIZE_CURSOR){
				this.height = e.getYOnScreen() - now_loc.y;
				jFrame.setSize(checkMin(this.width,MIN_WIDTH),checkMin(this.height,MIN_HEIGHT));
			}else if(jFrame.getCursorType() == Cursor.SW_RESIZE_CURSOR){
				int px = now_loc.x + width;
				this.height = e.getYOnScreen() - now_loc.y;
				jFrame.setSize(checkMin(px-e.getXOnScreen(),MIN_WIDTH),checkMin(this.height,MIN_HEIGHT));
				jFrame.setLocation(e.getXOnScreen(),this.now_loc.y);
			}else if(jFrame.getCursorType() == Cursor.W_RESIZE_CURSOR){
				int px = now_loc.x + width;
				jFrame.setSize(checkMin(px-e.getXOnScreen(),MIN_WIDTH),checkMin(this.height,MIN_HEIGHT));
				jFrame.setLocation(e.getXOnScreen(),this.now_loc.y);
			}
			
		}
	}

	/**
	 * ��ֵ֤������Сֵ
	 * @param value
	 * @param min
	 * @return
	 */
	private int checkMin(int value, int min) {
		if(value<min){
			return min;
		}else{
			return value;
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(Moving){
			setParameterValues(e);
		}
		setCurrentCursorType();
	}

	public boolean isCanResize() {
		return canResize;
	}

	public void setCanResize(boolean canResize) {
		this.canResize = canResize;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}
}
