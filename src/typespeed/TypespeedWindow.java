package typespeed;

import typespeed.Game.Inputline;
import typespeed.Game.Tile;
import typespeed.Game.TypespeedGame;
import typespeed.Menu.TypespeedMenu;

import javax.swing.*;
import java.awt.*;

public class TypespeedWindow extends JFrame {

	private static final long serialVersionUID = 966905019220131354L;
	private TypespeedGame game;

	public static final int WIDTH = 500;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final String GAME_NAME = "Typespeed";

	private static TypespeedWindow window =  new TypespeedWindow(GAME_NAME);
	private TypespeedMenu menu;

	public static TypespeedWindow getWindow() {
		return window;
	}

	public static void main(String[] args){
		TypespeedMenu menu = new TypespeedMenu();
		setMenu(menu);
	}

	public TypespeedWindow(String title){
		this(TypespeedWindow.WIDTH, TypespeedWindow.HEIGHT, title);
	}

	public TypespeedWindow(int width, int height, String title){
		super(title);

		this.setSize(new Dimension(width,height));
		this.setMinimumSize(new Dimension(width,height));
		this.setMaximumSize(new Dimension(width,height));
		this.setResizable(false);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setLocation(400,200);

		this.setVisible(true);
	}

	public static void clear(){
		if(window.game != null)
			window.remove(window.game);

		if(window.menu != null)
			window.remove(window.menu);

		window.game = null;
		window.menu = null;
	}

	public static void addMenu(TypespeedMenu menu){
		window.getContentPane().add(menu);
		window.getContentPane().setVisible(true);
		window.setVisible(true);

		window.add(menu);
		window.addMouseListener(menu);
		menu.start();
	}

	public static void setMenu(TypespeedMenu menu){
		clear();

		window.menu = menu;
		addMenu(menu);
	}

	public static void addGame(TypespeedGame game){
		window.getContentPane().add(game);
		window.getContentPane().setVisible(true);
		window.setVisible(true);

		game.start();
	}

	public static void setGame(TypespeedGame game){
		clear();

		window.game = game;
		addGame(game);
	}

	public static int getScreenheight(){
		return window.getComponent(0).getBounds().height - Inputline.LINEHEIGHT - Tile.TILEHEIGHT;
		//Fixme: getComponent(0) is not nice. Do something else. eg save Canvas of game instance
	}
}
