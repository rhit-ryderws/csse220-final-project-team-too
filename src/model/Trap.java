package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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

	public Trap(int xl, int yl, int xs, int ys) {
		super(xl, yl, xs, ys);
		SetSpeed(0, 0);

		loadSpriteOnce();
	}

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
	
	public void reset() {
		if(triggered) {
			Collide.remove(name);
		}
		triggered = false;
		pressed = false;
	}

	@Override
	public void update(int worldWidth, int worldHeight) {

		SetLocation(GetLocation()[0] + GetSpeed()[0], GetLocation()[1] + GetSpeed()[1]);

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
