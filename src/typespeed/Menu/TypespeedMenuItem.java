package typespeed.Menu;

import java.awt.*;

public abstract class TypespeedMenuItem extends Canvas {

    public enum ItemType {
        DEFAULT,
        NEWGAME,
        OPTIONS
    }

    protected String text;
    protected final ItemType menuType;
    protected final TypespeedMenu menu;

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

    public TypespeedMenuItem(TypespeedMenu typespeedMenu, Rectangle area, String text) {
        this(typespeedMenu, area, text, ItemType.DEFAULT);
    }

    public TypespeedMenuItem(TypespeedMenu typespeedMenu, Rectangle area, String text, ItemType menuType) {
        this.text = text;
        this.menuType = menuType;
        this.menu = typespeedMenu;

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
        int centeredHeight = this.getHeight() + getFont().getSize();

        g.drawString(text, getCenteredTextpos(), centeredHeight / 2);
    }

    private int getCenteredTextpos(){
        FontMetrics fm = this.getFontMetrics(this.getFont());

        int textWidth = fm.stringWidth(text);
        int pos = (this.getWidth() - textWidth) / 2;

        return pos;
    }

    //----------------------- Hover Action -----------------------------//

    public void onMouseEntered(){
        this.setColor(Color.GREEN);
        this.repaint();
    }

    public void onMouseExited(){
        this.setColor(Color.RED);
        this.repaint();
    }

    public abstract void onAction();
}


class NewGameMenuItem extends TypespeedMenuItem {
    public NewGameMenuItem(TypespeedMenu typespeedMenu, Rectangle area) {
        super(typespeedMenu, area, "New Game", ItemType.NEWGAME);
    }

    @Override
    public void onAction() {
        this.menu.startNewGame();
    }
}