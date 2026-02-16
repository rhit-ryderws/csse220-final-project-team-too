package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class: Trap
 * @author Team too
 * <br>Purpose: Used to instantiate the objects "trap" when the level is loaded.
 * <br>Restrictions:  A trap cannot be "disarmed".
 */
public class Trap extends Entity {

	private int[] speed = GetSpeed();
	private int[] size = GetSize();
	private int[] position = GetLocation();

	private String name;
	private boolean pressed;
	private boolean triggered;

	private static BufferedImage sprite1 = null;
	private static BufferedImage sprite2 = null;
	private static boolean triedLoad = false;
	
	/**
	 * ensures: initializes the wall to its location and defines its size. 
	 * @param x1 used to initialize the x component of the upper right corner location.
	 * <br>requires: x1 &ne; 0.0
	 * @param y1 used to initialize the y component of the upper right corner location
	 * <br>requires: y1 &ge; 0.0
	 * @param xs used to initialize the length of the trap.
	 * <br>requires: x1 &ne; 0.0
	 * @param xs used to initialize the height of the trap.
	 * <br>requires: x1 &ne; 0.0
	 */

	public Trap(int xl, int yl, int xs, int ys) {
		super(xl, yl, xs, ys);
		SetSpeed(0, 0);

		loadSpriteOnce();
	}
	
	/**
	 * ensures: loads the sprite from the image sheet once.  If the load fails the sprite reference is set to null. 
	 */
	
	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {

			sprite1 = ImageIO.read(Square.class.getResource("Sprites.png"));
			sprite1 = sprite1.getSubimage(102, 101, 20, 20);
		} catch (IOException | IllegalArgumentException ex) {
			sprite1 = null;
		}
		try {

			sprite2 = ImageIO.read(Square.class.getResource("Sprites.png"));
			sprite2 = sprite2.getSubimage(6, 135, 20, 20);
		} catch (IOException | IllegalArgumentException ex) {
			sprite2 = null;
		}
	}

	/**
	 * ensures: This method is used to draw the objects current state depending on whether the trap has been triggered.
	 * 			If a sprite fails to load, the trap is displayed as a dark gray box.
	 * @param g2 used to give context required to draw.
	 * <br>requires: g2 &ne; ""
	 */
	public void draw(Graphics2D g2) {
		if (!triggered) {
			if (sprite1 != null) {
				// sprite replaces the circle
				g2.drawImage(sprite1, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);

			} else {
				// fallback if sprite failed to load
				g2.setColor(Color.lightGray);
				g2.fillRect(position[0], position[1], size[0], size[1]);
			}
		}
		if (triggered) {
			if (sprite2 != null) {
				// sprite replaces the circle
				g2.drawImage(sprite2, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);

			} else {
				// fallback if sprite failed to load
				g2.setColor(Color.darkGray);
				g2.fillRect(position[0], position[1], size[0], size[1]);
			}
		}
	}
	
	
	/**
	 * ensures: This method removes the current trap from the hash map in collide and defaults triggered and pressed to false.
	 */
	
	public void reset() {
		if(triggered) {
			Collide.remove(name);
		}
		triggered = false;
		pressed = false;
	}


	/**
	 * ensures: This method is used to check if the player has stepped on a trap, stepped off the trap and the trap has not been
	 * 			already pressed.  If these conditions are met, the trap will arm.
	 * @param worldWidth is used to give context to the super class entity to check if valid bounds were given.
	 * <br>requires: worldWidth &ne; 0.0
	 * @param worldHeight is used to give context to the super class entity to check if valid bounds were given.
	 * <br>requires: worldHeight &ne; 0.0
	 */
	
	@Override
	public void update(int worldWidth, int worldHeight) {

		// Running Collisions
		boolean collision = Collide.getCollidePlayer(this.name, GetLocation(), GetSize());
		if (collision == true) {
			pressed = true;
		}
		if (pressed == true && triggered == false && collision == false) {
			triggered = true;
			this.name = Collide.addEntity("Enemy", GetLocation(), GetSize());
		}
	}
}
