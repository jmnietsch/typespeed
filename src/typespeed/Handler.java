package typespeed;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Handler {
    private ArrayList<GameObject> gameObjects = new ArrayList<>();
	private Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public synchronized void tick(){
		for(GameObject game : gameObjects){
			game.tick();
		}

		ArrayList<GameObject> completedObjects = new ArrayList<>();
		ArrayList<GameObject> removeObjects = new ArrayList<>();
		for(GameObject game : gameObjects){
			if(game instanceof Tile){
				Tile t = (Tile) game;

				if(t.isSolved()){
					completedObjects.add(t);
					continue;
				}

				if(t.hasReachedEnd()) {
					removeObjects.add(t);
					continue;
				}
			}


		}

		cleanup(completedObjects);
		cleanup(removeObjects);

	}

	public synchronized void render(Graphics g){

		for(GameObject game : gameObjects){
			game.render(g);
		}
    }

	private void cleanup(ArrayList<GameObject> clearObjects) {
		for(GameObject game : clearObjects){
			removeObject(game);
		}
	}

	public synchronized void addObject(GameObject gameObject){
		gameObjects.add(gameObject);
	}

	public synchronized void removeObject(GameObject gameObject){
    	if(gameObjects.contains(gameObject))
			gameObjects.remove(gameObject);
	}

	public synchronized int getObjectCount(){
		return gameObjects.size();
	}

	public synchronized List<Tile> getTiles(){
		return gameObjects
				.stream().filter(Tile.class::isInstance)
				.map(Tile.class::cast)
				.collect(Collectors.toList());
	}

    public synchronized int getTileCount(){
        return getTiles().size();
    }

    public Game getGame() {
        return game;
    }
}
