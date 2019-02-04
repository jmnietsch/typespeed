package typespeed.UI;

import typespeed.Game.ObjectID;
import typespeed.TypespeedWindow;

import java.awt.*;
import java.time.Instant;
import java.util.Date;

import static java.time.temporal.ChronoUnit.SECONDS;

public class UICounter extends UIObject {

    private final Date inittime;
    private Date duetime;
    private Date timenow;

    private boolean stopGame = false;

    private static final Font TIMEFONT = new Font("Arial", Font.BOLD, 24);

    public UICounter(){
        super(ObjectID.UICounter);

        //Coordinate somewhere upper right corner
        Canvas c = new Canvas();
        FontMetrics fm = c.getFontMetrics(TIMEFONT);

        x = TypespeedWindow.WIDTH - fm.stringWidth("00:00") - 20;
        y = 25;

        //Set initial Time Values and the duedate 90 seconds from now.
        inittime = Date.from(Instant.now());

        duetime = new Date();
        timenow = new Date();
        duetime.setTime(Date.from(inittime.toInstant().plus(30, SECONDS)).getTime());
    }

    @Override
    public void tick() {
        timenow = new Date();

        if(stopGame){
            TypespeedWindow.getGame().stop();
        }
    }

    @Override
    public void render(Graphics g) {
        long diff = getRemainingTime();

        long sec = diff % 60;
        long min = ( diff-sec )/60;
        String time = String.format("%02d:%02d", min, sec);

        g.setColor(Color.RED);
        g.setFont(TIMEFONT);
        g.drawString(time, (int)x, (int)y);

        if(getRemainingTime() <= 0){
            stopGame = true;
        }
    }

    private long getRemainingTime() {
        return Math.round((duetime.getTime() - timenow.getTime()) / 1000.0d);
    }
}
