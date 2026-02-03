package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.Timer;

import model.Square;
import model.Enemy;
import model.GameModel;
import model.Player;

public class GameComponent extends JComponent {

	private Player player = new Player(200, 200, 80, 80);
	private Timer timer;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	private Enemy enemy = new Enemy(100, 100, 80, 80);

	private boolean W;
	private boolean A;
	private boolean S;
	private boolean D;

	private GameModel model;

	public GameComponent(GameModel model) {
		this.model = model;

		timer = new Timer(20, e -> {
			playerKeys();
			player.update(WIDTH, HEIGHT);
			enemy.update(WIDTH, HEIGHT);
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
			player.SetSpeed(player.GetSpeed()[0], 10);
		} else if (W) {
			player.SetSpeed(player.GetSpeed()[0], -10);
		} else {
			player.SetSpeed(player.GetSpeed()[0], 0);
		}
		if (D) {
			player.SetSpeed(10, player.GetSpeed()[1]);
		} else if (A) {
			player.SetSpeed(-10, player.GetSpeed()[1]);
		} else {
			player.SetSpeed(0, player.GetSpeed()[1]);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		player.draw(g2);
		enemy.draw(g2);

		// Minimal placeholder to test it
		g2.drawString("Final Project Starter: UI is running ✅", 20, 30);

		// TODO: draw based on model state

	}
}
