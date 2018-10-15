package typespeed;

import org.apache.commons.lang3.RandomStringUtils;

import java.awt.*;
import java.awt.image.BufferStrategy;


public class Game extends Canvas implements Runnable{
	public static final String GAME_NAME = "Typespeed";
	private static final int WIDTH = 500;
	private static final int HEIGHT = WIDTH / 12 * 9;;
	private static final long serialVersionUID = -333377613080388191L;

	private Thread thread;
	private boolean running = false;
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

			if (System.currentTimeMillis() - timer > 1000) {
				timer = +1000;
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
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.green);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);

		g.dispose();
		bs.show();
	}

	private void addNewTile(String name){
		Tile t = new Tile(name, (int) (Math.random() * (HEIGHT-30)));
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

		g.addNewTile(RandomStringUtils.randomAlphabetic(5));
	}

}
