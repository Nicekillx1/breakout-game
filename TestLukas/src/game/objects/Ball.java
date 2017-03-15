package game.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

import gamestates.Game;
import gamestates.Play;

public class Ball extends GameObjects {

	private Shape hitbox, hitbox2;
	private Image img;
	private int radius;
	private int y, x;
	private final float g = 0.0000681f;
	private float m;
	private float[] speed = new float[2];
	private boolean collision;

	public Ball(Image img, int pos_x, int pos_y, int radius, float m, float[] speed) {
		super(pos_x, pos_y);
		this.img = img;
		this.hitbox = new Circle(pos_x, pos_y, radius);
		this.hitbox2 = new Circle(pos_x, pos_y, radius);
		this.x = pos_x;
		this.y = pos_y;
		this.m = m;
		this.radius = radius;
		this.collision = false;
		this.speed[0] = speed[0];
		this.speed[1] = speed[1];
	}

	public Image getImage() {
		return this.img;
	}

	public void update(int delta, Input in) {
		hitbox2.setLocation(speed[0] * delta + x, speed[1] * delta + y);
		if (hitbox2.getX() <= 0 || hitbox2.getMaxX() >= Game.getWindowX()) {
			speed[0] = -speed[0];
		}
		if (hitbox2.getY() <= 30) {
			speed[1] = -speed[1];
		}
		// stick mit Speed
		if (Play.getStick().intersect(hitbox2) && hitbox2.getY() + radius <= Game.getWindowY() - 40) {
			speed[1] = -speed[1];
			Play.getHitStick().play();
			if (Play.getStick().getX() != 0
					&& Play.getStick().getX() + Play.getStick().getShape().getWidth() != Game.getWindowX()) {
				if (in.isKeyDown(Input.KEY_RIGHT)) {
					speed[0] += 0.02f;
				}
				if (in.isKeyDown(Input.KEY_LEFT)) {
					speed[0] -= 0.02f;
				}
			}
		}
		// verloren
		if (Play.getStick().intersect(hitbox2) && hitbox2.getY() + radius > Game.getWindowY() - 40) {
			Play.getHitStick().play();
			if (in.isKeyDown(Input.KEY_RIGHT)) {
				speed[0] += Play.getStick().getspeed();
			} else if (in.isKeyDown(Input.KEY_LEFT)) {
				speed[0] -= Play.getStick().getspeed();
			} else {
				speed[0] = -speed[0];
			}
		}

		// blockcollison
		for (Block b : Play.getBlock()) {
		
			if (b.getShape().intersects(hitbox2)) {
				collision = !collision;
				Play.getHitBlock().play();
				// if (x +radius == b.getX()
				// || x ==b.getX() ) {
				// speed[0] = -speed[0];
				// speed[1] = -speed[1];
				// }
				if (x < b.getX() + b.getWidth() / 2 && y + radius / 2 >= b.getY()
						&& y + radius / 2 < b.getY() + b.getHeight() && b.getCollision() == false) {
					speed[0] = -speed[0];
					b.setCollision(true);
					break;
				}
				if (x > b.getX() + b.getWidth() / 2 && y + radius / 2 >= b.getY()
						&& y + radius / 2 < b.getY() + b.getHeight() && b.getCollision() == false) {
					speed[0] = -speed[0];
					b.setCollision(true);
					break;
				}
				if (y > b.getY() + b.getHeight() / 2 && x + radius / 2 >= b.getX()
						&& x + radius / 2 < b.getX() + b.getWidth() && b.getCollision() == false) {
					speed[1] = -speed[1];
					b.setCollision(true);
					break;
				}
				if (y < b.getY() + b.getHeight() / 2 && x + radius / 2 >= b.getX()
						&& x + radius / 2 < b.getX() + b.getWidth() && b.getCollision() == false) {
					speed[1] = -speed[1];
					b.setCollision(true);
					break;
				}
			}
		}
		if (y + radius > Game.getWindowY()) {
			Play.setRunning(false);
			Play.setBeendet(true);

		}

		x += speed[0] * delta;
		y += speed[1] * delta;
		hitbox.setLocation(x, y);

	}

	public boolean getCollision() {
		return collision;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public void setGravitation(float y) {
		speed[1] = y;
	}

	public Shape getShape() {
		return hitbox;
	}

	public float getMasse() {
		return m;
	}

	public float getSpeedY() {
		return speed[1];
	}

	public float getSpeedX() {
		return speed[0];
	}

	public float getOrtF() {
		return g;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}