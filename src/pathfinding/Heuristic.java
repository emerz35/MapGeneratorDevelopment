package pathfinding;

import java.util.function.BiFunction;
import map.Point;

/**
 *
 * @author Charlie
 */
public class Heuristic {
    
    private final double constant;
    private final BiFunction<Point, Point , Double> function;
    
    /**
     * the constructor for heuristics
     * @param c The constant to times the output of the heuristic function by
     * @param f The heuristic function - takes the point being checked and the end point as parameters and outputs a double
     */
    public Heuristic(double c, BiFunction<Point, Point , Double> f){
        constant = c;
        function = f;
    }
    
    /**
     * Finds the output of the heuristic function on the 2 points given times by the constant
     * @param a The point being checked
     * @param b The end point
     * @return The output of the function times the constant
     */
    public double applyFunction(Point a, Point b){
        return constant * function.apply(a, b);
    }
}
