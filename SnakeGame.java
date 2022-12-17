
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

class GameFrame extends JFrame {

	GameFrame() {

		this.add(new GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

	}
}

class GamePanel extends JPanel implements ActionListener {

	static int width = 1300;
	static int height = 750;
	static int units = 50;
	static int gameUnit = (width * height) / (units * units);
	static int delay = 175;
	final int x[] = new int[gameUnit];
	final int y[] = new int[gameUnit];
	int bodyParts = 6;
	int count;
	int foodX;
	int foodY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;

	GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		food();
		running = true;
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {

		if (running) {
			g.setColor(Color.red);
			g.fillRect(foodX, foodY, units, units);

			for (int i = 0; i < bodyParts; i++) {
				if (i % 2 == 0) {
					g.setColor(Color.yellow);
					g.fillRect(x[i], y[i], units, units);
				} else {
					g.setColor(Color.orange);
					// g.setColor(new
					// Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					g.fillRect(x[i], y[i], units, units);
				}
			}
			g.setColor(Color.red);
		} else {
			gameOver(g);
		}

	}

	public void food() {
		foodX = random.nextInt((int) (width / units)) * units;
		foodY = random.nextInt((int) (height / units)) * units;
	}

	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		switch (direction) {
			case 'U':
				y[0] = y[0] - units;
				break;
			case 'D':
				y[0] = y[0] + units;
				break;
			case 'L':
				x[0] = x[0] - units;
				break;
			case 'R':
				x[0] = x[0] + units;
				break;
		}

	}

	public void checkApple() {
		if ((x[0] == foodX) && (y[0] == foodY)) {
			bodyParts++;
			count++;
			food();
		}
	}

	public void checkCollisions() {
		// check if head touches left border
		if (x[0] < 0) {
			running = false;
		}
		// check if head touches right border
		if (x[0] > width) {
			running = false;
		}
		// check if head touches top border
		if (y[0] < 0) {
			running = false;
		}
		// check if head touches bottom border
		if (y[0] > height) {
			running = false;
		}

		if (!running) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g) {
		JOptionPane.showMessageDialog(null, "Your Score: " + count, "Game Over", JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case 37:
					if (direction != 'R') {
						direction = 'L';
					}
					break;
				case 39:
					if (direction != 'L') {
						direction = 'R';
					}
					break;
				case 38:
					if (direction != 'D') {
						direction = 'U';
					}
					break;
				case 40:
					if (direction != 'U') {
						direction = 'D';
					}
					break;
			}
		}
	}
}

public class SnakeGame {

	public static void main(String[] args) {

		new GameFrame();
	}
}
