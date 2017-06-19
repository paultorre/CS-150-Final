//Paul Torre
//Section: 03
import java.awt.Rectangle;

public class Bullet {
	private int x, y;

	public Bullet (int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void move() {
			y+=5;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle bounds () {
		return new Rectangle(getX(), getY(), 1, 1);
	}

}