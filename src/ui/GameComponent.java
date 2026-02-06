package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.Timer;

import model.Square;
import model.Wall;
import model.Enemy;
import model.GameModel;
import model.Player;

public class GameComponent extends JComponent {

	private Player player;
	private Timer timer;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final int TILE_SIZE = 50;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private ArrayList<Wall> walls = new ArrayList<>();

	private boolean W;
	private boolean A;
	private boolean S;
	private boolean D;

	private GameModel model;

	public GameComponent(GameModel model) {
		this.model = model;

		// Reading from .txt file
		loadLevel(1);

		//Setting up timer
		timer = new Timer(20, e -> {
			playerKeys();
			player.update(WIDTH, HEIGHT);
			for(Enemy enemy : enemies) {
				enemy.update(WIDTH, HEIGHT);
			}			
			repaint();
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
	
	private void loadLevel(int n) {
		int row = 0;
		try {
			Scanner scanner = new Scanner(new File("level" + n + ".txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				for (int col = 0; col < line.length(); col++) {
					char c = line.charAt(col);

					if (c == 'P') {
						player = new Player(col*TILE_SIZE + 5, row*TILE_SIZE + 5, 40, 40);
						int[] spawn = {col*TILE_SIZE + 5,row*TILE_SIZE + 5};
						player.setSpawn(spawn);
					} else if(c == 'E') {
						Enemy enemy = new Enemy(col*TILE_SIZE + 5, row*TILE_SIZE + 5, 40, 40);
						enemies.add(enemy);
					} else if(c == 'W') {
						Wall wall = new Wall(col*TILE_SIZE, row*TILE_SIZE, TILE_SIZE);
						walls.add(wall);
					}
				}

				row++;
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("level1.txt not found");
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		player.draw(g2);
		for(Enemy enemy : enemies) {
			enemy.draw(g2);
		}
		for(Wall wall : walls) {
			wall.draw(g2);
		}
	}
}
