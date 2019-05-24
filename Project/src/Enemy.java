import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.image.BufferedImage;

abstract class Enemy {
	public int hp = 10, speed = 1;
	//public Bullet b;
	public int x, y;
	public Color enmyColor;
	boolean hit = false;
	public int imgh, imgw;
	public boolean remove = true;
	
	BufferedImage image;
	
	public boolean canRemove () {
		return false;
	}
	
	public void shoot () {}
	
	public boolean testHit (double tx, double ty) {
		return false;
	}
	
	abstract public void draw (Graphics g);
	
	abstract public void update (List fb, List eb, List bo, int px, int py);
}
