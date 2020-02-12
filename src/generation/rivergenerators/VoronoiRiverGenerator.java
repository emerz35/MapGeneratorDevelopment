package generation.rivergenerators;

import exceptions.SeaNotFoundException;
import generation.RiverGenerator;
import gui.GUISlider;
import java.util.LinkedList;
import main.Utils;
import map.Biome;
import map.Centroid;
import map.Map;
import map.Point;
import pathfinding.Heuristic;
import pathfinding.PathFinder;

/**
 *
 * @author Charlie
 */
public class VoronoiRiverGenerator implements RiverGenerator{
    
    
    private final PathFinder riverFinder = new PathFinder(new Heuristic(0.00001,PathFinder.euclideanDistance), new Heuristic(3,(a,b)->(double)Utils.randInt(0,100)),new Heuristic(20,(a,b)->(double)a.altitude));
    
    private final GUISlider minAltitude, riverNum;
    
    private static final double ALTITUDE_CONSTANT = 1.1;
    //private static final int RIVER_NUM = 4;
    
    public VoronoiRiverGenerator(GUISlider s, GUISlider r){
        minAltitude = s;
        riverNum = r;
    }
            
    @Override
    public Point[][] generate(Point[][] map) {
        for(int r = 0;r<riverNum.getNum();r++){
            System.out.println("Finding river: "+(r+1) +"/"+(int)riverNum.getNum());
            Centroid start = Map.centroids[Utils.randInt(0,Map.centroids.length)];
            while(start.altitude<minAltitude.getNum()*ALTITUDE_CONSTANT&&!map[start.y][start.x].isLand()) start = Map.centroids[Utils.randInt(0,Map.centroids.length)];
            Point startPoint = map[start.y][start.x];
            //Point start = map[1000][1000];
            try{
                Point sea = findSea(startPoint,map);
                LinkedList<Point> river = riverFinder.generatePath(map[start.y][start.x], sea, map);
                //river.forEach(x->x.biome = Biome.RIVER);
                for(int i = 0; i<river.size();i++){
                    setRiver(i,river.get(i),map);
                }
                //sea.biome = Biome.RIVER;
            }catch(SeaNotFoundException e){
                System.err.println(e.getMessage());
                //e.printStackTrace();
            }
        }
        return map;
    }
    
    
    private void setRiver(int num, Point p, Point[][] map){
        //p.biome = Biome.RIVER;
        //double width = Math.log1p((double)num/2d);
        double width = Math.sqrt((double)num)/2;
        for(int i = (int)(-width/2d);i<=(int)(width/2d);i++){
            for(int j = (int)(-width/2d);j<=(int)(width/2d);j++){
                int nx = p.x+j,ny = p.y+i;
                if(nx>=0&&nx<map[0].length&&ny>=0&&ny<map.length){
                    map[ny][nx].biome = Biome.RIVER;
                }
            }
        }   
    }
    
    /**
     * This chooses a sea point by flood filling excluding any higher altitude points
     * @param p The point to start at
     * @param map
     * @return The sea point found
     * @throws SeaNotFoundException if no sea tile is found
     */
    private Point findSea(Point start, Point[][] map) throws SeaNotFoundException{
        LinkedList<Point> frontier =  new LinkedList<>(),visited = new LinkedList<>();
        
        frontier.add(start);
        
        while(!frontier.isEmpty()){
            Point p = frontier.remove(0);
            if(!visited.contains(p)){
                if(p.biome != Biome.LAND) return p;
                visited.add(p);

                if(checkPoint(p.x,p.y+1,map,p,visited)){
                    frontier.add(map[p.y+1][p.x]);
                }
                if(checkPoint(p.x+1,p.y,map,p,visited)){
                    frontier.add(map[p.y][p.x+1]);
                }
                if(checkPoint(p.x-1,p.y,map,p,visited)){
                    frontier.add(map[p.y][p.x-1]);
                }
                if(checkPoint(p.x,p.y-1,map,p,visited)){
                    frontier.add(map[p.y-1][p.x]);
                }
            }
        }
        throw new SeaNotFoundException();
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param map
     * @return 
     */
    private boolean checkPoint(int x, int y, Point[][] map, Point current, LinkedList<Point> visited){
        if(y<0||y>=map.length) return false;
        if(x<0||x>=map[y].length) return false;
        //if(!map[y][x].centroid.equals(current.centroid))System.out.println("different");
        return !visited.contains(map[y][x])&&map[y][x].altitude <= current.altitude;
    }
}