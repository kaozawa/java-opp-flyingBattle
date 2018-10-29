package cn.tedu.shoot;

import java.awt.image.BufferedImage;
import java.util.Random;

/** 小蜜蜂*/
public class Bee extends FlyingObject implements Award{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++) {
			images[i] = loadImage("bee"+i+".png");
		}
	}
	

	private int xSpeed;//x坐标移动速度
	private int ySpeed;//y坐标移动速度
	private int awardType;//奖励类型
	//小蜜蜂构造方法
	public Bee(){
		super(60,50);
		Random rand = new Random();
		xSpeed = 1;
		ySpeed = 2;
		awardType = rand.nextInt(2);//0或1
	}
	/**
	 * 小蜜蜂移动
	 */
	public void step() {
		x+= xSpeed;
		y+= ySpeed;
		if(x<=0||x>=World.WIDTH-this.width) {
			xSpeed*=-1;
		}
	}
	/**重写getImage（）*/
	int index = 1;
	public BufferedImage getImage() {
		if(isLife()) {
			return images[0];
		}else if(isDead()){
			/*BufferedImage img = images[index++];
			if(index == images.length) {
				state = REMOVE;
			}
			return img;
			*/
			if(index == images.length) {
				state = REMOVE;
				return null;
			}
			return images[index++];

			
			/*
			 * 									index = 1
			 * 10M img=images[1]	index = 2 返回images[1]
			 * 20M img=images[2] index = 3 返回images[2]
			 * 30M img=images[3] index = 4 返回images[3]
			 * 40M img=images[4] index = 5 (REMOVE)返回images[4]
			 * 50M 返回null
			 */
		}
		return null;
		
	}
	
	public int getAwardType() {
		return awardType;//返回奖励类型
	}
}
