
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.View;

public class GraphicsDemo01 extends JFrame implements KeyListener, ActionListener {

	float playerPosX = 270;
	float playerPosY = 700;
	
	float playerSpeedY = 15;
	float playerSpeedX = 15;
	
	final int SCREEN_WIDTH = 540;
	final int SCREEN_HEIGHT = 800;
	
	Timer time = new Timer(1200, this);
	
	BufferedImage image;
	
	ImageIcon background = null;
	JLabel wordLabel = null, bgLabel = null;
	JPanel imagePanel = null;
	
	public GraphicsDemo01() {
        setTitle("Test");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addKeyListener(this);
        
        background = new ImageIcon(getClass().getResource("/bg2.jpg"));     
        bgLabel = new JLabel(background);     
        bgLabel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        
        imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
        this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
        
        try {

            image = ImageIO.read(this.getClass().getResource("/fighter.png"));
            

        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
        time.start();
        
	    
	}
	
	public static void main(String[] args) {
	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new BorderLayout());
	    frame.pack();
	    frame.setVisible(true);
	    new GraphicsDemo01().show();
	}
	
	
	
	
	public void update(Graphics g) { 
        this.paint(g); 
    } 
 
    public void paint(Graphics g) { 
        super.paint(g);
        
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
