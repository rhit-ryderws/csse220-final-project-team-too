package model;

import java.util.HashMap;

/**
 * Class: Collide
 * @author Team Too
 * <br>Purpose: When given a size and location, the class tells information about collisions (depending on the method called)
 * <br>Restrictions: Does not directly contain any of the classes that has the collision checked, so must be updated to have
 * the correct collision information
 */
public class Collide {

	private static HashMap<String, int[]> walls = new HashMap<>();
	private static HashMap<String, int[]> movers = new HashMap<>();
//	
//	 In this case, HashMap is indexed by a string representing the entities name,
//	 with the structure "Entity" + "Number", ie "Wall1" The array stored for each
//	 entity holds four values, [x_location, y_location, x_size, y_size] where x &
//	 y location represent the location of the top left corner of the rectangle,
//	 and the x & y size represent the length of the hit-box rectangles
//	 

	/**
	 * <br>ensures: Adds the given entity to the stored data of the collide class and tells it is designation
	 * within the class
	 *
	 * @param type name of the given entity (ie "Player", "Enemy", etc.)<br>
	 * @param location the entity's current location [x,y]<br>
	 * @param size entity's size [w,h]<br>
	 * @return the string it is indexed by in the collide class
	 */
	public static String addEntity(String type, int[] location, int[] size) {
		int[] input = { location[0], location[1], size[0], size[1] };
		boolean valid = false;
		int i = 0;
		if (type.equals("Wall")) {
			while (!valid) {
				i++;
				if (!walls.containsKey(type + i)) {
					valid = true;
				}
			}
			walls.put(type + i, input);
		}
		if (type.equals("Player") || type.equals("Enemy")) {
			while (!valid) {
				i++;
				if (!movers.containsKey(type + i)) {
					valid = true;
				}
			}
			movers.put(type + i, input);
		}
		String name = type + i;
//		System.out.println(name);
		return name;
	}

	/**
	 * <br>ensures: Calculates the amount a given entity is displaced into a wall
	 *
	 * @param type name of the given entity (ie "Player", "Enemy", etc.)<br>
	 * @param location the entity's current location [x,y]<br>
	 * @param size entity's size [w,h]<br>
	 * @return an array [dx,dy], representing the overlap of the entity and all walls.<br>
	 */
	public static int[] getCollideWall(String type, int[] location, int[] size) {
		int[] location_current = location;
		// Iterate through list of entities
		for (String entity : walls.keySet()) {
			int dx = 0;
			int dy = 0;
			// Getting the specific wall's data, listed as described above
			int[] wall = walls.get(entity);
			// Making sure the entity is close enough to the wall to possibly collide
			if (((wall[0] - size[0] < location_current[0]) && (wall[0] + wall[2] > location_current[0]))
					&& ((wall[1] - size[1] < location_current[1]) && (wall[1] + wall[3] > location_current[1]))) {
				// Check to see if the block collides at the top of the wall
				if ((wall[1] + wall[3] / 2 > location_current[1] + size[1] / 2)
						&& (location_current[1] + size[1] > wall[1])) {
					dy += location_current[1] + size[1] - wall[1];
				} else if ((wall[1] + wall[3] / 2 < location_current[1] + size[1] / 2) // check bottom
						&& (wall[1] + wall[3] > location_current[1])) {
					dy += location_current[1] - (wall[1] + wall[3]);
				}
				// Check to see if the block collides at the left of the wall
				if ((wall[0] + wall[2] / 2 > location_current[0] + size[0] / 2)
						&& (location_current[0] + size[0] > wall[0])) {
					dx += location_current[0] + size[0] - wall[0];
				} else if ((wall[0] + wall[2] / 2 < location_current[0] + size[0] / 2) // check right
						&& (wall[0] + wall[2] > location_current[0])) {
					dx += location_current[0] - (wall[0] + wall[2]);
				}
			}

			// Once figuring out the displacement needed to get out of the block, the
			// location of the entity is reset to
			// the new calculated location.
			if ((Math.abs(dx) > Math.abs(dy))&&(dy!=0)) {
				dx = 0;
			} else if ((Math.abs(dx) < Math.abs(dy))&&(dx!=0)) {
				dy = 0;
			}
			int[] new_location = { location_current[0] - dx, location_current[1] - dy };
			location_current = new_location;
		}
		// Converting the final location to dx and dy, so that they can shift the final
		// output
		int[] out = { location[0] - location_current[0], location[1] - location_current[1] };
		return out;
	}

	/**
	 * <br>ensures: updates the collide data with new information, or adds a new datapoint if one previously didn't exist.
	 *
	 * @param name the name of the entity<br>
	 * @param location the entity's current location [x,y]<br>
	 * @param size entity's size [w,h]<br>
	 */
	public static void update(String name, int[] location, int[] size) {
		int[] input = { location[0], location[1], size[0], size[1] };
		movers.put(name, input);
//		if(name.substring(0, 4).equals("Play")) {
//			System.out.println("x: " + location[0] + "  y: "+location[1]);
//		}
	}
	
