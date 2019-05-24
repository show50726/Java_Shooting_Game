import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.text.View;

class EnemyA extends Enemy {
	
	EnemyA(int x, int y){
		this.x = x;
		this.y = y;
		this.hp = 5;
		this.speed = 1;
		try {
            image = ImageIO.read(this.getClass().getResource("/enemyA.png"));
            
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	public boolean canRemove () {
		if(x<=0||x>=PlayerController.SCREEN_WIDTH) {
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
		y = y+speed;
		g.drawImage(image, x, y, (int)image.getWidth()/10, (int)image.getHeight()/10, null);
		
		hit = false;
	}
	
	public void update (List fb, List eb, List bo, int px, int py) {
		
	}

	
}
