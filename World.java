package cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**整个游戏世界*/
public class World extends JPanel{
	public static final int WIDTH = 400;//窗口的宽
	public static final int HEIGHT = 700;//窗口的高
	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER= 3;
	private int state = START;
			
	private static BufferedImage start;
	private static BufferedImage pause;
	private static BufferedImage gameOver;
	static {
		start = FlyingObject.loadImage("start.png");
		pause = FlyingObject.loadImage("pause.png");
		gameOver = FlyingObject.loadImage("gameover.png");
	}
	
	
	public static int model = 0 ;
	
	public  int score = 0;
	
	private Sky sky = new Sky();
	private Hero hero = new Hero();
	private FlyingObject[] enemies = {
	
	};
	private Bullet[] bullets = {
		
	};//为了避免空指针错误
	public void initial() {
		model = 0 ;
		score = 0; 
		sky = new Sky();
		hero = new Hero();
		enemies = new FlyingObject[0];
		bullets = new Bullet[0];
	}
	public FlyingObject nextOne() {//随机生成敌人
		Random rand = new Random();
		int type = rand.nextInt(100);
		if(type<10) {
			return new Bee();
		}else if(type<70) {
			return new Airplane();
		}else {
			return new BigAirplane();
		}
	}
	
	int enterIndex = 0 ;
	/**敌人（小敌机、大敌机、小蜜蜂）入场*/
	public void enterAction() {//每10ms走一次
		enterIndex++;
		if(enterIndex%40==0) {
			FlyingObject obj = nextOne();
			enemies = Arrays.copyOf(enemies,enemies.length+1);
			enemies[enemies.length -1] = obj;
		}
	}
	
	int shootIndex = 0 ;
	public void shootAction() {
		shootIndex++;
		if(shootIndex%20==0) {
			Bullet[] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
			
		}
	}
	
	public void bulletBangAction() {
		
		for(int j=0;j<bullets.length;j++) {
			Bullet b = bullets[j];
			for(int i=0;i<enemies.length;i++) {
				FlyingObject f = enemies[i];
		//	System.out.println(f.y+","+b.y+"("+(f.y-14)+","+(f.y+f.height)+")"+f.hit(b));
				if(f.isLife() && b.isLife() && f.hit(b)) {
					f.goDead();
					b.goDead();	
					if(f instanceof Enemy) {
						Enemy e = (Enemy)f;
						score += e.getScore();
					}
					if(f instanceof Award) {
						Award a = (Award)f;
						int type = a.getAwardType();
						switch(type) {
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();
							break;
						case Award.LIFE:
							hero.addLife();
							break;
						default:break;
						}
					}
				}
			}
		}
	}
	public void heroBangAction() {
		for(int i=0;i<enemies.length;i++) {
			FlyingObject f = enemies[i];
			if(f.isLife() && hero.isLife() && f.hit(hero)) {
				f.goDead();
				hero.clearDoublieFire();
				hero.subtractLife();
			}
		}
	}
	
	
	public void stepAction() {
		sky.step();
		for(int i=0;i<enemies.length;i++) {
			enemies[i].step();
		}
		for(int i=0;i<bullets.length;i++) {
			bullets[i].step();
		}
	}
	
	public void outOfBoundAction() {
		int index = 0 ;
		FlyingObject[] enemyLives = new FlyingObject[enemies.length];
		for(int i=0;i<enemies.length;i++) {
			FlyingObject f = enemies[i];
			if(!f.outOfBounds() && !f.isRemove()) {
				enemyLives[index] = f;
				index++;
			}
		}
		enemies = Arrays.copyOf(enemyLives, index);
		index = 0;
		
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++) {
			Bullet f = bullets[i];
			if(!f.outOfBounds() && !f.isRemove()) {
				bulletLives[index] = f;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index);
		
		index = 0;
		
	}
	public void checkGameOverAction() {
		if(hero.getLife()<=0) {
			state = GAME_OVER;
		}
	}
	
	public void action() {//测试代码

		MouseAdapter l = new MouseAdapter() {
			/**重写mouseMoved（）鼠标移动*/
			public void mouseMoved(MouseEvent e) {
				if(state == RUNNING) {
					int x = e.getX();//获取鼠标的x坐标
					int y = e.getY();//获取鼠标的y坐标
					hero.moveTo(x, y); //英雄机随鼠标移动
				}	
			}
			public void mouseClicked(MouseEvent e) {
				switch(state) {
				case RUNNING:
					model++;
					break;
				case START:
					state = RUNNING;
					break;
				case PAUSE:
					state = RUNNING;
					break;
				case GAME_OVER:
					initial();
					state = START;
					break;
				default:
					state = START;
					break;
				}
				

			}
			public void mouseExited(MouseEvent e) {
				if(state == RUNNING) {
					state = PAUSE;
				}
				
			}
			public void mouseEntered(MouseEvent e) {
				if(state == PAUSE) {
					state = RUNNING;				
				}
			}
			
		};
		this.addMouseListener(l);//处理鼠标操作
		this.addMouseMotionListener(l);//处理鼠标移动
		
		Timer timer = new Timer();
		int intervel = 10;//以毫秒为单位
		timer.schedule(new TimerTask() {
			public void run() {//定时干的事--每10个毫秒
				if(state == RUNNING) {
					enterAction();
					if(model%2==1) {
						shootAction();
					}
					//System.out.println(model);
					stepAction();
					outOfBoundAction();//删除越界的飞行物和子弹
					bulletBangAction();//子弹与敌人的碰撞
					heroBangAction();//英雄机与敌人的碰撞
					checkGameOverAction();
					
				}
				repaint();
				
				
			}
		},intervel,intervel);

	
	}
/**重写paint（）*/
	public void paint(Graphics g) {
		sky.paintObject(g);
		hero.paintObject(g);
		for(int i=0;i<enemies.length;i++) {
			enemies[i].paintObject(g);
		}
		for(int i=0;i<bullets.length;i++) {
			bullets[i].paintObject(g);
		}
		g.drawString("SCORE: "+score, 10,25);
		g.drawString("LIFE: "+hero.getLife(), 10, 45);
		
		switch(state) {
		case START:
			g.drawImage(start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(gameOver,0,0,null);
			break;
		default:break;
		
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);//1）设置 窗口为可见,2)尽快调用paint（）方法
		
		world.action();
	}

}
