package typespeed;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Inputline extends GameObject implements KeyListener {
    private static final Font TEXTFONT = new Font("Arial", Font.PLAIN, 10);

    private Handler handler;
    private String input;

    protected Inputline(Handler handler) {
        super(ObjectID.Inputline);
        this.handler = handler;

        input = "";
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

        g.setFont(TEXTFONT);
        g.setColor(Color.GRAY);

        g.fillRect(0, (int) (handler.getGame().getBounds().getHeight() - 30), (int) handler.getGame().getBounds().getWidth(), 30);

        g.setColor(Color.BLACK);
        g.drawString(input, 0, (int) (handler.getGame().getBounds().getHeight() - 10));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if( e.isAltDown() && e.getKeyCode() == KeyEvent.VK_F4){
            System.out.println("Program will be quit");
            System.exit(0);
        }

        if( e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE ){
            input = "";
        } else if ( e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE ){
            if(input.length() > 0)
                input = input.substring(0, input.length()-1);
        } else {
            if ( e.getKeyChar() != KeyEvent.CHAR_UNDEFINED && !e.isActionKey() ) {
                input += e.getKeyChar();
            }
        }
        e.consume();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
