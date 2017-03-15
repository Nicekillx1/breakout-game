package game.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import gamestates.Game;

public class Stick extends GameObjects {

	private int x, y;
	private float m;
	private Shape hitbox;
	private Image img;
	private float speed = 0.7f;

	public Stick(Image img, int pos_x, int pos_y, int width, int height, int m) {
		super(pos_x, pos_y);
		this.hitbox = new Rectangle(pos_x, pos_y, width, height);
		this.x = pos_x;
		this.y = pos_y;
		this.img = img;
		this.setM(m);
	}

	public void control(Input in, int delta) {
		if ((in.isKeyDown(Input.KEY_A) || in.isKeyDown(Input.KEY_LEFT)) && (x > 0)) {
			x -= speed * delta;
			this.hitbox.setX(x);
		}
		if ((in.isKeyDown(Input.KEY_D) || in.isKeyDown(Input.KEY_RIGHT))
				&& (x < Game.getWindowX() - hitbox.getWidth())) {
			x += speed * delta;
			this.hitbox.setX(x);

		}
	}

	public boolean intersect(Shape shape) {
		return this.hitbox.intersects(shape);

	}

	public float getspeed() {
		return speed;
	}

	@Override
	public Shape getShape() {
		return hitbox;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return img;
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}

	public float getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}
}
