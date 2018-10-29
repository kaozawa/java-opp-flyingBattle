package cn.tedu.shoot;

import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics;
/**飞行物*/
public abstract class FlyingObject {
	public static final int LIFE = 0;
	public static final int DEAD = 1;
	public static final int REMOVE = 2;
	protected int state = LIFE;//当前状态（默认为活着的）
	
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
/**专门给小敌机、大敌机、小蜜蜂、提供的*/	
	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;
		Random rand = new Random();//随机数对象		
		x = rand.nextInt(World.WIDTH-this.width);//x:0到（窗口宽-小敌机宽之内的随机数）
		y = -this.height;
	}
/**专门给英雄机、天空、子弹提供的*/
	public FlyingObject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
/** 读取图片信息*/	
	public static BufferedImage loadImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	
	
/**飞行物移动*/
	public abstract void step() ;
	
/**获取对象的图片*/
	public abstract BufferedImage getImage();
	
/**判断是否是活着的*/
	public boolean isLife() {
		return state == LIFE;//当前状态为LIFE则表达式为TRUE
	}
/**判断是否是死了的*/	
	public boolean isDead() {
		return state == DEAD;//当前状态为DEAD则表达式为TRUE
	}
/**判断是否是删除的*/
	public boolean isRemove() {
		return state == REMOVE;//当前状态为REMOVE则表达式为TRUE
	}
/**画对象 g：画笔*/
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
	}
/**检测是否越界*/
	public boolean outOfBounds() {
	//	return this.y>=500;//测试用
		return this.y>=World.HEIGHT;
	}
	
	public boolean hit(FlyingObject other) {
		int x1 = this.x - other.width;
		int x2 = this.x + this.width;
		int y1 = this.y - other.height;
		int y2 = this.y + this.height;
		int x = other.x;
		int y = other.y;
		 
		return x>x1 && x<x2 && y>y1 && y<y2;
	}
	public Bullet[] shoot() {
			Bullet[] bs = new Bullet[1];
			return bs;
	}
	
	public void goDead() {
		state = DEAD;
	}
		
}
