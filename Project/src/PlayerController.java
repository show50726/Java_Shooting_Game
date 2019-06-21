import java.awt.AlphaComposite;
import sun.audio.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.text.View;

public class PlayerController extends JPanel implements KeyListener, ActionListener {

	float playerPosX = 270;
	float playerPosY = 700;
	
	float playerSpeedY = 5;
	float playerSpeedX = 5;
	
	int playerHP = 50, maxHP = 50;
	int laserPower = 30, maxlaserPower = 30;
	
	static int SCREEN_WIDTH = 540;
	static int SCREEN_HEIGHT = 800;
	
	int imgh, imgw;
	
	int Score = 0;
	
	int maxEnemy = 2, enemyCnt = 0, enemyType = 3;
	int myBulletType = 0;
	boolean canMove = true, explosionAnim = false;
	boolean canShootLaser = false;
	boolean gameover = true;
	
	Timer time = new Timer(15, this);
	
	BufferedImage fighterImage, gameoverimg;
	
	ImageIcon background = null;
	JLabel wordLabel = null, bgLabel = null;
	JPanel imagePanel = null;
	
	JPanel scoreboardPanel = null;
	
	PlaySound p = null;

	private static List<BufferedImage> bg = new ArrayList<BufferedImage>();
	List<Bullet> myBullets	= new LinkedList<Bullet>();
	List<Bullet> enmyBullets = new LinkedList<Bullet>();
	List<Enemy> allEnemy = new LinkedList<Enemy>();
	List<Item> allItem = new LinkedList<Item>();
	JLabel Scorelabel;
	JFrame frame;
	
