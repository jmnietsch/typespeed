package typespeed;

import java.awt.*;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable{
	public static final String GAME_NAME = "Typespeed";
	public static final int WIDTH = 500;
	public static final int HEIGHT = WIDTH / 12 * 9;

	private static final long serialVersionUID = -333377613080388191L;

	private Thread thread;
	private static boolean running = false;

	private Handler handler;

	private Game() {
		handler = new Handler(this);


        handler.addObject(new UIStats());

        Inputline inputline = new Inputline(handler);
        handler.addObject(inputline);
        this.addKeyListener(inputline);

		new Window(WIDTH, HEIGHT, GAME_NAME, this);

		handler.addObject(new UICounter());

		long startTime = System.currentTimeMillis();
		requestFocus();

		while(running){
			long curTime = System.currentTimeMillis();
			long bigtick = 1 + (curTime - startTime) / 1000;
		}
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		double bigDelta = 500;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			bigDelta += delta;
			lastTime = now;

			while (delta >= 1) {
				tick();
				delta--;
			}

			if (bigDelta >= 500){
				bigtick();
				bigDelta -= 500;
			}

			if (running)
				render();

		}
		stop();
	}

	private void tick() {
		handler.tick();
	}

	private void bigtick() {
		int objectCount = handler.getTileCount();
		int maxObjects = 5;

		if(objectCount < maxObjects){
			addNewTile(DictionaryService.getRandomString());
		}

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		//Define Background over full image Plane
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.green);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		//Call Handler to render all gameObjects
		handler.render(g);

		g.dispose();
		bs.show();
	}

	private void addNewTile(String name){
		int validrange = getBounds().height - Tile.TILEHEIGHT - Inputline.LINEHEIGHT;

		Tile t = new Tile(name, (int) ( Math.random() * validrange ));
		handler.addObject(t);
	}

	synchronized void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		Game g = new Game();
	}

	public Handler getHandler() {
		return handler;
	}

}
