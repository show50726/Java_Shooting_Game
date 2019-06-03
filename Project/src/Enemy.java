import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.image.BufferedImage;

abstract class Enemy {
	public int hp = 10, speed = 1, point = 5;
	//public Bullet b;
	public int x, y;
	public Color enmyColor;
	public int imgh, imgw;
	public boolean remove = true;
	public int type;
	
	BufferedImage image;
	
	abstract public boolean canRemove ();
	
	abstract public boolean shoot ();
	
	abstract public boolean testHit (double tx, double ty);
	
	abstract public void draw (Graphics g);
	
	public void setHP(int delta) {
		hp-=delta;
	}
}
