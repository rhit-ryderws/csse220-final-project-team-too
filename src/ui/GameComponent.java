package ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.Timer;

import model.Square;
import model.GameModel;
import model.Player;

public class GameComponent extends JComponent {
	

	private Player player = new Player(200,200,80,80);
	private Timer timer;
	public static final int WIDTH = 500;
	public static final int HEIGHT = 200;
	
	private GameModel model;

	public GameComponent(GameModel model) {
		this.model = model;
		
		timer = new Timer(20, e -> {
			player.update(WIDTH, HEIGHT);
			repaint();
			});
			timer.start();
		
		addKeyListener(new KeyAdapter() {
			  @Override
			  public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode() == KeyEvent.VK_A) {
			      player.SetSpeed(-25,0);
			    }
			  }
			});
			
			addKeyListener(new KeyAdapter() {
				  @Override
				  public void keyPressed(KeyEvent e) {
				    if (e.getKeyCode() == KeyEvent.VK_D) {
				      player.SetSpeed(25,0);
				    }
				  }
				});
			
			addKeyListener(new KeyAdapter() {
				  @Override
				  public void keyPressed(KeyEvent e) {
				    if (e.getKeyCode() == KeyEvent.VK_W) {
				      player.SetSpeed(0, 25);;
				    }
				  }
				});
				
				addKeyListener(new KeyAdapter() {
					  @Override
					  public void keyPressed(KeyEvent e) {
					    if (e.getKeyCode() == KeyEvent.VK_S) {
					      player.SetSpeed(0,-25);
					    }
					  }
					});
	}

	@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;
	player.draw(g2);

	// Minimal placeholder to test  it
	g2.drawString("Final Project Starter: UI is running ✅", 20, 30);


	// TODO: draw based on model state
	
	}
}
