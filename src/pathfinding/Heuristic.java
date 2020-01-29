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
    
    public Heuristic(double c, BiFunction<Point, Point , Double> f){
        constant = c;
        function = f;
    }
    
    public double applyFunction(Point a, Point b){
        return constant * function.apply(a, b);
    }
}
