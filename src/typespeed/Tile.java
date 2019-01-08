package typespeed;

import java.awt.*;

class Tile extends GameObject {
	//If True: Display gaussian curves used for cost calculation for next position.
	private static final boolean DEBUG_VISUALIZATION = false;

	public static final int TILEHEIGHT = 30;
	public static final int TEXTPOS = (int) (TILEHEIGHT * 0.6);
	public static final Font TILEFONT = new Font("Arial", Font.PLAIN,12);
	private boolean solved;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	String text;
	int length;

	private final int tileId;
	private static volatile int counter = 0;

	public Tile(String text, int posY) {
        super(ObjectID.Tile);

        setText(text);
		Canvas c = new Canvas();
		FontMetrics fm = c.getFontMetrics(TILEFONT);

		//Calculate display width of text, padded by a space front and back
		length = fm.stringWidth(" " + text + " ");

		//Set Id Value. Update counter
		tileId = counter++;

		speedX = 0.5f;
		y = posY;

		resetSolved();

//		System.out.println("Create " + toString() + " at posY " + posY);
	}

	@Override
	public void tick() {
		x += speedX;
	}

	@Override
	public void render(Graphics g) {
		if(DEBUG_VISUALIZATION) return;

		g.setFont(TILEFONT);
		g.setColor(Color.magenta);

		g.fillRect(getCurX(), getCurY(), (int)Math.min(length, (Game.WIDTH-x)), TILEHEIGHT);
		g.setColor(Color.BLACK);

		//Now draw the String, with a leading space
		g.drawString(" " + text, getCurX(), getCurY() + TEXTPOS);
	}

	boolean hasReachedEnd(){
		return (length > 0 && x > Game.WIDTH);
	}

    private int getCurX(){
        return Math.round(x);
    }

    private int getCurY(){
        return Math.round(y);
    }

    @Override
	public String toString() {
		return "Tile{" +
				"[" + tileId + "]" +
				"[x:" + getCurX() + "|y:" + getCurY() + "]" +
				"(" + length + ") -> " +
				"'" + text + '\'' +
				"}";
	}

	public int getTileId() {
		return tileId;
	}

	public void markAsSolved() {
		solved = true;
	}

	public void resetSolved() {
		solved = false;
	}

	public boolean isSolved() {
		return solved;
	}
}
