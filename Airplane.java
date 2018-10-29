package cn.tedu.shoot;
import java.awt.image.BufferedImage;

public class Airplane extends FlyingObject implements Enemy{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		for(int i=0;i<images.length;i++) {
			images[i] = loadImage("airplane"+i+".png");
		}
	}
	private int speed;//移动速度
	//构造方法
	public Airplane(){
		super(49,36);
		speed = 2;
	}

	
	
	/**
	 * 小敌机移动
	 */
	public void step() {
		y+=speed;
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
	
	public int getScore() {
		return 1;
	}
}
