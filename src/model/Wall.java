package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall{
	int x_location;
	int y_location;
	int side;
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	public Wall(int x_location, int y_location, int side) {
		this.x_location = x_location;
		this.y_location = y_location;
		this.side = side;
		
		int[] location = {x_location,y_location};
		int[] sides = {side,side};
		
		loadSpriteOnce();
		Collide.addEntity("Wall", location, sides);
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {

			sprite = ImageIO.read(Square.class.getResource("Wall.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}
	
	public void draw(Graphics2D g2) {

		if (sprite != null) {
			// sprite replaces the circle
			g2.drawImage(sprite, x_location, y_location, side, side, null);
		} else {
			// fallback if sprite failed to load
			g2.setColor(Color.RED);
			g2.fillRect(x_location, y_location, side, side);
		}
	}
}
