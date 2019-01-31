package typespeed;

import java.awt.*;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -333377613080388191L;

	private Thread thread;
	private static boolean running = false;

    private Rangefinder rangefinder;
	private UIStats uistats;
	private Handler handler;

	Game() {
		handler = new Handler(this);

		rangefinder = new Rangefinder();
		handler.addObject(rangefinder);

		uistats = new UIStats();
		handler.addObject(uistats);

		Inputline inputline = new Inputline(handler);
		handler.addObject(inputline);
		this.addKeyListener(inputline);

		handler.addObject(new UICounter());

		this.setVisible(true);
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

			if (bigDelta >= 1000){
				bigtick();
				bigDelta -= 1000;
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
		int maxObjects = 30;

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

		g.fillRect(0, 0, Window.WIDTH, Window.HEIGHT);

		//Call Handler to render all gameObjects
		handler.render(g);

		g.dispose();
		bs.show();
	}

	private void addNewTile(String name){

		int validRange = Menu.getScreenheight();

		Tile t = new Tile(name, (int) ( rangefinder.getNextPosition() * validRange ));
		handler.addObject(t);
	}

	synchronized void start() {
		thread = new Thread(this);
		running = true;
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

	public Handler getHandler() {
		return handler;
	}

}
