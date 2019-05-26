import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;


abstract class Bullet {
	public float x, y;
	
	public int power = 0, dimension = 5, speed = 1;
	public boolean hit = false, remove = false;
	public Color bullet_color = Color.WHITE;
	
	public boolean canRemove () {
		return hit;
		
	}
	
	public boolean testHit (double x, double y) { return false; }
	
	abstract public void draw (Graphics g);
	abstract public void update (List fb, List eb, List bo, int px, int py);
}
