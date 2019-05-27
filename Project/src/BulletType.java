import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.awt.event.ActionEvent;

class NormalBullet extends Bullet {
	int radius = 8;
	public NormalBullet(float nx, float ny, int dx, int dy) {
		this.dirX = dx;
		this.dirY = dy;
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
		
		//x = x-(radius/2);
		y = y-dirY*(radius/2);
		x = x-dirX*(radius/2);
		g.setColor(bullet_color);
		g.fillOval((int)x,(int)y,radius,radius);

	}

	@Override
	public void update(List fb, List eb, List bo, int px, int py) {
		// TODO Auto-generated method stub
		y-=speed;
	}

}

class EnemyABullet extends Bullet {
	int radius = 8;
	public EnemyABullet(float nx, float ny, int dx, int dy) {
		this.dirX = dx;
		this.dirY = dy;
		bullet_color = Color.WHITE;
		power = 1;
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
		//System.out.println(x+" "+y);
		this.speed = radius/2;
		g.setColor(bullet_color);
		//x = x-(radius/2);
		x-=dirX*speed;
		y+=dirY*speed;


		g.fillOval((int)x,(int)y,radius,radius);
	}

	@Override
	public void update(List fb, List eb, List bo, int px, int py) {

	}

}

class EnemyBBullet extends Bullet {
	int radius = 8;
	public EnemyBBullet(float nx, float ny, int dx, int dy) {
		this.dirX = dx;
		this.dirY = dy;
		bullet_color = Color.BLUE;
		power = 1;
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
		//System.out.println(x+" "+y);
		this.speed = radius/2;
		g.setColor(bullet_color);
		//x = x-(radius/2);
		x-=dirX*speed;
		y+=dirY*speed;


		g.fillOval((int)x,(int)y,radius,radius);
	}

	@Override
	public void update(List fb, List eb, List bo, int px, int py) {

	}

}
