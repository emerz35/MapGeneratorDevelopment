package pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.BiFunction;
import map.Point;

/**
 *
 * @author Tuesday
 */
public class PathFinder {
    
    private final ArrayList<PathFindingPoint> toVisit = new ArrayList<>();
    
    private final Heuristic[] heuristics;
    
    private final int[][] gs = {{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1}};
    
    public static BiFunction<Point,Point,Double> manhattanDistance = (a,b)->(double)(Math.abs(a.x-b.x)+Math.abs(a.y-b.y)), euclideanDistance = (a,b)->(double)(a.distanceSquaredTo(b.x, b.y));
    
    public PathFinder(Heuristic... heuristics){
        this.heuristics = heuristics;
    }
    
    /**
     * Finds the next point to visit
     * @return The PathFindingPoint in toVisit with the lowest cost
     */
    private PathFindingPoint getLowestCost(){
        
        PathFindingPoint point = toVisit.remove(0);
        //point.point.biome = Biome.VISITED;
        return point;
    }
    
    /**
     * Inserts a point in the list in ascending order of costs
     * @param toInsert 
     */
    private void insertPoint(PathFindingPoint toInsert){
        if(toVisit.isEmpty()){
            toVisit.add(toInsert);
            return;
        }
        for(int i = 0;i<toVisit.size();i++){
            if(toInsert.cost<toVisit.get(i).cost){
                toVisit.add(i,toInsert);
                return;
            }
        }
        toVisit.add(toInsert);
    }
    
    /**
     * Gets the cost of the next tile
     * @param point The tile to get the cost of
     * @param end The goal tile to reach
     * @return 
     */
    private int getCost(Point point,PathFindingPoint current, Point end){
        double sum = 0;
        for(Heuristic h:heuristics){
            sum+=h.applyFunction(point, end);
        }
        return current.cost + 1 + (int)sum;
    }
    
    /**
     * Checks if 2 points have the same x and y coordinate (are equal)
     * @param a A point to check
     * @param b A point to check
     * @return Whether a & b are equal
     */
    private boolean pointEquals(Point a, Point b){
        return a.x == b.x && a.y == b.y;
    }
    
    /**
     * Finds a path between start and end using the heuristics given
     * @param start The point to start from
     * @param end The end point
     * @param map The map that contains the points
     * @return A list containing all the points in the path
     */
    public LinkedList<Point> generatePath(Point start, Point end, Point[][] map){
        //Clears the list of visited points
        toVisit.clear();
        
        //Creates a PathFindingPoint from the start point and sets its cost to 0
        PathFindingPoint point = new PathFindingPoint(start);
        point.cost = 0;
        
        //Creates a map of PathFindingPoints
        PathFindingPoint[][] pathMap = new PathFindingPoint[map.length][map[0].length]; 
        
        pathMap[start.y][start.x] = point;
        toVisit.add(point);
        PathFindingPoint temp;
        int ny,nx;
        //Loops until there are no points to visit - this should never be false as it should find the point before this happens
        while(!toVisit.isEmpty()){
            //finds the point with the lowest cost
            point = getLowestCost();
            if(!point.visited){
                //exits the loop if the point being visited is the end point
                if(pointEquals(point.point,end)) break;
            
                point.visited = true;
                
                //Checks all neighbours
                for(int[] g:gs){
                    ny = point.point.y+g[1];
                    nx = point.point.x+g[0];
                    if(ny>=0&&ny<map.length&&nx>=0&&nx<map[0].length){
                        //Creates a new PathFindingPoint if the the point being checked has never been checked before 
                        if(pathMap[ny][nx]==null){
                            temp = new PathFindingPoint(map[ny][nx]);
                            temp.cost = getCost(temp.point,point,end);
                            temp.from = point;
                            pathMap[ny][nx] = temp;
                            
                            insertPoint(temp);
                        //If the point has been checked before, it updates its cost and readds it to the list of points to visit with its updated cost if its new cost is lower than its previous cost
                        }else{
                            temp = pathMap[ny][nx];
                            int cost = getCost(temp.point,point,end);
                            if(!temp.visited&&temp.cost>cost){
                                temp.cost = cost;
                                temp.from = point;
                                toVisit.remove(temp);
                                insertPoint(temp);
                            }
                        }
                    }
                }
            }
        }
        LinkedList<Point> path = new LinkedList<>();
        
        //Backtracks from the start point to add the path to a list
        while(point.from!=null){
            path.addFirst(point.point);
            point = point.from;
        }
        return path;
    }
}
