package pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;
import map.Point;

/**
 *
 * @author Charlie
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
    
    
    private boolean pointEquals(Point a, Point b){
        return a.x == b.x && a.y == b.y;
    }
    
    public LinkedList<Point> generatePath(Point start, Point end, Point[][] map){
        toVisit.clear();
        PathFindingPoint point = new PathFindingPoint(start);
        point.cost = 0;
        
        PathFindingPoint[][] pathMap = new PathFindingPoint[map.length][map[0].length]; 
        
        pathMap[start.y][start.x] = point;
        toVisit.add(point);
        PathFindingPoint temp;
        int ny,nx;
        while(!toVisit.isEmpty()){
            point = getLowestCost();
            if(!point.visited){
                if(pointEquals(point.point,end)) break;
            
                point.visited = true;

                for(int[] g:gs){
                    ny = point.point.y+g[1];
                    nx = point.point.x+g[0];
                    if(ny>=0&&ny<map.length&&nx>=0&&nx<map[0].length){
                        if(pathMap[ny][nx]==null){
                            temp = new PathFindingPoint(map[ny][nx]);
                            temp.cost = getCost(temp.point,point,end);
                            temp.from = point;
                            pathMap[ny][nx] = temp;
                            
                            insertPoint(temp);
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
        
        while(point.from!=null){
            path.addFirst(point.point);
            point = point.from;
        }
        return path;
    }
    
    private boolean containsPoint(List<PathFindingPoint> points,Point p){
        return points.stream().anyMatch(x->pointEquals(x.point,p));
        
    }
}
