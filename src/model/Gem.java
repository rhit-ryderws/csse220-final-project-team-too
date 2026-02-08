package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Gem extends Entity{

	private int[] size = GetSize();
	private int[] position = GetLocation();
	private boolean collected = false;

	private String name;
	
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	
	public Gem(int xl, int yl, int xs, int ys) {
		super(xl, yl, xs, ys);
		
		loadSpriteOnce();
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {

			sprite = ImageIO.read(Square.class.getResource("Gem.png"));
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}
	
	public void draw(Graphics2D g2) {
		if (sprite != null) {
			// sprite replaces the circle
			g2.drawImage(sprite, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);

		} else {
			// fallback if sprite failed to load
			g2.setColor(Color.GREEN);
			g2.fillRect(position[0], position[1], size[0], size[1]);
		}
	}
	
	@Override
	public void update(int worldWidth, int worldHeight) {

		this.collected = Collide.getCollidePlayer(this.name, GetLocation(), GetSize());
	}

	public boolean isCollected() {
		return this.collected;
	}
}
