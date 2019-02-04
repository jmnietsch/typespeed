package typespeed.Game;

import typespeed.TypespeedWindow;
import typespeed.UI.UICounter;
import typespeed.UI.UIStats;
import typespeed.Utils.DictionaryService;
import typespeed.Utils.GraphicUtils;
import typespeed.Utils.RangefinderService;

import java.awt.*;
import java.awt.image.BufferStrategy;


public class TypespeedGame extends Canvas implements Runnable{

	private static final long serialVersionUID = -333377613080388191L;

	private Thread thread;
	private static boolean running = false;

    private RangefinderService rangefinderService;
	private UIStats uistats;
	private Handler handler;

	public TypespeedGame() {
		handler = new Handler(this);

		rangefinderService = new RangefinderService();
		handler.addObject(rangefinderService);

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

        BufferStrategy bs = GraphicUtils.setupRenderEnvironment(this);
        if (bs == null) return;

		//Define Background over full image Plane
		Graphics g = bs.getDrawGraphics();

        GraphicUtils.setBackground(g, Color.green);

		//Call Handler to render all gameObjects
		handler.render(g);

		g.dispose();
		bs.show();
	}

	private void addNewTile(String name){
		int validRange = TypespeedWindow.getScreenheight();

		Tile t = new Tile(name, (int) ( rangefinderService.getNextPosition() * validRange ));
		handler.addObject(t);
	}

	public synchronized void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
	}

    public synchronized void stop() {
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
