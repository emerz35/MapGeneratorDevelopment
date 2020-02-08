package pathfinding;

import map.Point;

/**
 *
 * @author Charlie
 */
public class PathFindingPoint {
    public int cost=-1;
    public boolean visited=false;

    public final Point point;
    public PathFindingPoint from;
    
    public PathFindingPoint(Point p){
        point = p;
    }
}
