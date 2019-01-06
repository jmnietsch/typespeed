package typespeed;

import java.awt.*;

public class UIStats extends GameObject{

    private static final Font FPSFONT = new Font("Arial", Font.PLAIN, 10);
    private int fps;

    private long ticks;

    public UIStats(){
        super(ObjectID.UIStats);

        fps = 0;
        ticks = 0;

        x = 5;
        y = 15;
    }

    @Override
    public void tick() {
        ticks++;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(FPSFONT);

        String stringbuffer = "Fps:" + '\t' + this.fps + "\n" +
                "Ticks:"+ '\t' + this.ticks + "\n";

        String[] lines = stringbuffer.split("\n");

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

    public void setFps(int frames) {
        fps = frames;
    }
}
