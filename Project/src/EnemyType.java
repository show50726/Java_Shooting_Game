import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.text.View;

class EnemyA extends Enemy {

	
	EnemyA(int x, int y){
		this.point = 3;
		this.type = 0;
		this.remove = false;
		this.x = x;
		this.y = y;
		this.hp = 3;
		this.speed = 1;
		try {
            image = ImageIO.read(this.getClass().getResource("/enemyA.png"));
            
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		this.imgw = (int)image.getWidth()/10;
		this.imgh = (int)image.getHeight()/10;
	}
	
	public boolean canRemove () {
		if(x<=0||x>=PlayerController.SCREEN_WIDTH||remove||this.hp<=0) {
			return true;
		}
		if(y<=0||y>=PlayerController.SCREEN_HEIGHT) {
			return true;
		}
		return false;
	}
	
	public void draw (Graphics g) {
		Random ran = new Random();
	
		int range = ran.nextInt(speed*2+1)-speed;
		//System.out.printf("%d\n", range);
		if(x+range<=0||x+range>=PlayerController.SCREEN_WIDTH)
			x -= range;
		else x += range;
		
		y+=speed;
		
		g.drawImage(image, x, y, imgw, imgh, null);
		
		hit = false;
	}
	
	public void update (List fb, List eb, List bo, int px, int py) {
		
	}
	
	public boolean testHit (double tx, double ty) {
		return new Ellipse2D.Double(x-imgw/2, y-imgh/2, imgw, imgh).contains(tx, ty);
	}
	
	public boolean shoot() {
		Random ran = new Random();
		int range = ran.nextInt(100);
		if(range%10==0) return true;
		return false;
	}
	
}

class EnemyB extends Enemy{
	
	EnemyB(int x, int y){
		this.point = 1;
		this.type = 1;
		this.remove = false;
		this.x = x;
		this.y = y;
		this.hp = 5;
		this.speed = 2;
		try {
            image = ImageIO.read(this.getClass().getResource("/enemyB.png"));
            
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		this.imgw = (int)image.getWidth()/10;
		this.imgh = (int)image.getHeight()/10;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Random ran = new Random();
		
		int range = ran.nextInt(speed*2+1)-speed;
		//System.out.printf("%d\n", range);
		if(x+range<=0||x+range>=PlayerController.SCREEN_WIDTH)
			x -= range;
		else x += range;
		y = y+speed;
		
		g.drawImage(image, x, y, imgw, imgh, null);
		
		hit = false;
	}
	
	public boolean canRemove () {
		if(x<=0||x>=PlayerController.SCREEN_WIDTH||remove||this.hp<=0) {
			return true;
		}
		if(y<=0||y>=PlayerController.SCREEN_HEIGHT) {
			return true;
		}
		return false;
	}

	@Override
	public void update(List fb, List eb, List bo, int px, int py) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean testHit(double tx, double ty) {
		return new Ellipse2D.Double(x-imgw/2, y-imgh/2, imgw, imgh).contains(tx, ty);
	}

	@Override
	public boolean shoot() {
		Random ran = new Random();
		int range = ran.nextInt(1000);
		if(range%23==0) return true;
		return false;
	}
	
}
