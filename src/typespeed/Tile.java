package typespeed;

import javax.swing.*;
import java.awt.*;

class Tile extends GameObject {
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	String text;

	Tile(String text) {
		this(text, 20);
	}

	public Tile(String text, int posY) {
		setText(text);
		speedX = 1f;

		System.out.println("Created Tile " + text);
	}

	@Override
	public void tick() {
		x += speedX;
	}

	@Override
	public void render(Graphics g) {
		final int estimatedCharSize = 7;

		g.setColor(Color.magenta);
		g.fillRect(x, y, text.length()*estimatedCharSize, 30);

		g.setColor(Color.BLACK);
		g.setFont(Font.getFont("Arial"));
		g.drawString(text, x, y + 18);
	}
}
