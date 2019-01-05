package typespeed;

import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;


public class Game extends Canvas implements Runnable{
	public static final String GAME_NAME = "Typespeed";
	public static final int WIDTH = 500;
	public static final int HEIGHT = WIDTH / 12 * 9;

	private static final long serialVersionUID = -333377613080388191L;

	private Thread thread;
	private static boolean running = false;

	private Handler handler;

	private Game() {
		handler = new Handler();

		new Window(WIDTH, HEIGHT, GAME_NAME, this);
		requestFocus();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1) {
				tick();
				delta--;
			}

			if (running)
				render();

			frames++;

			long curTime = System.currentTimeMillis();
			if (curTime - timer > 1000) {
				timer = curTime - (curTime % 1000);
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}

	private void tick() {
		handler.tick();
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
		int validrange = getBounds().height - Tile.TILEHEIGHT;

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

		Random r = new Random(0);
		long startTime = System.currentTimeMillis();
		while(running){
			long curTime = System.currentTimeMillis();
			int objectCount = g.getHandler().getObjectCount();

			long timedelta = (curTime - startTime) / 1000;

			if(objectCount < 25 && timedelta > objectCount)
				g.addNewTile(RandomStringUtils.randomAlphabetic(r.nextInt(6)+1));
		}

	}

	public Handler getHandler() {
		return handler;
	}


}
