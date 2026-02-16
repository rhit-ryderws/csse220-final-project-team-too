package model;


/**
 * Class: Entity
 * @author Team Too
 * <br>Purpose: Serves as an abstract class for all moving entities
 * <br>Restrictions: Specifically intended for moving entities, initially speed is null so must be set manually.
 */
public abstract class Entity{
	private int[] speed; // speed[0] = x_speed; speed[1] = y_speed
	private int[] location; // location[0] = x_location; location[0] = y_location
	private int[] size; // size[0] = x_size; size[1] = y_size

	/**
	 * ensures: initializes the entity with the given location and size. <br>
	 * @param xl the x-location of the top-left corner of the entity <br>
	 * @param yl the y-location of the top-left corner of the entity <br>
	 * @param xs the width of the entity <br>
	 * @param ys the height of the entity
	 */
	public Entity(int xl, int yl, int xs, int ys) {
		int[] location = { xl, yl };
		this.location = location;
		int[] size = { xs, ys };
		this.size = size;
	}

	/**
	 * ensures: sets the speed of the entity to the given values
	 * @param x_speed the speed in the x direction (right positive)
	 * @param y_speed the speed in the y direction (down positive)
	 */
	public void SetSpeed(int x_speed, int y_speed) {
		int[] speed = { x_speed, y_speed };
		this.speed = speed;
	}

	/**
	 * ensures: sets the location of the entity to the given values
	 * @param x_location the location of the top left corner in the x direction (right positive)
	 * @param y_location the location of the top left corner in the y direction (down positive)
	 */
	public void SetLocation(int x_location, int y_location) {
		int[] location = { x_location, y_location };
		this.location = location;
	}

	/**
	 * ensures: Gets the speed 
	 * returns: the speed as an array [x_speed, y_speed]
	 */
	public int[] GetSpeed() {
		return speed;
	}

	/**
	 * ensures: Gets the location
	 * returns: the speed as an array [x_location, y_location]
	 */
	public int[] GetLocation() {
		return location;
	}

	/**
	 * ensures: Gets the size
	 * returns: the speed as an array [x_size, y_size]
	 */
	public int[] GetSize() {
		return size;
	}

	public abstract void update(int worldWidth, int worldHeight);
}
