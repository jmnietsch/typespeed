package typespeed;

import javax.swing.*;
import java.awt.*;

class Tile extends JLabel {

	Tile(String text) {
		this(text, 20);
	}

	public Tile(String text, int posY) {
		setText(text);
		setVisible(true);

		this.setBounds(0,0,getText().length()*10,30);
		this.setHorizontalTextPosition(CENTER);
		this.setVerticalTextPosition(CENTER);
		this.setHorizontalTextPosition(CENTER);

		this.setBackground(new Color(0));
		this.setBorder(BorderFactory.createLineBorder(new Color(0),2,false));

		this.setLocation(20, posY);

		System.out.println("Created Tile " + text);
	}

	@Override
	public String toString() {
		return "Tile{" +
				"labelFor=" + labelFor +
				", ui=" + ui +
				", listenerList=" + listenerList +
				", accessibleContext=" + accessibleContext +
				'}';
	}
}
