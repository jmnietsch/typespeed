package typespeed;

import javax.swing.*;
import java.awt.*;

class Window extends JFrame {

	private static final long serialVersionUID = 966905019220131354L;
	private Game game;

	static final int WIDTH = 500;
	static final int HEIGHT = WIDTH / 12 * 9;

	Window(String title){
		this(Window.WIDTH, Window.HEIGHT, title);
	}

	Window(int width, int height, String title){
		super(title);

		this.setSize(new Dimension(width,height));
		this.setMinimumSize(new Dimension(width,height));
		this.setMaximumSize(new Dimension(width,height));
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLocation(400,200);

		this.setVisible(true);
	}

	void clear(){
		if(this.game != null)
			remove(this.game);

		this.game = null;
	}

	void addGame(Game game){
		getContentPane().add(game);
		getContentPane().setVisible(true);
		this.setVisible(true);

		game.start();
	}

	void setGame(Game game){
		clear();

		this.game = game;
		addGame(game);
	}
}
