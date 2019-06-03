import java.awt.*
import java.awt.event.*
import java.awt.geom.*
import java.awt.image.BufferedImage;
import java.io.*
import java.util.*
import java.util.logging.*

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
	static int SCREEN_WIDTH = 540; //TODO
	static int SCREEN_HEIGHT = 800; //TODO

	private int playerPosX = 270;
	private int playerPosY = 700;
	
	private int playerSpeedY = 5;
	private int playerSpeedX = 5;
	
	private int playerHP = 50
	private int maxHP = 50;

	private int imageHigh, imageWidth;
	
	private int Score = 0;
	private int scoreThreshold = 15;
	
	private int maxEnemy = 2, enemyCnt = 0, enemyType = 3;
	private int myBulletType = 0;
	private boolean canMove = true
	private boolean explosionAnim = false;
	private boolean hitAnim = false; //TODO
	
	Timer time = new Timer(15, this);
	
	BufferedImage image; //TODO revise "image" to "fighter"
	BufferedImage gameoverimg;
	
	ImageIcon background = null;
	JLabel wordLabel = null, bgLabel = null;
	JPanel imagePanel = null;

	private List<BufferedImage> bg = new ArrayList<BufferedImage>(); // variable naming problem
	List<Bullet> myBullets = new LinkedList<Bullet>();
	List<Bullet> enmyBullets = new LinkedList<Bullet>();
	List<Enemy> allEnemy = new LinkedList<Enemy>();
	List<Item> allItem = new LinkedList<Item>();
	JLabel Scorelabel;
	
	public PlayerController() {

		try {
            image = ImageIO.read(this.getClass().getResource("/fighter.png"));
            imageWidth = (int)image.getWidth()/10;
            imageHigh = (int)image.getHeight()/10;
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		try {
            gameoverimg = ImageIO.read(this.getClass().getResource("/gameover.png"));
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		int numOfexplosionImg = 12;
	    for (int i = 1; i <= numOfexplosionImg; i++) {
	        try {
	            bg.add(ImageIO.read(this.getClass().getResource("/explosion/"+i+".png")));
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
		
		JFrame frame = new JFrame();
		
		frame.setTitle("Test");
		frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.setBackground(Color.BLACK);
		frame.add(this);
        frame.addKeyListener(this);
        
      	/* Set score label */
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

	private void spawnEnemy() { //TODO
		//System.out.println(enemyCnt);
		if(enemyCnt < maxEnemy) {
			Random ran = new Random();
			int pos = ran.nextInt(500);
			int type = ran.nextInt(enemyType);
			if (type == 0) {
				allEnemy.add(new EnemyA(pos, 10));
				enemyCnt++;
			}
			else if (type == 1) {
				allEnemy.add(new EnemyB(pos, 10));
				enemyCnt++;
			}
			else if (type == 2 && this.Score >= 50) {
				System.out.println("OK");
				allEnemy.add(new EnemyC(pos, 10));
				enemyCnt++;
			}
		}
	}
	
	private void spawnItem(int x, int y, int type) { //TODO
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
 
    public void paint(Graphics g) { //design pattern
    	super.paint(g);
    	this.spawnEnemy();
        
        for(Enemy i: allEnemy) {
        	try {
				i.draw(g);
				if(i.shoot()) {
					if(i.type==0) { //TODO
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
        }
        
        // Test whether player's bullet hit enemy 
        for(Enemy i: allEnemy) { 
           for (Bullet j : myBullets) {
          		if(i.testHit(j.x, j.y)) {
    			i.setHP(j.power);
    			j.remove = true;
    			//i.remove = true;
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
        
        // Test whether enemies' bullet hit player 
        for (Bullet j : enmyBullets) {
			try {
				if(!j.canRemove()) {
					j.draw(g);
					if(this.testHit(j.x, j.y)) {
	        			this.setHP(j.power);
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
							this.myBulletType = 1;
						}
						else if(j.type==1) {
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
        	if(allEnemy.get(j).canRemove()) {
        		this.setScore(allEnemy.get(j).point);
        		
        		Random ran = new Random(); // TODO whether there is a better way to code random.
        		int range = ran.nextInt(100);
        		
        		if(range >= 80) {
        			spawnItem(allEnemy.get(j).x, allEnemy.get(j).y, 0);
        		}
        		else if(range <= 30) {
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
        drawPlayer(g);
    }

    private void setHP(int delta) {
		// TODO Auto-generated method stub
		playerHP = (playerHP - delta >= maxHP) ? maxHP : (playerHP - delta);
		checkDie();
	}

    private void setScore(int delta) {
    	Score+=delta;
    	Scorelabel.setText("Score: "+Score);
    	setMaxEnemy();
    }
    
    
    
    private void setMaxEnemy() {
    	if(Score >= scoreThreshold) {
    		maxEnemy++;
    		scoreThreshold *= 2;
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

	int explosionCnt = 0;
	int shootPeriod = 5, shootCnt = 0;
	
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
    	
    	if(checkDie()) {
    		g.drawImage(gameoverimg, 50, 260, gameoverimg.getWidth()/2, gameoverimg.getHeight()/2, this);
    		float alpha = 0f;
    		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
    		((Graphics2D) g).setComposite(ac);
    	}
    	
    	g.drawImage(image, (int)playerPosX, (int)playerPosY, (int)image.getWidth()/10, (int)image.getHeight()/10, this);
    	
    	if(checkDie()&&!explosionAnim) {
    		float alpha = 1f;
    		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha);
    		((Graphics2D) g).setComposite(ac);
    		g.drawImage(bg.get(explosionCnt/2), (int)playerPosX, (int)playerPosY, (int)bg.get(explosionCnt/2).getWidth()/2, (int)bg.get(explosionCnt/2).getHeight()/2, this);
    		explosionCnt++;
    	}    
    	
    	if(explosionCnt==24)
    		explosionAnim = true;
    	
    	AffineTransform originalTransform = ((Graphics2D) g).getTransform();
    	
    	((Graphics2D) g).setTransform(originalTransform);
    	Toolkit.getDefaultToolkit().sync();
    	
    }
	
	private void Shoot() {
		if(myBulletType==0)
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, 0, 1));
		else if(myBulletType==1) {
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, 0, 1));
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, 1, 1));
			myBullets.add(new NormalBullet(playerPosX+15, playerPosY, -1, 1));
		}
	}
	
	public boolean testHit(double tx, double ty) {
		return new Ellipse2D.Double(playerPosX-imageWidth/2, playerPosY-imageHigh/2, imageHigh, imageWidth).contains(tx, ty);
	}
  
	@Override
	public void actionPerformed(ActionEvent e) {
		//playerPosX-= playerSpeedX;
		this.repaint();
	}

	boolean up = false, down = false, left = false, right = false, toShoot = false;
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println(e);
		
		if(canMove) {
			if(key == KeyEvent.VK_UP)
				up = true;
			
			if(key == KeyEvent.VK_DOWN)
				down = true;
	
			if(key == KeyEvent.VK_LEFT)
				left = true;
			
			if(key == KeyEvent.VK_RIGHT)
				right = true;
			
			if(key == KeyEvent.VK_SPACE)
				toShoot = true;
		}
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
		
		if(key == KeyEvent.VK_SPACE)
			toShoot = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
  
}
