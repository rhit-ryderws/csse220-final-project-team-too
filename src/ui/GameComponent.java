package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

import model.Square;
import model.TempWall;
import model.Trap;
import model.Wall;
import model.Collide;
import model.Enemy;
import model.Gem;
import model.Player;

public class GameComponent extends JComponent {

	private Player player;
	private Timer timer;
	public static final int WIDTH = 614;
	public static final int HEIGHT = 636;
	public static final int TILE_SIZE = 50;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<Wall> walls = new ArrayList<>();
	private Rectangle rect;
	private JLabel label;
	private ArrayList<Gem> gems = new ArrayList<>();
	private ArrayList<Trap> traps = new ArrayList<>();
	private ArrayList<TempWall> tempwalls = new ArrayList<>();
	private int level = 1;
	private int current_lives = 3;

	private boolean W;
	private boolean A;
	private boolean S;
	private boolean D;

	public GameComponent() {

		// Reading from .txt file
		loadLevel(level);

		// Setting up timer
		timer = new Timer(20, e -> {
			playerKeys();
			if (current_lives != player.getLives()) {
				for (Trap trap : traps) {
					trap.reset();
				}
				for (TempWall tempWall : tempwalls) {
					tempWall.remove();
				}
				tempwalls.clear();
			}
			current_lives = player.getLives();
			if (player.getScore() == 5) {
				level++;
				loadLevel(level);
			}
			player.update(WIDTH, HEIGHT);
			for (Enemy enemy : enemies) {
				enemy.update(WIDTH, HEIGHT);
			}
			if (gems.size() != 0) {
				for (Gem gem : gems) {
					gem.update(WIDTH, HEIGHT);
				}
			}
			for (Trap trap : traps) {
				trap.update(WIDTH, HEIGHT);
			}
			repaint();

			gameEnd();
		});
		timer.start();

		setFocusable(true);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					A = true;
					break;
				case KeyEvent.VK_D:
					D = true;
					break;
				case KeyEvent.VK_W:
					W = true;
					break;
				case KeyEvent.VK_S:
					S = true;
					break;
				}
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					A = false;
					break;
				case KeyEvent.VK_D:
					D = false;
					break;
				case KeyEvent.VK_W:
					W = false;
					break;
				case KeyEvent.VK_S:
					S = false;
					break;
				case KeyEvent.VK_SPACE:
					TempWall newWall = player.placeWall();
					if (newWall != null)
						tempwalls.add(newWall);
					else {
					}
					break;
				case KeyEvent.VK_R:
					current_lives = 3;
					level = 1;
					loadLevel(1);
					break;
				}
			}
		});

	}

	private void playerKeys() {
		if (S) {
			player.SetSpeed(player.GetSpeed()[0], 4);
		} else if (W) {
			player.SetSpeed(player.GetSpeed()[0], -4);
		} else {
			player.SetSpeed(player.GetSpeed()[0], 0);
		}
		if (D) {
			player.SetSpeed(4, player.GetSpeed()[1]);
		} else if (A) {
			player.SetSpeed(-4, player.GetSpeed()[1]);
		} else {
			player.SetSpeed(0, player.GetSpeed()[1]);
		}
	}

	
	/*
	 * Some Notes regarding the loading of levels:
	 * When loading a level, there is a specific "Key" for loading a level from text file:
	 * "-" -> Empty Tile
	 * "P" -> Player Start Location
	 * "E" -> Enemy Start Location
	 * "W" -> Wall Tile
	 * "G" -> Gem Location
	 * "T" -> Trap Location
	 */
	private void loadLevel(int n) {
		enemies.clear();
		walls.clear();
		gems.clear();
		traps.clear();
		tempwalls.clear();
		Collide.reset();
		int row = 0;
		try {
			Scanner scanner = new Scanner(new File("level" + n + ".txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				for (int col = 0; col < line.length(); col++) {
					char c = line.charAt(col);

					if (c == 'P') {
						player = new Player(col * TILE_SIZE + 5, row * TILE_SIZE + 5, 40, 40);
						int[] spawn = { col * TILE_SIZE + 5, row * TILE_SIZE + 5 };
						player.setSpawn(spawn);
						player.setLives(current_lives);
					} else if (c == 'E') {
						Enemy enemy = new Enemy(col * TILE_SIZE + 5, row * TILE_SIZE + 5, 40, 40);
						enemies.add(enemy);
					} else if (c == 'W') {
						Wall wall = new Wall(col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE);
						walls.add(wall);
					} else if (c == 'G') {
						Gem gem = new Gem(col * TILE_SIZE + 13, row * TILE_SIZE + 13, 24, 24);
						gems.add(gem);
					} else if (c == 'T') {
						Trap trap = new Trap(col * TILE_SIZE + 5, row * TILE_SIZE + 5, 40, 40);
						traps.add(trap);
					}
				}

				row++;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("level1.txt not found");
		}
	}

	private void displayLives(Graphics2D g2d) {
		rect = new Rectangle(50, 5, 100, 40);
		g2d.setColor(Color.WHITE);
		g2d.fill(rect);
		g2d.draw(rect);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 10));
		g2d.drawString("Lives: ", 55, 30);
		String out = "" + player.getLives();
		g2d.drawString(out, 90, 30);
	}

	private void displayScore(Graphics2D g2d) {
		rect = new Rectangle(100, 5, 70, 40);
		g2d.setColor(Color.WHITE);
		g2d.fill(rect);
		g2d.draw(rect);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, 10));
		g2d.drawString("Score: ", 105, 30);
		String out = "" + player.getScore();
		g2d.drawString(out, 140, 30);
	}

	private void gameEnd() {
		if (player.getLives() == 0) {
			level = 1;
			loadLevel(0);
			player.setLives(0);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(60,60,60));
        g2.fillRect(0, 0, 600, 600);   // filled rectangle
		for (Trap trap : traps) {
			trap.draw(g2);
		}
		player.draw(g2);
		if (gems.size() != 0) {
			for (Gem gem : gems) {
				if (gem.isCollected() == false) {
					gem.draw(g2);
				} else if (!gem.isScored()) {
					player.setScore(player.getScore() + 1);
					gem.Scored();
				}
			}
		}
		for (Enemy enemy : enemies) {
			enemy.draw(g2);
		}
		for (TempWall tempWall : tempwalls) {
			tempWall.draw(g2);
		}
		for (Wall wall : walls) {
			wall.draw(g2);
		}

		this.displayLives(g2);
		this.displayScore(g2);
	}
}
