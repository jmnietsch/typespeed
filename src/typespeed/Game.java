package typespeed;

import javax.swing.*;
import java.awt.*;
import org.apache.commons.lang3.RandomStringUtils;


public class Game implements Runnable{
	public static final String GAME_NAME = "Typespeed";
	private static final int width = 500;
	private static final int height = 300;
	private final JFrame frame;

	public static void main(String[] args){
		Game game = new Game();

		game.run();
	}

	private Game() {

		frame = new JFrame(GAME_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(width, height));

		frame.pack();
		frame.setVisible(true);

		frame.setLocation(400,500);

		frame.getContentPane().setLayout(null);
	}

	public void run() {
		System.out.println("Running");

		int numTiles = 5;

		for(int i = 0; i<numTiles; ++i){
			int randomWordLength = (int) (Math.random() * 15);
			addNewTile(RandomStringUtils.randomAlphabetic(randomWordLength));
		}
	}

	private void addNewTile(String name){
		Tile t = new Tile(name, (int) (Math.random() * (height-30)));
		frame.add(t);
	}
}
