package typespeed;

import java.awt.*;

public class UIStats extends GameObject{

    private static final Font FPSFONT = new Font("Arial", Font.PLAIN, 10);

    private int fps;
    private long ticks;
    private int framesTmp;
    long timer = System.currentTimeMillis();

    public UIStats(){
        super(ObjectID.UIStats);

        fps = 0;
        ticks = 0;

        x = 5;
        y = 15;

        framesTmp = 0;
    }

    @Override
    public void tick() {
        ticks++;
    }

    @Override
    public void render(Graphics g) {
        framesTmp++;

        long curTime = System.currentTimeMillis();
        if (curTime - timer > 1000) {
            timer = curTime - (curTime % 1000);

            fps = framesTmp;
            framesTmp = 0;
        }

        g.setColor(Color.BLACK);
        g.setFont(FPSFONT);

        //Generating Text Output Scheme
        String buffer =
                "Fps:" + '\t' + this.fps + "\n" +
                "Ticks:"+ '\t' + this.ticks + "\n";

        //Draw Text separated in Lines and Tab-Parts
        String[] lines = buffer.split("\n");

        int lineNr = 0;
        for (String line : lines){
            String[] parts = line.split("\t");

            int partNr = 0;
            for(String part : parts){
                g.drawString(part, (int)x + partNr * 30, (int)y + lineNr * FPSFONT.getSize());
                partNr++;
            }

            lineNr++;
        }
    }
}
