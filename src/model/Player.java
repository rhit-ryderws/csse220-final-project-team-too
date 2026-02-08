package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {

	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;
	private String name;
	private int[] start_location;
	private int lives;
	private int score;

	public Player(int xl, int yl, int xs, int ys) {
		super(xl, yl, xs, ys);
		SetSpeed(0, 0);
		loadSpriteOnce();
		
		int[] location = {xl,yl};
		int[] size = {xs,ys};
		this.name = Collide.addEntity("Player", location, size);
		
		this.lives = 3;
		this.score = 0;
	}
	public int getLives() {
		return lives;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setLives(int lives) {
		this.lives = lives;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {

			sprite = ImageIO.read(Square.class.getResource("LoreAccurateSteve.png"));
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
			g2.setColor(Color.RED);
			g2.fillRect(GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1]);
		}
	}
	
	public void setSpawn(int[] location) {
		start_location = location;
	}

	@Override
	public void update(int worldWidth, int worldHeight) {
		// move first
		SetLocation(GetLocation()[0] + GetSpeed()[0], GetLocation()[1] + GetSpeed()[1]);

		// Left wall
		if (GetLocation()[0] < 0) {
			SetLocation(0, GetLocation()[1]); // clamp
			SetSpeed(0, GetSpeed()[1]);
		}
		// Right wall
		else if (GetLocation()[0] + GetSize()[0] > worldWidth) {
			SetLocation(worldWidth - GetSize()[0], GetLocation()[1]);
			SetSpeed(0, GetSpeed()[1]);
		}

		// Top wall
		if (GetLocation()[1] < 0) {
			SetLocation(GetLocation()[0], 0); // clamp
			SetSpeed(GetSpeed()[0], 0);
		}
		// Bottom wall
		else if (GetLocation()[1] + GetSize()[1] > worldHeight) {
			SetLocation(GetLocation()[0], worldHeight - GetSize()[1]);
			SetSpeed(GetSpeed()[0], 0);
		}
		//Running Collisions
		int[] collision = Collide.getCollideWall(this.name, GetLocation(), GetSize());
		SetLocation(GetLocation()[0] - collision[0], GetLocation()[1] - collision[1]);
		
		Collide.update(this.name, GetLocation(), GetSize());
		
		if(Collide.getCollideEnemy(this.name, GetLocation(), GetSize())) {
			SetLocation(start_location[0],start_location[1]);
			lives--;
		}
	}

}
