package typespeed.Utils;

import org.apache.commons.math3.analysis.function.Gaussian;
import typespeed.Game.TypespeedGame;
import typespeed.Game.GameObject;
import typespeed.Game.ObjectID;

import java.awt.*;
import java.util.*;


public class RangefinderService extends GameObject {
    //If True: Display gaussian curves used for cost calculation for next position.
    private static final boolean DEBUG_VISUALIZATION = false;

    //The Amount of Random positions, that will be looked at for their cost. The cheapest one will be used.
    private final static int AMOUNT_POSITIONS = 10;
    private double[] positions = new double[RangefinderService.AMOUNT_POSITIONS];

    //Scales display of gaussian Curves in horizontal direction (for better visibility)
    private static final float DEFAULT_GAUSSIAN_SCALING = 100;

    //Scale the y samplings of gaussian curve.
    private static final int DEFAULT_SCREEN_HEIGHT = 1;

    //The basic Amount of Ticks, a gaussian is alive. The actual value will be a random multiple of this.
    private static final int LIFETICKS = 300;

    //Track the remaining lifespan of any gaussian and document all "live" gaussians in the ID array.
    private Map<Gaussian, Integer> lifespan;
    private ArrayList<Gaussian> gaussianId;

    //The default Gauss will slightly reduce the Cost in the center.
    private static final Gaussian defaultGaussian = new Gaussian(0.5, 0.3);


    public RangefinderService() {
        super(ObjectID.Rangefinder);

        randomizePositions();
        lifespan = new HashMap<>();
        gaussianId = new ArrayList<>();
    }

    @Override
    public void tick() {
        ArrayList<Gaussian> deadGaussianList = new ArrayList<>();

        for (Gaussian gaussian : gaussianId) {
            if(lifespan.containsKey(gaussian)){
                int lifeReduction = 1;

                //Update Remaining Life
                int remainingLife = lifespan.get(gaussian);
                lifespan.put(gaussian, remainingLife - lifeReduction);

                //Remove if to be considered dead.
                if(remainingLife < 0)
                    deadGaussianList.add(gaussian);

            } else {
                //The Gaussian is not in the life-list. Remove also from Index.
                deadGaussianList.add(gaussian);
            }
        }

        //Remove gaussian from tracked Structures.
        for(Gaussian gaussian : deadGaussianList){
            removeGaussian(gaussian);
        }
    }

    @Override
    public void render(Graphics g) {
        //All This rendering Output is only for "understanding" the choosing Mechanism.
        //Turn DEBUG_VISUALIZATION on for Visualization of Cost for each position.
        if(!DEBUG_VISUALIZATION) return;

        //Indicate the currently sampled positions
        g.setColor(Color.BLUE);
        for(double d : positions){
            g.drawRect(0, (int) (TypespeedGame.HEIGHT * d), 5, 1);
        }

        ArrayList<Float> yList = new ArrayList<>();
        ArrayList<Float> xSumList = new ArrayList<>();

        int pointsOnCurve = 40;
        float height = TypespeedGame.HEIGHT - 60;
        float step = 1.0f / (pointsOnCurve-1);

        //Init y points in step between 0 and 1. (Convenient for Gaussian probability)
        for(int i=0; i < pointsOnCurve; ++i){
            float point = step * i;
            yList.add(point);

            //Use this opportunity to add N values to xSumList, so it is properly initialized later.
            xSumList.add(0.0f);
        }

        //Paint all Gaussians from -1 to N.
        for(int i=-1; i<gaussianId.size(); ++i){
            Gaussian gaussian = getGaussian(i);

            //Calculate Gaussian Curve
            ArrayList<Float> xList = getGaussianCurve(gaussian, yList);

            //Choose Color for this Gaussian
            Random random = new Random(gaussianId.indexOf(gaussian));
            Color randomColor = Color.getHSBColor(random.nextFloat(), 0.8f, 0.8f);
            g.setColor(randomColor);

            //Paint this Gaussian Curve
            drawGaussianPoly(g, xList, yList, height);

            //Adds all Values except the default one. That is subtracted.
            for (int t = 0; t < yList.size(); t++) {
                float factor = (gaussian.equals(defaultGaussian)) ? -0.2f : 1.0f;

                xSumList.set(t, xSumList.get(t) + factor * xList.get(t));
            }
        }

        g.setColor(Color.RED);
        drawGaussianPoly(g, xSumList, yList, height);
    }

