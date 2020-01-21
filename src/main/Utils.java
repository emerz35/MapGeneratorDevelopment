package main;

import java.util.Random;

public class Utils {

    public static Random R = new Random();

    /**
     * 
     * @param start
     * @param end
     * @return 
     */
    public static int randInt(int start, int end) {
        return start + R.nextInt(end-start);
    }
    
    /**
     * 
     * @param x
     * @param s
     * @return 
     */
    public static double kernel(double x, double s){
        return 1/(1+x/(s*s));
    }
}
