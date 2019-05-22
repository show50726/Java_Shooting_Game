import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.ActionEvent;

class NormalBullet extends Bullet {
	
	int radius = 10;
	public NormalBullet(float nx, float ny) {
		bullet_color = Color.RED;
		power = 1;
		x = nx;
		y = ny;
	}
	
	@Override
	public boolean canRemove() {
		if(remove) return true;
		return false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		System.out.println(x+" "+y);
		g.setColor(bullet_color);
		//x = x-(radius/2);
		y = y-(radius/2);
		g.fillOval((int)x,(int)y,radius,radius);
		
	}

	@Override
	public void update(List fb, List eb, List bo, int px, int py) {
		// TODO Auto-generated method stub
		y-=speed;
	}



}
