package model;

public class Player extends Entity {

	public Player(int xl, int yl, int xs, int ys) { super(xl, yl, xs, ys);}
	
	@Override
	public void update() {
		System.out.println("Updating");
		
	}
	
}
