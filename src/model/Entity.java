package model;

public abstract class Entity implements Renderable{
	private int[] speed; //speed[0] = x_speed; speed[1] = y_speed
	private int[] location; //location[0] = x_location; location[0] = y_location
	private int[] size; //size[0] = x_size; size[1] = y_size
	
	
	public Entity(int xl, int yl, int xs, int ys) {
		int[] location = {xl,yl};
		this.location = location;
		int[] size = {xs,ys};
		this.size = size;
	}
	
	public void SetSpeed(int x_speed, int y_speed) {
		int[] speed = {x_speed, y_speed};
		this.speed = speed;
	}
	
	public void SetLocation(int x_location, int y_location) {
		int[] location = {x_location, y_location};
		this.location = location;
	}
	
	public int[] GetSpeed() {return speed;}
	
	public int[] GetLocation() {return location;}
	
	public int[] GetSize() {return size;}
	
	@Override
	public void render() {
		System.out.println("Rendering ");
	}
	
	public abstract void update();
}
