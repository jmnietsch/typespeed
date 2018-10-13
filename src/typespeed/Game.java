package typespeed;

import javax.swing.*;
import java.awt.*;



public class Game implements Runnable{
	public static final String GAME_NAME = "Typespeed";
	private static final int width = 500;
	private static final int height = 300;

	public static void main(String[] args){
		Game game = new Game();

		game.run();
	}

	private Game() {

		JFrame frame = new JFrame(GAME_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(width, height));

		JLabel emptyLabel = new JLabel();
		frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);

		frame.pack();
		frame.setVisible(true);

		frame.setLocation(400,500);

	}

	public void run() {
		System.out.println("Running");
	}
}
