package typespeed;

import java.awt.*;
import java.util.ArrayList;

public class Handler {
	ArrayList<GameObject> gameObjects = new ArrayList<>();

	public synchronized void tick(){
		for(GameObject game : gameObjects){
			game.tick();
		}
	}

	public synchronized void render(Graphics g){
		ArrayList<GameObject> removeObjects = new ArrayList<>();

		for(GameObject game : gameObjects){
			if(game instanceof Tile){
				Tile t = (Tile) game;

				if(t.hasReachedEnd()) {
					removeObjects.add(t);
					continue;
				}
			}

			game.render(g);
		}

		for(GameObject game : removeObjects){
			removeObject(game);
		}
	}

	public synchronized void addObject(GameObject gameObject){
		gameObjects.add(gameObject);
	}

	public synchronized void removeObject(GameObject gameObject){
		gameObjects.remove(gameObject);
	}

	public int getObjectCount(){
		return gameObjects.size();
	}
}
