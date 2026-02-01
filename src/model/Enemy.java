package model;

import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy extends Entity{
	
	private int[] speed = GetSpeed();
	private int[] size = GetSize();
	private int[] position = GetLocation();
	
	public Enemy(int xl, int yl, int xs, int ys) {
		super(xl, yl, xs, ys);
	}
	
	public void draw(Graphics2D g2) {
		g2.setColor(Color.BLUE);
		g2.fill(null);
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
