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

	float playerPosX = 270;               //initial position of the player
	float playerPosY = 700;
	
	float playerSpeedY = 5;          //moving speed of the player
	float playerSpeedX = 5;
	
	int playerHP = 50, maxHP = 50;         //health point of the player
	int laserPower = 30, maxlaserPower = 30;     //laser power of the player
	
	static int SCREEN_WIDTH = 540;        
	static int SCREEN_HEIGHT = 800;
	
	int imgh, imgw;         //image height and width of the player fighter
	
	int Score = 0;         
	
	int maxEnemy = 2, enemyCnt = 0, enemyType = 3;       //enemy counter
	int myBulletType = 0;                                //player's bullet type
	boolean canMove = true, explosionAnim = false;       //if canMove==false player is not able to move the fighter
	boolean canShootLaser = false;                       
	boolean gameover = true;
	
	Timer time = new Timer(15, this);               //timer
	
	BufferedImage fighterImage, gameoverimg;        
	
	JLabel wordLabel = null, bgLabel = null;
	JPanel imagePanel = null;
	
	JPanel scoreboardPanel = null;
	
	PlaySound p = null;

	private static List<BufferedImage> bg = new ArrayList<BufferedImage>();         //arraylist of explosion animation
	List<Bullet> myBullets	= new LinkedList<Bullet>();                            //list of different objects;
	List<Bullet> enmyBullets = new LinkedList<Bullet>();
	List<Enemy> allEnemy = new LinkedList<Enemy>();
	List<Item> allItem = new LinkedList<Item>();
	JLabel Scorelabel;
	JFrame frame;
	
	public PlayerController() {

		try {                                   //load every image of the game when initialization

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
		
		frame = new JFrame();                   //game frame
		
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
        setVisible(true);
        
      //set score label
        Scorelabel = new JLabel("Score: "+Score);
        Scorelabel.setSize(50, 20);
        Scorelabel.setLocation(245, 3);
        Scorelabel.setForeground(Color.white);
        GroupLayout layout = new javax.swing.GroupLayout(Scorelabel);
        Scorelabel.setLayout(layout);
        add(Scorelabel);     
        
        time.start();

	}

	private void spawnEnemy() {                //random the spawning enemy type
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
			else if(type==2&&this.Score>=50) {                //this enemy only appear when the score>=50
				allEnemy.add(new EnemyC(pos, 10));
				enemyCnt++;
			}
		}
	}
	
	private void spawnItem(int x, int y, int type) {             //spawn the item
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
					if(i.type==0) {                      //add enemy's bullets
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
        	for (Bullet j : myBullets) {           //test the collision between player's bullets and enemies
        		if(i.testHit(j.x, j.y)) {
        			i.setHP(j.power);              //set the enemy's hp 
        			if(i.hp<=0) {
        				this.setPower(1);          //add laserPower if enemy dies
        				this.setScore(i.point);    //add score
        			}
        			j.remove = true;
        			p = new PlaySound("EnemyDie.wav");      //play enemy dying sound
        			//i.remove = true;
        		}
        	}
        }
        
        for (Bullet j : myBullets) {          //draw every player's bullet
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
					if(this.testHit(j.x, j.y)&&playerHP>0) {           //test the collision between enemy's bullets and the player
	        			this.setHP(j.power);
	        			p = new PlaySound("playerGotHit.wav");         //play the sound effect
	        			j.remove = true;                               //remove the bullet
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
					if(this.testHit(j.x, j.y)) {                //test if the player gets the item
						if(j.type==0) {
							p = new PlaySound("getItem1.wav");   //sound effect
							this.myBulletType = 1;               //change plsyer's bullet type
						}
						else if(j.type==1) {
							p = new PlaySound("getItem2.wav");   //sound effect
							this.setHP(-10);                     //add player's hp
						}
	        			j.remove = true;                        //remove the item
	        		}
				}
			}
			catch (Exception e) {
				break;
			}
		}
        
        for(int j = allEnemy.size()-1; j >=0 ; j--) {
        	if(canShootLaser)
        		System.out.printf("%f %d\n", playerPosX, allEnemy.get(j).x);          //test of the enemy is in the laser range
        	if(allEnemy.get(j).canRemove()||(canShootLaser&&((int)playerPosX-3<=allEnemy.get(j).x)&&((int)playerPosX+3>=allEnemy.get(j).x))) {
        		
        		if((canShootLaser&&((int)playerPosX-3<=allEnemy.get(j).x)&&((int)playerPosX+3>=allEnemy.get(j).x))){
        			p = new PlaySound("EnemyDie.wav");            //sound effect
        		}

        		Random ran = new Random();
        		int range = ran.nextInt(100);
        		
        		if(range>=90) {                      //the probability of the recovery item is 10%
        			spawnItem(allEnemy.get(j).x, allEnemy.get(j).y, 0);
        		}
        		else if(range<=20) {                 //the probability of the recovery item is 20%
        			spawnItem(allEnemy.get(j).x, allEnemy.get(j).y, 1);
        		}
        		
        		allEnemy.remove(j);
        		enemyCnt--;
        	}
        }

        
        for(int j = myBullets.size()-1; j >=0 ; j--) {               //remove every objects which should be removed
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

        
        drawHPBar(g); 
        drawPowerBar(g);
        drawPlayer(g);
    }

    private void setHP(int delta) {                                //delta>0 => decrease hp, otherwise increase hp
		// TODO Auto-generated method stub
		this.playerHP-=delta;
		if(this.playerHP>this.maxHP) this.playerHP = this.maxHP;  //hp cannot exceeds the limit    
		checkDie();                                               //check if the player dies
	}
    
    private void setPower(int delta) {                            //add the laser power
		// TODO Auto-generated method stub
		laserPower+=delta;
		if(laserPower>maxlaserPower) laserPower = maxlaserPower;  //power cannot exceeds the limit         
	}

    private void setScore(int delta) {                            //add the score
    	this.Score+=delta;
    	Scorelabel.setText("Score: "+this.Score);
    	setMaxEnemy();
    }
    
    private int threshold = 15;
    private void setMaxEnemy() {                                  //the enemy's maximum number is proportional to the score
    	if(this.Score>=threshold) {
    		this.maxEnemy++;
    		threshold*=2;
    	}
    }
    
	private boolean checkDie() {
		// TODO Auto-generated method stub
		if(this.playerHP<=0) {                //if player's dead, it's not able to move
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
	
	private void drawHPBar(Graphics g) {        //draw hp bar
		int k = 100/this.maxHP;
		g.setColor(Color.RED);
		g.drawRect(400, 30, 100, 10);
		g.fillRect(400, 30, this.playerHP*k>=0?this.playerHP>this.maxHP?this.maxHP*k:this.playerHP*k:0, 10);
	}
	
	private void drawPowerBar(Graphics g) {      //draw power bar
		int k = 90/maxlaserPower;
		g.setColor(Color.YELLOW);
		g.drawRect(400, 50, 90, 10);
		g.fillRect(400, 50, laserPower*k, 10);
	}

	int explosionCnt = 0;                 //explosion animation counter
	int shootPeriod = 5, shootCnt = 0;    //the period between shooting two times
	int laserCnt = 0, laserPeriod = 140;  //laser period counter
	private void drawPlayer(Graphics g) {
		
    	playerPosY = (playerPosY-(up?playerSpeedY:0)+(down?playerSpeedY:0)); 
    	if(playerPosY<=0) playerPosY = 1;                                          //check if the player is in the range
    	if(playerPosY>=730) playerPosY = 729;
    	
    	playerPosX = (playerPosX-(left?playerSpeedX:0)+(right?playerSpeedX:0));
    	if(playerPosX<=0) playerPosX = 1;                                           //check if the player is in the range
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

    	
    	if(checkDie()) {
    		if(gameover) {           //draw gameover image
    			g.drawImage(gameoverimg, 50, 260, gameoverimg.getWidth()/2, gameoverimg.getHeight()/2, this);
    		}
    		float alpha = 0f;               //set the opacity of the player
    		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
    		((Graphics2D) g).setComposite(ac);
    		
    		if(!explosionAnim) {            //draw explosion animation
    			alpha = 1f;
        		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
        		((Graphics2D) g).setComposite(ac);
        		g.drawImage(bg.get(explosionCnt/2), (int)playerPosX, (int)playerPosY, (int)bg.get(explosionCnt/2).getWidth()/2, (int)bg.get(explosionCnt/2).getHeight()/2, this);
        		explosionCnt++;
    		}
    		
    	}
    	
    	g.drawImage(fighterImage, (int)playerPosX, (int)playerPosY, (int)fighterImage.getWidth()/10, (int)fighterImage.getHeight()/10, this);
    	
    	
    	if(explosionCnt==1) p = new PlaySound("explosion.wav");        //play explosion sound effect
    	
    	if(explosionCnt==24)                                           //already play the animation
    		explosionAnim = true;

    	AffineTransform originalTransform = ((Graphics2D) g).getTransform();
    	
    	((Graphics2D) g).setTransform(originalTransform);
    	Toolkit.getDefaultToolkit().sync();
    	
    }
	
	boolean up = false, down = false, left = false, right = false, toShoot = false;;
	public void keyPressed(KeyEvent e) {            //get the key
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
		else {                    //if gameover and press any key, go to the scoreboard scene

			new ScoreboardScene(Score);
			frame.setVisible(false);            //close this frame
			frame.dispose();
			
		}

	}
	
	void Shoot() {                  //player's shooting method
		PlaySound p = new PlaySound("shoot.wav");
		if(myBulletType==0)                                                     //different types of bullets
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, 0, 1));
		else if(myBulletType==1) {
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, 0, 1));
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, 1, 1));
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, -1, 1));
		}
	}
	
	public boolean testHit(double tx, double ty) {                            //test if the player got hit
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

	private void ShootLaser() {                 //player can shoot laser only if the power is maximum
		// TODO Auto-generated method stub
		if(laserPower==maxlaserPower) {
			canShootLaser = true;
			laserPower = 0;
			p = new PlaySound("LaserShoot.wav");  //laser sound effect
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

  
}