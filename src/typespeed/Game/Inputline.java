package typespeed.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Inputline extends GameObject implements KeyListener {
    private static final Font TEXTFONT = new Font("Arial", Font.PLAIN, 10);
    public static final int LINEHEIGHT = 30;

    private Handler handler;
    private String input;

    private boolean closeInput = false;

    protected Inputline(Handler handler) {
        super(ObjectID.Inputline);
        this.handler = handler;

        clearInput();
    }

    public void addToInput(char inputChar) {
        input += inputChar;
    }

    public void removeLastInput() {
        input = input.substring(0, input.length()-1);
    }

    public void clearInput(){
        input = "";
        closeInput = false;
    }

    public void close(){
        closeInput = true;
    }

    public boolean isClosed(){
        return closeInput;
    }

    public boolean isOpen(){
        return !isClosed();
    }

    @Override
    public void tick() {
        if(!closeInput) return;

        ArrayList<Tile> foundTiles = new ArrayList<>();

        for(Tile tile : handler.getTiles()){
            if(tile.getText().contains(input)){
                System.out.println("This Tile " + tile.getText() + " somewhat matches the Input " + input);

                foundTiles.add(tile);
            }
        }

        for(Tile tile : foundTiles){
            if(tile.getText().contentEquals(input)){
                tile.markAsSolved();
            }
        }

        clearInput();
    }

    @Override
    public void render(Graphics g) {

        g.setFont(TEXTFONT);
        g.setColor(Color.GRAY);

        g.fillRect(0, (int) (handler.getGame().getBounds().getHeight() - LINEHEIGHT), (int) handler.getGame().getBounds().getWidth(), LINEHEIGHT);

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

        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            this.close();
        }

        if(this.isOpen()){
            if( e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE ){
                clearInput();
            }


            if ( e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE ){
                //Delete latest saved Key input.
                if(input.length() > 0)
                    removeLastInput();

            } else {
                //Only add usable Chars to the Input
                if ( e.getKeyChar() != KeyEvent.CHAR_UNDEFINED && !e.isActionKey() ) {
                    addToInput(e.getKeyChar());
                }
            }
        }
        e.consume();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
