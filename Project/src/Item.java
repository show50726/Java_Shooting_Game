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
		return false;
	}
	
	abstract public void draw (Graphics g);

}
