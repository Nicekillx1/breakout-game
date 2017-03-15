package game.objects;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

public abstract class GameObjects {
	protected int x, y;

	public GameObjects(int pos_x, int pos_y) {
		this.x = pos_x;
		this.y = pos_y;
	}

	public abstract Shape getShape();

	public abstract Image getImage();

	public abstract int getX();

	public abstract int getY();
}
