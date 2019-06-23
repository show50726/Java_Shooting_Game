import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;


abstract class Bullet {              
	public float x, y;                   //position of the bullet
	public int dirX, dirY;                 //flying direction
	public int power = 0, speed = 1;           
	public boolean hit = false, remove = false;
	public Color bullet_color = Color.WHITE;
	
	public boolean canRemove () {              //if remove==true or canRemove==true => remove the bullet
		return hit;
	}
	
	public boolean testHit (double x, double y) { return false; }              //test collision
	
	abstract public void draw (Graphics g);
}
