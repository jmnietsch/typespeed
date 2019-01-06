package typespeed;

import org.apache.commons.math3.analysis.function.Gaussian;

import java.awt.*;
import java.util.*;


public class Rangefinder extends GameObject{

    private final static int AMOUNT_POSITIONS = 10;
    private double[] positions = new double[Rangefinder.AMOUNT_POSITIONS];
//    private double[] positions = {0.1,0.2,0.5,0.8,0.9 };

    private Map<Gaussian, Integer> lifespan;
    private static final int LIFETICKS = 2000;


    protected Rangefinder() {
        super(ObjectID.Rangefinder);

        randomizePositions();
        lifespan = new HashMap<>();
    }

    private void randomizePositions() {
        Random random = new Random();

        for(int i = 0; i< AMOUNT_POSITIONS; ++i){
            positions[i] = random.nextFloat();
        }
        Arrays.sort(positions);
    }

    @Override
    public void tick() {
        for (Gaussian gaussian : lifespan.keySet()) {
            int currentlife = lifespan.get(gaussian);
            lifespan.put(gaussian, currentlife-1);
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        for(double d : positions){
            g.drawRect(0, (int) (Game.HEIGHT * d), 2, 1);
        }

    }

    public double getNextPosition(){
        Map<Double, Double> resultMap = new HashMap<>();
        randomizePositions();

        //Gaussian gaussian = new Gaussian(0.5,0.2);

        for(double possiblePosition : positions){
            double sum = 0.0;

            for(Gaussian gaussian : lifespan.keySet()){
                int lifeticks = lifespan.get(gaussian);
                float amplitude = (float)lifeticks / LIFETICKS;

                double reducingValue = gaussian.value(possiblePosition);

                sum += 10*amplitude*reducingValue;
            }

            resultMap.put(possiblePosition, sum);
        }

        double minimalPenalty = Collections.min(resultMap.values());

        double pos = 0.5;
        for(double val : resultMap.keySet()){
            if(resultMap.get(val).equals(minimalPenalty)){
                pos = val;
                lifespan.put(new Gaussian(pos, 0.2), LIFETICKS);

                break;
            }
        }

        return pos;
    }

}