	public PlayerController() {

		try {

            fighterImage = ImageIO.read(this.getClass().getResource("/fighter.png"));
            imgw = (int)fighterImage.getWidth()/10;
            imgh = (int)fighterImage.getHeight()/10;

        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		try {

            gameoverimg = ImageIO.read(this.getClass().getResource("/gameover.png"));

        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		Integer max = 12;
	    for (int i = 1; i <= max; i++) {
	        try {
	            bg.add(ImageIO.read(this.getClass().getResource("/explosion/"+i+".png")));
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		
		frame = new JFrame();
		
		
		
		frame.setTitle("Game");
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.setBackground(Color.BLACK);
		frame.add(this);
        frame.addKeyListener(this);
        
      //set score label
        Scorelabel = new JLabel("Score: "+Score);
        Scorelabel.setSize(50, 20);
        Scorelabel.setLocation(245, 3);
        Scorelabel.setForeground(Color.white);
        GroupLayout layout = new javax.swing.GroupLayout(Scorelabel);
        Scorelabel.setLayout(layout);
        add(Scorelabel);
         
        
//        background = new ImageIcon(getClass().getResource("/bg2.jpg"));     
//        bgLabel = new JLabel(background);     
//        bgLabel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
//        
//        
//        
//        imagePanel = (JPanel) this.getContentPane();
//        imagePanel.setOpaque(false);
//        this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
        
        
        time.start();

	}

	private void spawnEnemy() {
		//System.out.println(enemyCnt);
		if(enemyCnt<maxEnemy) {
			Random ran = new Random();
			int type = ran.nextInt(enemyType);
			
			int pos = ran.nextInt(500);
			if(type==0) {
				allEnemy.add(new EnemyA(pos, 10));
				enemyCnt++;
			}
			else if(type==1) {
				allEnemy.add(new EnemyB(pos, 10));
				enemyCnt++;
			}
			else if(type==2&&this.Score>=50) {
				allEnemy.add(new EnemyC(pos, 10));
				enemyCnt++;
			}
		}
	}
	
	private void spawnItem(int x, int y, int type) {
		if(type==0) {
			allItem.add(new ChangeBulletItem(x, y));
		}
		else if(type==1) {
			allItem.add(new RecoverItem(x, y));
		}
	}
	
	public void update(Graphics g) { 
		System.out.println("update");
        this.paint(g); 
    } 
 
    public void paint(Graphics g) { 
    	super.paint(g);
    	this.spawnEnemy();
        
        for(Enemy i: allEnemy) {
        	try {
				i.draw(g);
				if(i.shoot()) {
					if(i.type==0) {
						enmyBullets.add(new EnemyABullet(i.x, i.y, -1, 1));
						enmyBullets.add(new EnemyABullet(i.x, i.y, 0, 1));
						enmyBullets.add(new EnemyABullet(i.x, i.y, 1, 1));
					}
					else if(i.type==1) {
						enmyBullets.add(new EnemyBBullet(i.x, i.y, -1, 1));
						enmyBullets.add(new EnemyBBullet(i.x, i.y, 0, 1));
						enmyBullets.add(new EnemyBBullet(i.x, i.y, 1, 1));
						enmyBullets.add(new EnemyBBullet(i.x, i.y, 0, -1));
						enmyBullets.add(new EnemyBBullet(i.x, i.y, 1, -1));
						enmyBullets.add(new EnemyBBullet(i.x, i.y, -1, -1));
					}
					else if(i.type==2) {
						enmyBullets.add(new EnemyCBullet(i.x, i.y, 0, 1));
					}
				}
				
			}
			catch (Exception e) {
				break;
			}
        	for (Bullet j : myBullets) {
        		if(i.testHit(j.x, j.y)) {
        			i.setHP(j.power);
        			if(i.hp<=0) {
        				this.setPower(1);
        				this.setScore(i.point);
        			}
        			j.remove = true;
        			p = new PlaySound("EnemyDie.wav");
        			//i.remove = true;
        		}
        	}
        }
        
        for (Bullet j : myBullets) {
			try {
				j.draw(g);
			}
			catch (Exception e) {
				break;
			}
		}
        
        for (Bullet j : enmyBullets) {
			try {
				if(!j.canRemove()) {
					j.draw(g);
					if(this.testHit(j.x, j.y)&&playerHP>0) {
	        			this.setHP(j.power);
	        			p = new PlaySound("playerGotHit.wav");
	        			j.remove = true;
	        		}
				}
			}
			catch (Exception e) {
				break;
			}
		}
        
        for (Item j : allItem) {
			try {
				if(!j.canRemove()) {
					j.draw(g);
					if(this.testHit(j.x, j.y)) {
						if(j.type==0) {
							p = new PlaySound("getItem1.wav");
							this.myBulletType = 1;
						}
						else if(j.type==1) {
							p = new PlaySound("getItem2.wav");
							this.setHP(-10);
						}
	        			j.remove = true;
	        		}
				}
			}
			catch (Exception e) {
				break;
			}
		}
        
        for(int j = allEnemy.size()-1; j >=0 ; j--) {
        	if(canShootLaser)
        		System.out.printf("%f %d\n", playerPosX, allEnemy.get(j).x);
        	if(allEnemy.get(j).canRemove()||(canShootLaser&&((int)playerPosX-3<=allEnemy.get(j).x)&&((int)playerPosX+3>=allEnemy.get(j).x))) {
        		
        		if((canShootLaser&&((int)playerPosX-3<=allEnemy.get(j).x)&&((int)playerPosX+3>=allEnemy.get(j).x))){
        			p = new PlaySound("EnemyDie.wav");
        		}

        		Random ran = new Random();
        		int range = ran.nextInt(100);
        		
        		if(range>=80) {
        			spawnItem(allEnemy.get(j).x, allEnemy.get(j).y, 0);
        		}
        		else if(range<=30) {
        			spawnItem(allEnemy.get(j).x, allEnemy.get(j).y, 1);
        		}
        		
        		allEnemy.remove(j);
        		enemyCnt--;
        	}
        }

//        for(Enemy i: allEnemy) {
//        	if(i.canRemove()) allEnemy.remove(i);
//        }
        
//        for (Bullet j : myBullets) {
//        	if(j.canRemove()) myBullets.remove(j);
//        }
        
        for(int j = myBullets.size()-1; j >=0 ; j--) {
        	if(myBullets.get(j).canRemove()) {
        		myBullets.remove(j);
        		
        	}
        }
        
        for(int i = enmyBullets.size()-1; i >=0 ; i--) {
        	if(enmyBullets.get(i).canRemove()) {
        		enmyBullets.remove(i);
        		
        	}
        }
        
        for(int i = allItem.size()-1; i >=0 ; i--) {
        	if(allItem.get(i).canRemove()) {
        		allItem.remove(i);
        		
        	}
        }
//        for (Bullet k : enmyBullets) {
//        	if(k.canRemove()) enmyBullets.remove(k);
//        }
        
        drawHPBar(g);
        drawPowerBar(g);
        drawPlayer(g);
    }

    private void setHP(int delta) {
		// TODO Auto-generated method stub
		this.playerHP-=delta;
		if(this.playerHP>this.maxHP) this.playerHP = this.maxHP;
		checkDie();
	}
    
    private void setPower(int delta) {
		// TODO Auto-generated method stub
		laserPower+=delta;
		if(laserPower>maxlaserPower) laserPower = maxlaserPower;
	}

    private void setScore(int delta) {
    	this.Score+=delta;
    	Scorelabel.setText("Score: "+this.Score);
    	setMaxEnemy();
    }
    
    private int threshold = 15;
    private void setMaxEnemy() {
    	if(this.Score>=threshold) {
    		this.maxEnemy++;
    		threshold*=2;
    	}
    }
    
	private boolean checkDie() {
		// TODO Auto-generated method stub
		if(this.playerHP<=0) {
			canMove = false;
			up = false;
			down = false;
			left = false;
			right = false;
			toShoot = false;
			
			
			return true;
		}
		return false;
	}
	
	private void drawHPBar(Graphics g) {
		int k = 100/this.maxHP;
		g.setColor(Color.RED);
		g.drawRect(400, 30, 100, 10);
		g.fillRect(400, 30, this.playerHP*k>=0?this.playerHP>this.maxHP?this.maxHP*k:this.playerHP*k:0, 10);
	}
	
	private void drawPowerBar(Graphics g) {
		int k = 90/maxlaserPower;
		g.setColor(Color.YELLOW);
		g.drawRect(400, 50, 90, 10);
		g.fillRect(400, 50, laserPower*k, 10);
	}

	int explosionCnt = 0;
	int shootPeriod = 5, shootCnt = 0;
	int laserCnt = 0, laserPeriod = 140;
	private void drawPlayer(Graphics g) {
		
    	playerPosY = (playerPosY-(up?playerSpeedY:0)+(down?playerSpeedY:0));
    	if(playerPosY<=0) playerPosY = 1;
    	if(playerPosY>=730) playerPosY = 729;
    	
    	playerPosX = (playerPosX-(left?playerSpeedX:0)+(right?playerSpeedX:0));
    	if(playerPosX<=0) playerPosX = 1;
    	if(playerPosX>=500) playerPosX = 499;
    	//System.out.printf("%f %f\n", playerPosX, playerPosY);
    	
    	if(toShoot) {
    		if(shootCnt>=shootPeriod) {
    			this.Shoot();
    			shootCnt%=shootPeriod;
    		}
    		else shootCnt++;
    	}
    	
    	if(canShootLaser&&laserCnt<=laserPeriod) {
    		laserCnt++;
    		//g.drawLine((int)playerPosX+15, (int)playerPosY, (int)playerPosX, (int)-playerPosY);
    		g.fillRect((int)playerPosX+16, (int)playerPosY-1000, 6, 1000);
    	}
    	else if(laserCnt>laserPeriod){
    		laserCnt = 0;
    		canShootLaser = false;
    	}
    	
//    	if(checkDie()) {
//    		g.drawImage(gameoverimg, 50, 260, gameoverimg.getWidth()/2, gameoverimg.getHeight()/2, this);
//    		float alpha = 0f;
//    		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
//    		((Graphics2D) g).setComposite(ac);
//    	}
    	
    	if(checkDie()) {
    		if(gameover) {
    			g.drawImage(gameoverimg, 50, 260, gameoverimg.getWidth()/2, gameoverimg.getHeight()/2, this);
    		}
    		float alpha = 0f;
    		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
    		((Graphics2D) g).setComposite(ac);
    		
    		if(!explosionAnim) {
    			alpha = 1f;
        		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        		((Graphics2D) g).setComposite(ac);
        		g.drawImage(bg.get(explosionCnt/2), (int)playerPosX, (int)playerPosY, (int)bg.get(explosionCnt/2).getWidth()/2, (int)bg.get(explosionCnt/2).getHeight()/2, this);
        		explosionCnt++;
    		}
    		
    	}
    	
    	g.drawImage(fighterImage, (int)playerPosX, (int)playerPosY, (int)fighterImage.getWidth()/10, (int)fighterImage.getHeight()/10, this);
    	
//    	if(checkDie()&&!explosionAnim) {
//    		float alpha = 1f;
//    		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
//    		((Graphics2D) g).setComposite(ac);
//    		g.drawImage(bg.get(explosionCnt/2), (int)playerPosX, (int)playerPosY, (int)bg.get(explosionCnt/2).getWidth()/2, (int)bg.get(explosionCnt/2).getHeight()/2, this);
//    		explosionCnt++;
//    	}    
    	
    	if(explosionCnt==1) p = new PlaySound("explosion.wav");
    	
    	if(explosionCnt==24)
    		explosionAnim = true;
		    
    	
    	
    	
    	AffineTransform originalTransform = ((Graphics2D) g).getTransform();
    	
    	((Graphics2D) g).setTransform(originalTransform);
    	Toolkit.getDefaultToolkit().sync();
    	
    }
	
	boolean up = false, down = false, left = false, right = false, toShoot = false;;
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println(e);
		
		if(canMove) {
			if( key == KeyEvent.VK_UP ) {
				up = true;
			}
			
			if( key == KeyEvent.VK_DOWN )
				down = true;
	
			if( key == KeyEvent.VK_LEFT )
				left = true;
			
			if( key == KeyEvent.VK_RIGHT )
				right = true;
			
			if(key == KeyEvent.VK_SPACE)
				toShoot = true;
			
			if( key == KeyEvent.VK_Z )
				ShootLaser();
		}
		else {
//			gameover = false;
//			scoreboardPanel = new JPanel();
//			scoreboardPanel.setBackground(Color.WHITE);
//			scoreboardPanel.setSize(350, 600);
//			scoreboardPanel.setLocation(100, 100);
//
//			add(scoreboardPanel);
			
			new ScoreboardScene(Score);
			frame.setVisible(false);
			frame.dispose();
			
		}
		//checkPosRange();
		//repaint();
	}
	
	void Shoot() {
		PlaySound p = new PlaySound("shoot.wav");
		if(myBulletType==0)
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, 0, 1));
		else if(myBulletType==1) {
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, 0, 1));
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, 1, 1));
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, -1, 1));
		}
	}
	
	public boolean testHit(double tx, double ty) {
		return new Ellipse2D.Double(playerPosX-imgw/2, playerPosY-imgh/2, imgh, imgw).contains(tx, ty);
	}
  

	@Override
	public void actionPerformed(ActionEvent e) {
		//playerPosX-= playerSpeedX;
		this.repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();
		if( key == KeyEvent.VK_UP ) {
			up = false;
		}
		if( key == KeyEvent.VK_DOWN )
			down = false;

		// Move the player on the right
		if( key == KeyEvent.VK_LEFT )
			left = false;
		
		if( key == KeyEvent.VK_RIGHT )
			right = false;
		
		if( key == KeyEvent.VK_SPACE )
			toShoot = false;
		
		
	}

	private void ShootLaser() {
		// TODO Auto-generated method stub
		if(laserPower==maxlaserPower) {
			canShootLaser = true;
			laserPower = 0;
			p = new PlaySound("LaserShoot.wav");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

  
}