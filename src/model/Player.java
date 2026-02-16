package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity {

	private static BufferedImage sprite = null;
	private static BufferedImage up1 = null;
	private static BufferedImage down1 = null;
	private static BufferedImage left1 = null;
	private static BufferedImage right1 = null;
	private static BufferedImage up2 = null;
	private static BufferedImage down2 = null;
	private static BufferedImage left2 = null;
	private static BufferedImage right2 = null;
	
	private static boolean triedLoad = false;
	private String name;
	private int[] start_location;
	private int lives;
	private int score;
	private String direction = "DOWN";
	private int wallCount = 0;
	private int animate = 0;

	public Player(int xl, int yl, int xs, int ys) {
		super(xl, yl, xs, ys);
		SetSpeed(0, 0);
		loadSpriteOnce();

		int[] location = { xl, yl };
		int[] size = { xs, ys };
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
			sprite = ImageIO.read(Square.class.getResource("Sprites.png"));
			up1 = sprite.getSubimage(69, 7, 20, 20);
			down1 = sprite.getSubimage(71, 37, 20, 20);
			left1 = sprite.getSubimage(7, 39, 20, 20);
			right1 = sprite.getSubimage(5, 5, 20, 20);
			up2 = sprite.getSubimage(101, 7, 20, 20);
			down2 = sprite.getSubimage(103, 37, 20, 20);
			left2 = sprite.getSubimage(39, 39, 20, 20);
			right2 = sprite.getSubimage(37, 5, 20, 20);
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}

	public void draw(Graphics2D g2) {

		if (sprite != null) {
			if(animate < 10) {
				if(direction == "RIGHT") {
					g2.drawImage(right1, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
				} else if(direction == "LEFT") {
					g2.drawImage(left1, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
				} else if(direction == "DOWN") {
					g2.drawImage(down1, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
				} else {
					g2.drawImage(up1, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
				}
			} else {
				if(direction == "RIGHT") {
					g2.drawImage(right2, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
				} else if(direction == "LEFT") {
					g2.drawImage(left2, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
				} else if(direction == "DOWN") {
					g2.drawImage(down2, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
				} else {
					g2.drawImage(up2, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
				}
			}
		} else {
			// fallback if sprite failed to load
			g2.setColor(Color.ORANGE);
			g2.fillRect(GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1]);
		}
	}

	public void setSpawn(int[] location) {
		start_location = location;
	}

	public TempWall placeWall() {
		if (wallCount == 0 && GetSpeed()[0] == 0 && GetSpeed()[1] == 0) {
			TempWall tempWall;
			wallCount = 20;
			if(direction == "UP") {
				tempWall = new TempWall(GetLocation()[0] - 5, GetLocation()[1] - 50, 50);
			} else if (direction == "DOWN") {
				tempWall = new TempWall(GetLocation()[0] - 5, GetLocation()[1] + GetSize()[1], 50);
			} else if (direction == "RIGHT") {
				tempWall = new TempWall(GetLocation()[0] + GetSize()[0], GetLocation()[1] - 5, 50);
			} else {
				tempWall = new TempWall(GetLocation()[0] - 50, GetLocation()[1] - 5, 50);
			}
			return tempWall;
		}
		return null;
	}

	@Override
	public void update(int worldWidth, int worldHeight) {
		if (GetSpeed()[0] > 0) {
			direction = "RIGHT";
		} else if (GetSpeed()[0] < 0) {
			direction = "LEFT";
		} else if (GetSpeed()[1] < 0) {
			direction = "UP";
		} else if (GetSpeed()[1] > 0) {
			direction = "DOWN";
		}

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
		// Running Collisions
		int[] collision = Collide.getCollideWall(this.name, GetLocation(), GetSize());
		SetLocation(GetLocation()[0] - collision[0], GetLocation()[1] - collision[1]);

		Collide.update(this.name, GetLocation(), GetSize());

		if (Collide.getCollideEnemy(this.name, GetLocation(), GetSize())) {
			SetLocation(start_location[0], start_location[1]);
			lives--;
		}
		if(wallCount > 0) {
		wallCount--;
		}
		if(GetSpeed()[0]!=0||GetSpeed()[1]!=0) {
			animate++;
			if(animate > 20) animate = 0;
		}
	}

}
