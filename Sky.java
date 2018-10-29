package cn.tedu.shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
/**天空：是飞行物*/
public class Sky  extends FlyingObject{
	private static BufferedImage image;
	static {
		image = loadImage("background.png") ;
	}
		
	
	private int speed;//移动速度
	private int y1;//第二章图片的y坐标

	public Sky(){
		super(World.WIDTH,World.HEIGHT,0,0);
		y1 =-World.HEIGHT;
		speed = 1;
	}
	/**
	 *天空移动
	 */
	public void step() {
		y+=speed;
		y1+=speed;
		if(y>=World.HEIGHT) {
			y=-World.HEIGHT;
		}
		if(y1>=World.HEIGHT) {
			y1=-World.HEIGHT;
		}
	}
	
	/**重写天空图片*/
	public BufferedImage getImage() {
		return image;//直接返回image即可
	}
	/**画对象 g：画笔*/
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
}
