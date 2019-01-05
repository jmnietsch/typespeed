package typespeed;

import java.awt.*;

public abstract class GameObject {
	protected float x;
	protected float y;
	protected float speedX;
	protected float speedY;
    protected final ObjectID id;

    protected GameObject(ObjectID id) {
        this.id = id;
    }

    public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getSpeedX() {
		return speedX;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public ObjectID getId() {
		return id;
	}

	public abstract void tick();
	public abstract void render(Graphics g);

}
