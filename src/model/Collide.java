package model;

import java.util.HashMap;

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
	 * Calculates the amount a given entity is displaced into a wall
	 *
	 * @param type     name of the given entity (ie "Player", "Enemy", etc.)
	 * @param location the entity's current location [x,y]
	 * @param the      entity's size [w,h]
	 * @return an array [dx,dy], representing the overlap of the entity and all
	 *         walls.
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

	public static void update(String name, int[] location, int[] size) {
		int[] input = { location[0], location[1], size[0], size[1] };
		movers.put(name, input);
//		if(name.substring(0, 4).equals("Play")) {
//			System.out.println("x: " + location[0] + "  y: "+location[1]);
//		}
	}
	
	public static void reset() {
		walls.clear();
		movers.clear();
	}
	
	public static void remove(String gone) {
			walls.remove(gone);
			movers.remove(gone);
	}

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
				// location of the entity is reset to
				// the new calculated location.
				if ((dy!=0)||(dx!=0)) {
					return true;
				}
			}
		}
		// Converting the final location to dx and dy, so that they can shift the final
		// output
		return false;
	}
	
	public static boolean getCollidePlayer(String type, int[] location, int[] size) {
		int[] location_current = location;
		// Iterate through list of entities
		for (String entity : movers.keySet()) {
			if (entity.substring(0, 4).equals("Play")&&(!entity.equals(type))) {
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
						return true;
					} else if ((enemy[1] + enemy[3] / 2 < location_current[1] + size[1] / 2) // check bottom
							&& (enemy[1] + enemy[3] > location_current[1])) {
						return true;
					}
					// Check to see if the block collides at the left of the wall
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
				}
			}
		}
		// Converting the final location to dx and dy, so that they can shift the final
		// output
		return false;
	}
}
