package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class: TempWall
 * @author Team too
 * <br>Purpose: Used to instantiate the objects "TempWall" when the player presses a button
 */

public class TempWall {
	int x_location;
	int y_location;
	int side;
	private String name;
	
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	
	/**
	 * ensures: initializes the wall to its location and defines its size. This method also calls the collide class to
	 * 			determine if the player is colliding with it to prevent motion.  If the wall would be placed into an existing wall,
	 * 			the wall is "shifted" to be in line. 
	 * @param x_location used to initialize the x component of the upper right corner location.
	 * <br>requires: x_location &ne; 0.0
	 * @param y_location used to initialize the y component of the upper right corner location
	 * <br>requires: y_location &ge; 0.0
	 * @param side used to initialize the sides of the trap.  This creates a square because all sides are the same size.
	 * <br>requires: side &ne; 0.0
	 */

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
	
	/**
	 * ensures: this method removes the temp wall from the hash map in the collide class.
	 */
	public void remove() {
		Collide.remove(name);
	}
	
	/**
	 * ensures: loads the sprite from the image sheet once.  If the load fails the sprite reference is set to null. 
	 */
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
	
	/**
	 * ensures: This method is used to draw the objects current state depending on whether the trap has been triggered.
	 * 			If a sprite fails to load, the trap is displayed as a dark gray box.
	 * @param g2 used to give context required to draw.
	 * <br>requires: g2 &ne; ""
	 */
	
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
