package typespeed;

import java.awt.*;

public class FPSScreen extends GameObject{

    private static final Font FPSFONT = new Font("Arial", Font.PLAIN, 10);
    private int fps;

    public FPSScreen(){
        super(ObjectID.FPSscreen);

        fps = 0;
        x = 5;
        y = 15;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(FPSFONT);

        g.drawString("FPS: " + this.fps, (int)x, (int)y);
    }

    public void setFps(int frames) {
        fps = frames;
    }
}
