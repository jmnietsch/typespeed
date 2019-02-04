package typespeed.Menu;

import typespeed.Game.TypespeedGame;
import typespeed.TypespeedWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class TypespeedMenu extends Container implements Runnable, MouseListener {
    private static final long serialVersionUID = 4530308932580747353L;

    private ArrayList<TypespeedMenuItem> itemList;

    private Thread thread;
    private static boolean running = false;

    public TypespeedMenu() {
        this.setBackground(Color.CYAN);
        this.setSize(TypespeedWindow.getWindow().getSize());
        this.setLocation(TypespeedWindow.getWindow().getLocation());
        this.setBounds(TypespeedWindow.getWindow().getBounds());

        itemList = new ArrayList<>();

        int tileWidth = 200;

        TypespeedMenuItem item = addItem(50, tileWidth, "Test");
        TypespeedMenuItem item2 = addItem( 130, tileWidth, "Testasdasd");

        item.addMouseListener(this);
        item2.addMouseListener(this);
//        this.addMouseListener(this);
    }

    private TypespeedMenuItem addItem(int y, int tileWidth, String itemText) {
        int centeredX = this.getWidth() / 2;
        TypespeedMenuItem item = new TypespeedMenuItem(new Rectangle(centeredX - (tileWidth/2), y, tileWidth, TypespeedMenuItem.ITEMHEIGHT), itemText);
        itemList.add(item);
        this.add(item);
        return item;
    }

    private void startNewGame(){
        TypespeedGame game = new TypespeedGame();
        TypespeedWindow.setGame(game);
    }

    //----------------------- MouseListener -----------------------------//

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object source = e.getSource();
        if( source instanceof TypespeedMenuItem){
            ((TypespeedMenuItem) source).setColor(Color.GREEN);
            ((TypespeedMenuItem) source).repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object source = e.getSource();
        if( source instanceof TypespeedMenuItem){
            ((TypespeedMenuItem) source).setColor(Color.RED);
            ((TypespeedMenuItem) source).repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        saySomething("Mouse clicked (# of clicks: "
                + e.getClickCount() + ")", e);
    }

    private void saySomething(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription + " detected on "
                + e.getComponent().getClass().getName());
    }

    //----------------------- RUNNABLE -----------------------------//

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                delta--;
            }
        }
        stop();
    }

    public synchronized void start() {
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
