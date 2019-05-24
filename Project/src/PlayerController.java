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
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Deque;
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
	
	float playerSpeedY = 15;
	float playerSpeedX = 15;
	
	static int SCREEN_WIDTH = 540;
	static int SCREEN_HEIGHT = 800;
	
	Timer time = new Timer(15, this);
	
	BufferedImage image;
	
	ImageIcon background = null;
	JLabel wordLabel = null, bgLabel = null;
	JPanel imagePanel = null;

	
	List<Bullet> myBullets	= new LinkedList<Bullet>();
	List<Enemy> allEnemy = new LinkedList<Enemy>();
	
	
	public PlayerController() {

		try {

            image = ImageIO.read(this.getClass().getResource("/fighter.png"));
            

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
			}
			catch (Exception e) {
				break;
			}
        	for (Bullet j : myBullets) {
        		if(i.testHit(j.x, j.y)) {
        			j.remove = true;
        			i.remove = true;
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
        
        for(Enemy i: allEnemy) {
        	if(i.canRemove()) allEnemy.remove(i);
        }
        
        for (Bullet j : myBullets) {
        	if(j.canRemove()) myBullets.remove(j);
        }
        
        drawPlayer(g);
        
    }

    private void drawPlayer(Graphics g) {
    	
    	g.drawImage(image, (int)playerPosX, (int)playerPosY, (int)image.getWidth()/10, (int)image.getHeight()/10, this);
    	AffineTransform originalTransform = ((Graphics2D) g).getTransform();
    	
    	((Graphics2D) g).setTransform(originalTransform);
    	Toolkit.getDefaultToolkit().sync();
    	
    }
  
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//System.out.println(e);
		
		// Move the player on the left
		if( key == KeyEvent.VK_UP )
			playerPosY-= playerSpeedY;
		
		if( key == KeyEvent.VK_DOWN )
			playerPosY += playerSpeedY;

		// Move the player on the right
		if( key == KeyEvent.VK_LEFT )
			playerPosX -= playerSpeedX;
		
		if( key == KeyEvent.VK_RIGHT )
			playerPosX += playerSpeedX;
		
		if(key == KeyEvent.VK_SPACE)
			Shoot();

		//checkPosRange();
		repaint();
	}
	
	void Shoot() {
		myBullets.add(new NormalBullet(playerPosX+15, playerPosY));
	}
  
  	private void checkPosRange() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//playerPosX-= playerSpeedX;
		this.repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
  
}