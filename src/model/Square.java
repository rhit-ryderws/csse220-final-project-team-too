package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Square {

	private int x, y;
	private int dx = 4; // direction + speed, 4 pixels per move
	private int dy = 4; // direction + speed

	private int width = 50;
	private int height = 50;

	private Rectangle square;

	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	public void draw(Graphics2D g2) {

		if (sprite != null) {
			// sprite replaces the circle
			g2.drawImage(sprite, x, y, width, height, null);
		} else {
			// fallback if sprite failed to load
			g2.setColor(Color.RED);
			g2.fillRect(x, y, width, height);
		}
	}

	public void moveRight() {
		x += dx;
	}

	public void moveLeft() {
		x += -dx;
	}

	public void moveUp() {
		y += -dy;
	}

	public void moveDown() {
		y += +dy;
	}

	public void update(int worldWidth, int worldHeight) {
		// move first
//	x += dx;
//	y += dy;

		// Left wall
		if (x < 0) {
			x = 0; // clamp
			dx = 0;
		}
		// Right wall
		else if (x > worldWidth) {
			x = worldWidth;
			dx = 0;
		}

		// Top wall
		if (y < 0) {
			y = 0;
			dy = 0;
		}
		// Bottom wall
		else if (y > worldHeight) {
			y = worldHeight;
			dy = 0;
		}
	}

	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {

			sprite = ImageIO.read(Square.class.getResource("CHANGETHIS.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}

	public Square(int x, int y) {
		this.x = x;
		this.y = y;
		loadSpriteOnce();
	}

}
