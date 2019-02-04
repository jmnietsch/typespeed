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

        addItem(50, tileWidth, TypespeedMenuItem.ItemType.NEWGAME);
        addItem( 130, tileWidth, TypespeedMenuItem.ItemType.OPTIONS);
    }

    private void addItem(int y, int tileWidth, TypespeedMenuItem.ItemType type) {
        int centeredX = this.getWidth() / 2;

        TypespeedMenuItem item;

        switch (type){
            default:
            case DEFAULT:
            case NEWGAME:
                item = new NewGameMenuItem(this, new Rectangle(centeredX - (tileWidth/2), y, tileWidth, TypespeedMenuItem.ITEMHEIGHT));
                break;
//            case OPTIONS:
//                break;
        }

        itemList.add(item);
        this.add(item);

        item.addMouseListener(this);

    }

    public void startNewGame(){
        TypespeedGame game = new TypespeedGame();
        TypespeedWindow.setGame(game);
    }

    //----------------------- MouseListener -----------------------------//

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        saySomething("Mouse clicked (# of clicks: "
                + e.getClickCount() + ")", e);

        if( e.getSource() instanceof TypespeedMenuItem){
            TypespeedMenuItem source = (TypespeedMenuItem) e.getSource();
            source.onAction();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if( e.getSource() instanceof TypespeedMenuItem){
            TypespeedMenuItem source = (TypespeedMenuItem) e.getSource();
            source.onMouseEntered();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if( e.getSource() instanceof TypespeedMenuItem){
            TypespeedMenuItem source = (TypespeedMenuItem) e.getSource();
            source.onMouseExited();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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
