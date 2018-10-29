package cn.tedu.shoot;

import java.awt.image.BufferedImage;
/**子弹*/
public class Bullet extends FlyingObject{
	private static BufferedImage image;
	static {
		image = loadImage("bullet.png");	
	}
	
	private int speed;//移动速度
	//构造方法
	public Bullet(int x,int y){
		super(8,14,x,y);
		speed = 3;
	}
	/**
	 * 子弹移动
	 */
	public void step() {
		y-=speed;
	}
	
	/**重写getImage（）获取图片*/
	public BufferedImage getImage() {
		if(this.isLife()){//活着的
			return image;
		}else if(isDead()) {//死了的，
			state = REMOVE;		
		}
		return null;//删除的
	}
	/*
	 * 活着的，返回image
	 * 死了的，状态改为删除并返回null
	 * 删除的，返回null
	 */
	
	public boolean outOfBounds() {
		return this.y<= -this.height;
	}
}
