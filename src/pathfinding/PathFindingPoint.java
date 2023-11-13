package pathfinding;

import map.Point;

/**
 *
 * @author Tuesday
 */
public class PathFindingPoint {
    public int cost=-1;
    public boolean visited=false;

    public final Point point;
    public PathFindingPoint from;
    
    /**
     * Creates a PthFindingPoint from the point p with -1 cost, that hasn't been visited
     * @param p 
     */
    public PathFindingPoint(Point p){
        point = p;
    }
}
