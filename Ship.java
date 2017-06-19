//Paul Torre
//Section: 03

import java.awt.Rectangle;

public class Ship {

	private String pic;
	private int x, y, height, width;

	public Ship() {
		pic = "ship.png";
		x = 50;
		y = 10;
		height = 10;
		width = 10;
	}

	public Rectangle bounds () {
		return new Rectangle(getX(), getY(), 20, 20);
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public String getPic() {
		return pic;
	}

	public void moveX(double i) {
		x += i;
	}

	public int getWidth(){
		return width;
	}

	public int getHeight(){
		return height;
	}
}