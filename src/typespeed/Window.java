package typespeed;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

	private static final long serialVersionUID = 966905019220131354L;

	public Window(int width, int height, String title, Game game){
		JFrame frame = new JFrame(title);

		frame.setSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height));
		frame.setMaximumSize(new Dimension(width,height));
		frame.setResizable(false);

		frame.setLocation(400,200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);

		frame.setVisible(true);
		game.start();
	}

}
