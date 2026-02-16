package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class: Wall
 * @author Team too
 * <br>Purpose: Used to instantiate the objects "wall" when the level is loaded.
 * <br>Restrictions: Cannot be placed on top of an existing wall
 */
public class Wall{
	int x_location;
	int y_location;
	int side;
	private String name;
	
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	/**
	 * ensures: initializes the wall to its location and defines its size. 
	 * @param x_location used to initialize the x component of the upper right corner location.
	 * <br>requires: x_location &ne; 0.0
	 * @param y_location used to initialize the y component of the upper right corner location
	 * <br>requires: y_location &ge; 0.0
	 * @param side used to initialize the overall size of the wall.  Because wall is a square, only one side length is needed.
	 * <br>requires: side &ge; 0.0
	 */
	public Wall(int x_location, int y_location, int side) {
		this.x_location = x_location;
		this.y_location = y_location;
		this.side = side;
		
		int[] location = {x_location,y_location};
		int[] sides = {side,side};
		
		loadSpriteOnce();
		this.name = Collide.addEntity("Wall", location, sides);
	}
	
	
	/**
	 * ensures: attempts to add a sprite to the square and throws an exception if no sprite is detected .
	 */
	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {

			sprite = ImageIO.read(Square.class.getResource("Sprites.png"));
			sprite = sprite.getSubimage(5, 101, 25, 25);
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}
	
	/**
	 * ensures: Draws the walls.  If a sprite is detected the wall will contain the sprite, if not, the wall will be a red square. 
	 * @param g2 is the context needed to draw.
	 * <br>requires: g2 &ne; ""
	 */
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
