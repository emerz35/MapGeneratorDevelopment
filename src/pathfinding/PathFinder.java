package pathfinding;

import java.util.LinkedList;
import java.util.function.BiFunction;
import map.Point;

/**
 *
 * @author Charlie
 */
public class PathFinder {
    
    private final LinkedList<PathFindingPoint> toVisit = new LinkedList<>(), visited = new LinkedList();
    
    private final Heuristic[] heuristics;
    
    private final int[][] gs = {{1,0},{0,-1},{-1,0},{0,1}};
    
    public static BiFunction<Point,Point,Double> manhattanDistance = (a,b)->(double)(Math.abs(a.x-b.x)+Math.abs(a.y-b.y));
    
    public PathFinder(Heuristic... heuristics){
        this.heuristics = heuristics;
    }
    
    /**
     * Finds the next point to visit
     * @return The PathFindingPoint in toVisit with the lowest cost
     */
    private PathFindingPoint getLowestCost(){
        return toVisit.stream().min((x,y)->(int)(x.cost-y.cost)).get();
    }
    
    /**
     * Gets the cost of the next tile
     * @param point The tile to get the cost of
     * @param end The goal tile to reach
     * @return 
     */
    private double getCost(PathFindingPoint point,PathFindingPoint current, PathFindingPoint end){
        double sum = 0;
        for(Heuristic h:heuristics){
            sum+=h.applyFunction(point.point, end.point);
        }
        return current.cost + 1 + sum;
    }
    
    
    private boolean pointEquals(Point a, Point b){
        return a.x == b.x && a.y == b.y;
    }
    
    public LinkedList<Point> generatePath(Point start, Point end, Point[][] map){
        PathFindingPoint point = new PathFindingPoint(start),finish = new PathFindingPoint(end);

        while(pointEquals(point.point, end)){
            for(PathFindingPoint p:toVisit){
                if(pointEquals(p.point,point.point)){
                    toVisit.remove(p);
                    break;
                }
            }
            visited.add(point);
            for(int[] g:gs){
                if(!(point.point.x+g[0]<0||point.point.x+g[0]>=map[0].length||point.point.y+g[1]<0||point.point.y+g[1]>=map.length)){

                    PathFindingPoint temp = new PathFindingPoint((map[point.point.y+g[1]][point.point.x+g[0]]));

                    double cost = getCost(temp,point,finish);
                    if(temp.cost>cost){
                        temp.cost = cost;
                        temp.from = point;
                        toVisit.add(temp);

                    }
                }
            }
            point = getLowestCost();
        }
        LinkedList<Point> path = new LinkedList<>();
        
        while(point.from!=null){
            path.addFirst(point.point);
            point = point.from;
        }
        return path;
    }
}
