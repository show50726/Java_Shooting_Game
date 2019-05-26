import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.View;

public class PlayerController extends JPanel implements KeyListener, ActionListener {

	float playerPosX = 270;
	float playerPosY = 700;
	
	float playerSpeedY = 5;
	float playerSpeedX = 5;
	
	int playerHP = 20;
	
	static int SCREEN_WIDTH = 540;
	static int SCREEN_HEIGHT = 800;
	
	int imgh, imgw;
	
	Timer time = new Timer(15, this);
	
	BufferedImage image;
	
	ImageIcon background = null;
	JLabel wordLabel = null, bgLabel = null;
	JPanel imagePanel = null;

	
	List<Bullet> myBullets	= new LinkedList<Bullet>();
	List<Bullet> enmyBullets = new LinkedList<Bullet>();
	List<Enemy> allEnemy = new LinkedList<Enemy>();
	
	
	public PlayerController() {

		try {

            image = ImageIO.read(this.getClass().getResource("/fighter.png"));
            imgw = (int)image.getWidth()/10;
            imgh = (int)image.getHeight()/10;

        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
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

        allEnemy.add(new EnemyA((int)playerPosX, 10));
        allEnemy.add(new EnemyB((int)playerPosX+20, 10));
        allEnemy.add(new EnemyA((int)playerPosX-50, 10));
	}

	
	
	public void update(Graphics g) { 
		System.out.println("update");
        this.paint(g); 
    } 
 
    public void paint(Graphics g) { 
        super.paint(g);
        
        for(Enemy i: allEnemy) {
        	try {
				i.draw(g);
				if(i.shoot()) {
					if(i.type==0) {
						enmyBullets.add(new EnemyABullet(i.x, i.y, -1));
						enmyBullets.add(new EnemyABullet(i.x, i.y, 0));
						enmyBullets.add(new EnemyABullet(i.x, i.y, 1));
					}
				}
			}
			catch (Exception e) {
				break;
			}
        	for (Bullet j : myBullets) {
        		if(i.testHit(j.x, j.y)) {
        			i.setHP(j.power);
        			j.remove = true;
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
        
        for(int j = allEnemy.size()-1; j >=0 ; j--) {
        	if(allEnemy.get(j).canRemove()) {
        		allEnemy.remove(j);
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
//        for (Bullet k : enmyBullets) {
//        	if(k.canRemove()) enmyBullets.remove(k);
//        }
        
        drawPlayer(g);
        
    }

    private void setHP(int delta) {
		// TODO Auto-generated method stub
		this.playerHP-=delta;
		checkDie();
	}



	private void checkDie() {
		// TODO Auto-generated method stub
		if(this.playerHP<=0) {
			//player dies
		}
	}


	int shootPeriod = 5, shootCnt = 0;
	private void drawPlayer(Graphics g) {
    	playerPosY = (playerPosY-(up?playerSpeedY:0)+(down?playerSpeedY:0));
    	if(playerPosY<=0) playerPosY = 1;
    	if(playerPosY>=730) playerPosY = 729;
    	
    	playerPosX = (playerPosX-(left?playerSpeedX:0)+(right?playerSpeedX:0));
    	if(playerPosX<=0) playerPosX = 1;
    	if(playerPosX>=500) playerPosX = 499;
    	System.out.printf("%f %f\n", playerPosX, playerPosY);
    	
    	if(toShoot) {
    		if(shootCnt>=shootPeriod) {
    			this.Shoot();
    			shootCnt%=shootPeriod;
    		}
    		else shootCnt++;
    	}
    	
    	g.drawImage(image, (int)playerPosX, (int)playerPosY, (int)image.getWidth()/10, (int)image.getHeight()/10, this);
    	AffineTransform originalTransform = ((Graphics2D) g).getTransform();
    	
    	((Graphics2D) g).setTransform(originalTransform);
    	Toolkit.getDefaultToolkit().sync();
    	
    }
	
	boolean up = false, down = false, left = false, right = false, toShoot = false;;
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println(e);
		
		// Move the player on the left
		if( key == KeyEvent.VK_UP ) {
			up = true;
		}
		
		if( key == KeyEvent.VK_DOWN )
			down = true;

		// Move the player on the right
		if( key == KeyEvent.VK_LEFT )
			left = true;
		
		if( key == KeyEvent.VK_RIGHT )
			right = true;
		
		if(key == KeyEvent.VK_SPACE)
			toShoot = true;

		//checkPosRange();
		repaint();
	}
	
	void Shoot() {
		myBullets.add(new NormalBullet(playerPosX+15, playerPosY));
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
		
		if(key == KeyEvent.VK_SPACE)
			toShoot = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
  
}