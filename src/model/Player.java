package model;

public class Player extends Entity {

	public Player(int xl, int yl, int xs, int ys) { super(xl, yl, xs, ys);}
	
	@Override
	public void update(int worldWidth, int worldHeight) {
		System.out.println("Updating");
		// move first
				SetLocation(GetLocation()[0]+GetSpeed()[0], 
						GetLocation()[1]+GetSpeed()[1]);

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
				SetLocation(GetLocation()[0],0); // clamp
				SetSpeed(GetSpeed()[0],0);
				}
				// Bottom wall
				else if (GetLocation()[1] + GetSize()[1] > worldHeight) {
				SetLocation(GetLocation()[0], worldHeight - GetSize()[1]); 
				SetSpeed(GetSpeed()[0],0);
				}
	}
	
}
