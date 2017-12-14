package asteroids;

import javax.swing.JFrame;

public class Game extends JFrame {

	public static void main(String[] args) {		
		Game game = new Game();
		game.setVisible(true);
                game.setDefaultCloseOperation(EXIT_ON_CLOSE);
                game.setResizable(false);
		game.startGame();
	}
	
	public Game() {
		this.addKeyListener(new KeyboardListener());
		Space space = Space.getInstance();
		add(space);
		setSize(space.getFrameWidth(), space.getFrameHeight());
		setTitle("Asteroids!");
	}
	
	public void startGame() {
		Thread t = new Thread(Space.getInstance());
		t.start();
	}
	
	
}
