package typespeed.Menu;

import java.awt.*;

public class TypespeedMenuItem extends Canvas {

    private String text;

    private Color color = Color.RED;
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public static final Font MENUFONT = new Font("Arial", Font.PLAIN,12);
    public static final int TEXTPOS = 24;
    public static final int ITEMHEIGHT = 50;
    private static Color TEXTCOLOR = Color.BLACK;

    public TypespeedMenuItem(Rectangle area, String text) {
        this.text = text;

        this.setBounds(area);
        this.setSize(area.getSize());
        this.setLocation(area.getLocation());
        this.setMinimumSize(getSize());
        this.setMaximumSize(getSize());
        this.setBackground(color);

        this.setFont(MENUFONT);
    }

    @Override
    public void paint(Graphics g){
        g.setFont(this.getFont());
        this.setBackground(color);

        //Now draw the String, with a leading space
        g.setColor(TEXTCOLOR);

        FontMetrics fm = this.getFontMetrics(this.getFont());
        int centeredHeight = this.getHeight() + getFont().getSize();


        g.drawString(text, getCenteredTextpos(), centeredHeight / 2);
    }

    private int getCenteredTextpos(){
        FontMetrics fm = this.getFontMetrics(this.getFont());

        int textwith = fm.stringWidth(text);
        int pos = (this.getWidth() - textwith) / 2;

        return pos;
    }

}
