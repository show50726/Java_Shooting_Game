import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;

abstract public class Item {
	public int x, y, type;
	public boolean remove = false;
	public int imgh, imgw;
	Image image;
	
	public boolean canRemove () {
		if(x<=0||x>=PlayerController.SCREEN_WIDTH||remove) {
			return true;
		}
		if(y<=0||y>=PlayerController.SCREEN_HEIGHT) {
			return true;
		}
		return false;
	}
	
	abstract public void draw (Graphics g);

	abstract public void itemEffect(); 
}
