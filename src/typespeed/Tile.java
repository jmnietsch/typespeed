package typespeed;

import java.awt.*;

class Tile extends GameObject {
	public static final int TILEHEIGHT = 30;
	public static final int TEXTPOS = (int) (TILEHEIGHT * 0.6);
	public static final Font TILEFONT = new Font("Arial", Font.PLAIN,12);;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	String text;
	int length = 0;

	private final int tileId;
	private static volatile int counter = 0;

	public Tile(String text, int posY) {
		setText(text);

		Canvas c = new Canvas();
		FontMetrics fm = c.getFontMetrics(TILEFONT);

		//Calculate display width of text, padded by a space front and back
		length = fm.stringWidth(" " + text + " ");

		//Set Id Value. Update counter
		tileId = counter++;

		speedX = 1.0f;
		y = posY;

		//Snap to Lines, defined by TILEHEIGHT
		//y = y - (y % TILEHEIGHT);

		//y = Math.min(y, Game.gameInstance.getBounds().height - Tile.TILEHEIGHT);

		System.out.println("Create " + toString() + " at posY " + posY);
	}

	@Override
	public void tick() {
		x += speedX;

		if(hasReachedEnd()){
			System.out.println("Destr  " + toString());
		}
	}

	@Override
	public void render(Graphics g) {
		g.setFont(TILEFONT);

		if( x+length >= Game.WIDTH)
			length -= ((length - 1) % Game.WIDTH);

		g.setColor(Color.magenta);
		g.fillRect((int)x, (int)y, length, TILEHEIGHT);

		g.setColor(Color.BLACK);

		//Now draw the String, with a leading space
		g.drawString(" " + text, (int)x, (int)y + TEXTPOS);
	}

	boolean hasReachedEnd(){
		return (length > 0 && x + length >= Game.WIDTH);
	}

	@Override
	public String toString() {
		return "Tile{" +
				"[" + tileId + "]" +
				"[x:" + (int)x + "|y:" + (int)y + "]" +
				"(" + length + ") -> " +
				"'" + text + '\'' +
				"}";
	}

	public int getTileId() {
		return tileId;
	}
}
