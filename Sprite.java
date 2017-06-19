//Paul Torre
//Section: 03
import java.awt.Rectangle;

public class Sprite {

	private String pic;
	private int x, y, height, width;

	public Sprite(int x, int y, String pic) {
		this.pic = pic;
		this.x = x;
		this.y = y;
		height = 10;
		width = 10;
	}

	public Rectangle bounds () {
		return new Rectangle(getX(), getY(), 5, 5);
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return this.y;
	}

	public int getX() {
		return this.x;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getPic() {
		return this.pic;
	}

}