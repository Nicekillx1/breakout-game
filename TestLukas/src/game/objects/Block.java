package game.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Block extends GameObjects {

	private Shape hitbox;
	private Image img;
	private int x, y, strength, width, height;
	private boolean collision;

	public Block(Image img, int pos_x, int pos_y, int width, int height, int strength) {
		super(pos_x, pos_y);
		this.img = img;
		this.x = pos_x;
		this.y = pos_y;
		this.strength = strength;
		this.hitbox = new Rectangle(pos_x, pos_y, width, height);
		this.width = width;
		this.height = height;
		this.collision = false;
	}

	public void update() {
		if (collision) {
			collision = false;
			strength -= 1;
		}
	}

	public boolean getCollision() {
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean intersect(Shape shape) {
		return this.hitbox.intersects(shape);

	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getStrength() {
		return strength;
	}

	@Override
	public Shape getShape() {
		// TODO Auto-generated method stub
		return hitbox;
	}

	@Override
	public Image getImage() {
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

}