    private void drawGaussianPoly(Graphics g, ArrayList<Float> xList, ArrayList<Float> yList, float height, float scale) {
        ArrayList<Float> yListScaled = new ArrayList<>(yList);
        ArrayList<Float> xListScaled = new ArrayList<>(xList);

        //Scale Height according to Parameter
        if (height != 1.0){
            yListScaled.replaceAll(y -> y = y*height);
        }

        //Scale visual amplitude according to Parameter
        if(scale != 1.0f){
            xListScaled.replaceAll(x -> x = x*scale);
        }

        //Draw actual Line / Curve according to given points.
        g.drawPolyline(
                xList.stream().mapToDouble(i -> i).mapToInt(i -> (int) i).toArray(),
                yListScaled.stream().mapToDouble(i -> i).mapToInt(i -> (int) i).toArray(),
                xList.size());
    }

    private void drawGaussianPoly(Graphics g, ArrayList<Float> xList, ArrayList<Float> yList) {
        drawGaussianPoly(g, xList, yList, DEFAULT_SCREEN_HEIGHT);
    }

    private void drawGaussianPoly(Graphics g, ArrayList<Float> xList, ArrayList<Float> yList, float height) {
        drawGaussianPoly(g, xList, yList, height, DEFAULT_GAUSSIAN_SCALING);
    }

    private ArrayList<Float> getGaussianCurve(Gaussian gaussian, ArrayList<Float> yList){
        ArrayList<Float> xList = new ArrayList<>();

        //Query remaining lifetime of gaussian. Take resulting "amplitude = 1" for default-gaussian
        int lifeticks = lifespan.getOrDefault(gaussian, LIFETICKS);
        float amplitude = (float)lifeticks / LIFETICKS;

        amplitude = Math.round(amplitude*50) / 50.0f; //Rounding to a precision of 0.02

        if(amplitude > 0.85) amplitude *= 2;
        if(amplitude > 0.60) amplitude *= 2;

        //For each point along the y axis, query the matching gaussian probability.
        for(float point : yList){
            float xPos = (float) (amplitude * 20 * gaussian.value(point));
            xList.add(xPos);
        }

        return xList;
    }

    private void randomizePositions() {
        Random random = new Random();

        for(int i = 0; i< AMOUNT_POSITIONS; ++i){
            positions[i] = random.nextFloat();
        }
        Arrays.sort(positions);
    }

    /**
     * Get a randomized 1d position to spawn a new Tile at. The next value will be less likely to get the same spot.
     * @return random Position with not too much correlation with recent previous positions.
     */
    public double getNextPosition(){
        randomizePositions();

        Map<Double, Double> costMap = new HashMap<>();

        for(double possiblePosition : positions){
            double cost = sampleAllGaussianCostsAt(possiblePosition);

            costMap.put(possiblePosition, cost);
        }

        //Take the minimal Cost point.
        double minimalCost = Collections.min(costMap.values());

        //Determine the matching position to this cost
        for(double position : costMap.keySet()){
            if(costMap.get(position).equals(minimalCost)){
                System.out.printf("%.2f: %3.2f\n", position, minimalCost);
                addGaussian(position);

                return position;
            }
        }

        System.err.println("No valid minimum has been found as next random Position. Using 0.5 instead.");
        return 0.5f;
    }

    private Gaussian getGaussian(int id){
        if (id < 0) return defaultGaussian;

        return gaussianId.get(id);
    }

    private void addGaussian(double position) {
        if(position < 0.1) position = 0.0;
        if(position > 0.9) position = 1.0;

        //Generate a new Gaussian at this position, that will increase the future cost //Selected sigma = 0.2 for its tight curve
        Gaussian g = new Gaussian(position, 0.2);

        //Add gaussian with its given Lifespan to the List of gaussians involved in the cost calculation and add to index.
        lifespan.put(g, LIFETICKS);
        gaussianId.add(g);
    }

    private void removeGaussian(Gaussian gaussian) {
        lifespan.remove(gaussian);
        gaussianId.remove(gaussian);
    }

    private double sampleAllGaussianCostsAt(double possiblePosition) {

        double costForSampledPosition = 0.0;

        for(Gaussian gaussian : lifespan.keySet()){
            int lifeticks = lifespan.get(gaussian);

            float amplitude = (float)lifeticks / LIFETICKS;
            amplitude = Math.max(amplitude, 1);

            double costForPosition = gaussian.value(possiblePosition);

            costForSampledPosition += amplitude * costForPosition;
        }

        //The Default Curve will improve the Chances in the Center for new Values.
        double defaultBonus = defaultGaussian.value(possiblePosition);
        costForSampledPosition -= defaultBonus;

        return costForSampledPosition;
    }

}
