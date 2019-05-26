import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.ActionEvent;

class NormalBullet extends Bullet {
	
	int radius = 10;
	public NormalBullet(float nx, float ny) {
		bullet_color = Color.RED;
		power = 2;
		x = nx;
		y = ny;
	}
	
	@Override
	public boolean canRemove() {
		if(x<=0||x>=PlayerController.SCREEN_WIDTH||remove) {
			return true;
		}
		if(y<=0||y>=PlayerController.SCREEN_HEIGHT) {
			return true;
		}
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

class EnemyABullet extends Bullet {
	int dir;
	int radius = 10;
	public EnemyABullet(float nx, float ny, int dir) {
		this.dir = dir;
		bullet_color = Color.WHITE;
		power = 1;
		x = nx;
		y = ny;
	}
	
	@Override
	public boolean canRemove() {
//		if(x<=0||x>=PlayerController.SCREEN_WIDTH||remove) {
//			return true;
//		}
//		if(y<=0||y>=PlayerController.SCREEN_HEIGHT) {
//			return true;
//		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		//System.out.println(x+" "+y);
		this.speed = radius/2;
		g.setColor(bullet_color);
		//x = x-(radius/2);
		if(this.dir==-1) {
			y+=speed;
			x-=speed;
		}
		else if(dir==0) {
			y = y+speed;
		}
		else {
			x+=speed;
			y+=speed;
		}
		y = y+(radius/2);
		g.fillOval((int)x,(int)y,radius,radius);
	}

	@Override
	public void update(List fb, List eb, List bo, int px, int py) {

	}

}
