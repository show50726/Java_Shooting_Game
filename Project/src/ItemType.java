import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.image.ImageObserver;
import javax.imageio.ImageIO;
import javax.swing.text.View;

class ChangeBulletItem extends Item {
	
	ChangeBulletItem(int x, int y){
		this.x = x;
		this.y = y;
		this.type = 0;
		this.remove = false;
		try {
            image = ImageIO.read(this.getClass().getResource("/star.png"));
            
        } catch (IOException ex) {
            Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		this.imgw = (int)image.getWidth(null)/20;
		this.imgh = (int)image.getHeight(null)/20;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(image, x, y, imgh, imgw, null);
		
	}
	
	public boolean canRemove () {
		if(x<=0||x>=PlayerController.SCREEN_WIDTH||remove) {
			return true;
		}
		if(y<=0||y>=PlayerController.SCREEN_HEIGHT) {
			return true;
		}
		return false;
	}

}
