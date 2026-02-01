package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends Entity{
	
	private int[] speed = GetSpeed();
	private int[] size = GetSize();
	private int[] position = GetLocation();
	
	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	
	public Enemy(int xl, int yl, int xs, int ys) {
		super(xl, yl, xs, ys);
		loadSpriteOnce();
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad) return;
		triedLoad = true;

		try {

		sprite = ImageIO.read(Square.class.getResource("ChatGPT_Zombie.png"));
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
		g2.setColor(Color.BLUE);
		g2.fillRect(position[0], position[1], size[0], size[1]);
		}
	}
	
	public void move() {
		position[0] += speed[0];
		position[1] += speed[1];
	}
	
	public void flip() {
		speed[0] = -speed[0];
	}
	
	@Override
	public void update(int ww, int wh) {
		// move first
		int x = position[0] + speed[0];
		int y = position[1] + speed[1];
		
		// Left wall
		if (x < 0) {
			x = 0; // clamp
			speed[0] = -speed[0];
		}
		// Right wall
		else if (x + size[0] > ww) {
			x = ww - size[0];
			speed[0] = -speed[0];
		}
		// Top wall
		if (y < 0) {
			y = 0;
			speed[1] = -speed[1];
		}
		// Bottom wall
		else if (y + size[1] > wh) {
			y = wh - size[1];
			speed[1] = -speed[1];
		}
	}//
}
