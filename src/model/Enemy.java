package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Enemy extends Entity {

	private int[] speed = GetSpeed();
	private int[] size = GetSize();
	private int[] position = GetLocation();

	private static BufferedImage sprite = null;
	private static boolean triedLoad = false;

	public Enemy(int xl, int yl, int xs, int ys) {
		super(xl, yl, xs, ys);
		SetSpeed(4, 5);
		loadSpriteOnce();
	}

	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
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

	@Override
	public void update(int worldWidth, int worldHeight) {
		
		SetLocation(GetLocation()[0] + GetSpeed()[0], GetLocation()[1] + GetSpeed()[1]);

		// Left wall
		if (GetLocation()[0] < 0) {
			SetLocation(0, GetLocation()[1]); // clamp
			SetSpeed(-GetSpeed()[0], GetSpeed()[1]);
		}
		// Right wall
		else if (GetLocation()[0] + GetSize()[0] > worldWidth) {
			SetLocation(worldWidth - GetSize()[0], GetLocation()[1]);
			SetSpeed(-GetSpeed()[0], GetSpeed()[1]);
		}

		// Top wall
		if (GetLocation()[1] < 0) {
			SetLocation(GetLocation()[0], 0); // clamp
			SetSpeed(GetSpeed()[0], -GetSpeed()[1]);
		}
		// Bottom wall
		else if (GetLocation()[1] + GetSize()[1] > worldHeight) {
			SetLocation(GetLocation()[0], worldHeight - GetSize()[1]);
			SetSpeed(GetSpeed()[0], -GetSpeed()[1]);
		}
	}//
}
