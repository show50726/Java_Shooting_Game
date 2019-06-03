import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;

abstract class Bullet {
	public float x, y;
	public int dirX, dirY;
	public int power = 0, dimension = 5, speed = 1;
	public boolean hit = false, remove = false;
	public Color bullet_color = Color.WHITE;
	
	abstract public boolean canRemove();
	abstract public boolean testHit (double x, double y);
	abstract public void draw (Graphics g);
}
