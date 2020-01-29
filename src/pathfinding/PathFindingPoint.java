package pathfinding;

import map.Point;

/**
 *
 * @author Charlie
 */
public class PathFindingPoint {
    public double cost = 99999999;

    public final Point point;
    public PathFindingPoint from;
    
    public PathFindingPoint(Point p){
        point = p;
    }
}
