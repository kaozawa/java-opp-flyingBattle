package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 英雄机
 * @author soft01
 *
 */
public class Hero extends FlyingObject{
	private static BufferedImage[] images;
	static {
		images = new BufferedImage[2];
		images[0] = loadImage("hero0.png");
		images[1] = loadImage("hero1.png");
	}
	
	
	
	
	private int life;//英雄机的命
	private int doubleFire;//火力值
	public Hero(){
		super(97,124,140,400);
		life = 3;
		doubleFire = 100000;
	}
	
	public void step() {
		System.out.println("英雄机切换图片啦！");
	}
	
	int index = 0;
	/**重写getImage（）方法*/
	public BufferedImage getImage() {//每10个毫秒走一次
		if(this.isLife()) {//若活着的
			return images[index++%images.length];
		}
		return null;
		/*
		 * 						index=0;
		 * 10M 返回images[0] index =1；
		 * 20M 返回images[1] index =2;
		 * 30M 返回images[0] index =3;
		 * 40M 返回images[1] index =4;
		 * 50M 返回images[0] index =5;
		 */
		
		
		
	}
/**英雄机发射子弹（生成子弹对象）*/	
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire>10000) {
			Bullet[] bs = new Bullet[4];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep);
			bs[2] = new Bullet(this.x-1*xStep,this.y-yStep);
			bs[3] = new Bullet(this.x+5*xStep,this.y-yStep);
			return bs;
		}
		else if(doubleFire>1000) {//双
			Bullet[] bs = new Bullet[3];
			bs[0] = new Bullet(this.x+0*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+2*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+4*xStep,this.y-yStep);
			return bs;
		}else if(doubleFire>100){//单
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep);
			return bs;
		}else {
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x+2*xStep,this.y-yStep);
			return bs;
		}
			
	}
	
	
	public void moveTo(int x,int y) {
		//英雄机跟随鼠标移动
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}
	/*
	public void delay(int xms) {
		int x,y;
		for(x=xms;x>0;y--) {
			for(y=110;y>0;y--);
		}
	}
	*/
	
	public void addLife() {
		life++;
	}
	public  int getLife() {
		return life;
	}
	public void addDoubleFire() {
		doubleFire+=40;
	}
	
	public void subtractLife() {
		life--;
	}
	public void clearDoublieFire() {
		doubleFire = 0;
	}
}
