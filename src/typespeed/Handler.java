package typespeed;

import java.awt.*;
import java.util.ArrayList;

public class Handler {
	ArrayList<GameObject> gameObjects = new ArrayList<>();

	public void tick(){
		for(GameObject game : gameObjects){
			game.tick();
		}
	}

	public void render(Graphics g){
		for(GameObject game : gameObjects){
			game.render(g);
		}
	}

	public void addObject(GameObject gameObject){
		gameObjects.add(gameObject);
	}

	public void removeObject(GameObject gameObject){
		gameObjects.add(gameObject);
	}
}
