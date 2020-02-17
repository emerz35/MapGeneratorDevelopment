package main;

import java.util.Random;

public class Utils {

    public static Random R = new Random();

    /**
     * Generates a random integer between start (inclusive) and end (exclusive)
     * @param start The lowest value the int can be (includes start)
     * @param end The upper bound the generated int can take (excludes end)
     * @return The random int
     */
    public static int randInt(int start, int end) {
        return start + R.nextInt(end-start);
    }
    
    /**
     * The Gaussian kernel
     * @param x The distance squared - input to this should be not negative
     * @param s A constant value
     * @return e^(-x/(s^2))
     */
    public static double kernel(double x, double s){
        //return 1/(1+x/(s*s));
        return Math.exp(-x/(s*s));
    }
}
