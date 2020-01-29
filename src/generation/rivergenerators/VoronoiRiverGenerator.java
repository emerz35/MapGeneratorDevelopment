package generation.rivergenerators;

import exceptions.SeaNotFoundException;
import generation.RiverGenerator;
import gui.GUISlider;
import java.util.LinkedList;
import main.Utils;
import map.Biome;
import map.Centroid;
import map.Point;
import pathfinding.Heuristic;
import pathfinding.PathFinder;
import map.Map;

/**
 *
 * @author Charlie
 */
public class VoronoiRiverGenerator implements RiverGenerator{
    
    
    private final PathFinder riverFinder = new PathFinder(new Heuristic(1,PathFinder.manhattanDistance));
    
    private final GUISlider minAltitude;
    
    
    public VoronoiRiverGenerator(GUISlider s){
        minAltitude = s;
    }
            
    @Override
    public Point[][] generate(Point[][] map) {
        /*Centroid start = Map.centroids.get(Utils.randInt(0,Map.centroids.size()));
        while(start.altitude<minAltitude.getNum()) start = Map.centroids.get(Utils.randInt(0,Map.centroids.size()));*/
        Point start = map[Utils.randInt(0, map.length)][Utils.randInt(0, map[0].length)];
        Point sea = map[Utils.randInt(0, map.length)][Utils.randInt(0, map[0].length)];
        LinkedList<Point> river = riverFinder.generatePath(map[start.y][start.x], sea, map);
        river.forEach(x->{x.biome = Biome.RIVER;System.out.println("ok");});
        return map;
    }
    
    /**
     * This chooses a sea point by flood filling excluding any higher altitude points
     * @param p The point to start at
     * @param map
     * @return The sea point found
     * @throws SeaNotFoundException if no sea tile is found
     */
    private Point findSea(Centroid start, Point[][] map) throws SeaNotFoundException{
        LinkedList<Centroid> frontier =  new LinkedList<>();
        
        frontier.add(start);
        
        while(!frontier.isEmpty()){
            Centroid centroid = frontier.remove();
            if(centroid.altitude<minAltitude.getNum()) return map[centroid.y][centroid.x];
            centroid.adjacent.forEach(c->{
                if(checkPoint(c.x,c.y,map,centroid)) frontier.add(c);
            });
            
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
    private boolean checkPoint(int x, int y, Point[][] map, Centroid current){
        if(y<0||y>=map.length) return false;
        if(x<0||x>=map[y].length) return false;
        return map[y][x].altitude < current.altitude;
    }
}
