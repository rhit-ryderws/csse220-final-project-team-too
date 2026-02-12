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

	private String name;
	
	private static BufferedImage sprite = null;
	private static BufferedImage up = null;
	private static BufferedImage down = null;
	private static BufferedImage left = null;
	private static BufferedImage right = null;
	private static boolean triedLoad = false;

	public Enemy(int xl, int yl, int xs, int ys) {
		super(xl, yl, xs, ys);
		if((int)Math.floor(Math.random()*2) == 1){
			SetSpeed(5,0);
		} else {
			SetSpeed(0,5);
		}
		
//		SetSpeed((int)Math.floor(Math.random()*10), (int)Math.floor(Math.random()*10));
		loadSpriteOnce();
		
		int[] location = {xl,yl};
		int[] size = {xs,ys};
		this.name = Collide.addEntity("Enemy", location, size);
	}

	private static void loadSpriteOnce() {
		if (triedLoad)
			return;
		triedLoad = true;

		try {

			sprite = ImageIO.read(Square.class.getResource("Sprites.png"));
			up = sprite.getSubimage(104, 67, 20, 20);
			down = sprite.getSubimage(36, 73, 20, 20);
			left = sprite.getSubimage(3, 68, 20, 20);
			right = sprite.getSubimage(73, 72, 20, 20);
		} catch (IOException | IllegalArgumentException ex) {
			sprite = null;
		}
	}

	public void draw(Graphics2D g2) {
		if (sprite != null) {
			// sprite replaces the circle
			if(GetSpeed()[0] > 0) {
				g2.drawImage(right, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
			} else if(GetSpeed()[0] < 0) {
				g2.drawImage(left, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
			} else if(GetSpeed()[1] > 0) {
				g2.drawImage(down, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
			} else {
				g2.drawImage(up, GetLocation()[0], GetLocation()[1], GetSize()[0], GetSize()[1], null);
			}

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
		//Running Collisions
				int[] collision = Collide.getCollideWall(this.name, GetLocation(), GetSize());
				SetLocation(GetLocation()[0] - collision[0], GetLocation()[1] - collision[1]);
				if(collision[0] != 0) {
					SetSpeed(-GetSpeed()[0], GetSpeed()[1]);
				}
				if(collision[1] != 0) {
					SetSpeed(GetSpeed()[0], -GetSpeed()[1]);
				}
//				int[] hitEnemy = Collide.getCollideEnemy(this.name, GetLocation(), GetSize());
//				if(hitEnemy[0] != 0) {
//					SetSpeed(-GetSpeed()[0], GetSpeed()[1]);
//				}
//				if(hitEnemy[1] != 0) {
//					SetSpeed(GetSpeed()[0], -GetSpeed()[1]);
//				}
//				
				
		Collide.update(this.name, GetLocation(), GetSize());
	}
}
