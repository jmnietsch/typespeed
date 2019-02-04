package typespeed.Utils;

import typespeed.TypespeedWindow;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GraphicUtils {

    public static BufferStrategy setupRenderEnvironment(Canvas canvas) {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return null;
        }
        return bs;
    }

    public static void setBackground(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(0, 0, TypespeedWindow.WIDTH, TypespeedWindow.HEIGHT);
    }

}