	/**
	 * <br>ensures: Clears the stored data of the collide class
	 */
	public static void reset() {
		walls.clear();
		movers.clear();
	}
	
	/**
	 * <br>ensures: Removes the given datapoint from the collide class if it exists
	 *
	 * @param name the name of the entity to be removed<br>
	 */
	public static void remove(String gone) {
			walls.remove(gone);
			movers.remove(gone);
	}

	/**
	 * <br>ensures: Determines whether or not the given entity is colliding with an enemy.
	 *
	 * @param type name of the given entity (ie "Player", "Enemy", etc.)<br>
	 * @param location the entity's current location [x,y]<br>
	 * @param size entity's size [w,h]<br>
	 * @return true if colliding, false if not
	 */
	public static boolean getCollideEnemy(String type, int[] location, int[] size) {
		int[] location_current = location;
		// Iterate through list of entities
		for (String entity : movers.keySet()) {
			if (entity.substring(0, 4).equals("Enem")&&(!entity.equals(type))) {
				int dx = 0;
				int dy = 0;
				// Getting the specific wall's data, listed as described above
				int[] enemy = movers.get(entity);
				// Making sure the entity is close enough to the wall to possibly collide
				if (((enemy[0] - size[0] < location_current[0]) && (enemy[0] + enemy[2] > location_current[0]))
						&& ((enemy[1] - size[1] < location_current[1])
								&& (enemy[1] + enemy[3] > location_current[1]))) {
					// Check to see if the block collides at the top of the wall
					if ((enemy[1] + enemy[3] / 2 > location_current[1] + size[1] / 2)
							&& (location_current[1] + size[1] > enemy[1])) {
						dy += location_current[1] + size[1] - enemy[1];
					} else if ((enemy[1] + enemy[3] / 2 < location_current[1] + size[1] / 2) // check bottom
							&& (enemy[1] + enemy[3] > location_current[1])) {
						dy += location_current[1] - (enemy[1] + enemy[3]);
					}
					// Check to see if the block collides at the left of the wall
					if ((enemy[0] + enemy[2] / 2 > location_current[0] + size[0] / 2)
							&& (location_current[0] + size[0] > enemy[0])) {
						dx += location_current[0] + size[0] - enemy[0];
					} else if ((enemy[0] + enemy[2] / 2 < location_current[0] + size[0] / 2) // check right
							&& (enemy[0] + enemy[2] > location_current[0])) {
						dx += location_current[0] - (enemy[0] + enemy[2]);
					}
				}
				// Once figuring out the displacement needed to get out of the block, the
				// function returns zero if it is non-zero
				if ((dy!=0)||(dx!=0)) {
					return true;
				}
			}
		}
		// Return false if no collisions found
		return false;
	}
	
	/**
	 * <br>ensures: Determines whether or not the given entity is colliding with the player.
	 *
	 * @param type name of the given entity (ie "Player", "Enemy", etc.)<br>
	 * @param location the entity's current location [x,y]<br>
	 * @param size entity's size [w,h]<br>
	 * @return true if colliding, false if not
	 */
	public static boolean getCollidePlayer(String type, int[] location, int[] size) {
		int[] location_current = location;
		// Iterate through list of entities
		for (String entity : movers.keySet()) {
			if (entity.substring(0, 4).equals("Play")&&(!entity.equals(type))) {
				int dx = 0;
				int dy = 0;
				// Getting the player's data, listed as described above
				int[] enemy = movers.get(entity);
				// Making sure the entity is close enough to the player to possibly collide
				if (((enemy[0] - size[0] < location_current[0]) && (enemy[0] + enemy[2] > location_current[0]))
						&& ((enemy[1] - size[1] < location_current[1])
								&& (enemy[1] + enemy[3] > location_current[1]))) {
					// Check to see if the block collides at the top of player
					if ((enemy[1] + enemy[3] / 2 > location_current[1] + size[1] / 2)
							&& (location_current[1] + size[1] > enemy[1])) {
						return true;
					} else if ((enemy[1] + enemy[3] / 2 < location_current[1] + size[1] / 2) // check bottom
							&& (enemy[1] + enemy[3] > location_current[1])) {
						return true;
					}
					// Check to see if the block collides at the left of player
					if ((enemy[0] + enemy[2] / 2 > location_current[0] + size[0] / 2)
							&& (location_current[0] + size[0] > enemy[0])) {
						return true;
					} else if ((enemy[0] + enemy[2] / 2 < location_current[0] + size[0] / 2) // check right
							&& (enemy[0] + enemy[2] > location_current[0])) {
						return true;
					}
					if ((enemy[0] + enemy[2]==location[0]+size[0])&&(enemy[1] + enemy[3]==location[1]+size[1])) {
						return true;
					}
					//returns true if any of the above cases are met
				}
			}
		}
		// Returns false if no collision found
		return false;
	}
}
