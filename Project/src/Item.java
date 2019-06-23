import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.List;

abstract public class Item {
	public int x, y, type;           //position and the type index of the item
	public boolean remove = false;
	public int imgh, imgw;              //width and height of the item
	Image image;                      //image of the item
	
	public boolean canRemove () {               //remove if out of range or gets eaten by the player
		if(x<=0||x>=PlayerController.SCREEN_WIDTH||remove) {
			return true;
		}
		if(y<=0||y>=PlayerController.SCREEN_HEIGHT) {
			return true;
		}
		return false;
	}
	
	abstract public void draw (Graphics g);

}
