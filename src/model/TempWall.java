package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TempWall {
	int x_location;
	int y_location;
	int side;
	private String name;
	
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	public TempWall(int x_location, int y_location, int side) {
		this.x_location = x_location;
		this.y_location = y_location;
		this.side = side;
		
		int[] location = {x_location,y_location};
		int[] sides = {side,side};
		
		int[] collision = Collide.getCollideWall(this.name, location, sides);
		this.x_location = location[0] - collision[0];
		this.y_location = location[1] - collision[1];
		
		loadSpriteOnce();
		this.name = Collide.addEntity("Wall", location, sides);
	}
	
	public void remove() {
		Collide.remove(name);
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {
			sprite = ImageIO.read(Square.class.getResource("Sprites.png"));
			sprite = sprite.getSubimage(37, 101, 25, 25);
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
			g2.setColor(Color.MAGENTA);
			g2.fillRect(x_location, y_location, side, side);
		}
	}
}
