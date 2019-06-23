import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.image.BufferedImage;

abstract class Enemy {
	public int hp = 10, speed = 1, point = 5;           //the points player gets after killing the enemy
	//public Bullet b;
	public int x, y;               //position of the enemy
	public Color enmyColor;
	boolean hit = false;      
	public int imgh, imgw;          //height and width of the enemy
	public boolean remove = true;
	public int type;               //type index
	
	BufferedImage image;           //image of the enemy
	
	abstract public boolean canRemove ();        //if remove==true or canRemove==true => remove the enemy
	
	abstract public boolean shoot ();             //different shooting method
	
	abstract public boolean testHit (double tx, double ty);         //test collision
	
	abstract public void draw (Graphics g);
	
	public void setHP(int delta) {
		hp-=delta;
	}
}
