package generation.altitudegenerators;

import generation.AltitudeGenerator;
import java.util.LinkedList;
import map.Centroid;
import map.Point;
import main.Utils;
import map.Biome;

/**
 *
 * @author Charlie
 */
public class VoronoiPerlinAltitudeGenerator implements AltitudeGenerator{

    private final PerlinNoiseAltitudeGenerator perlin = new PerlinNoiseAltitudeGenerator();
    
    private static final int POLYGON_NUM = 2000, ITERATIONS = 4;
    
    private int centroidArea = 0;
    
    @Override
    public Point[][] generate(Point[][] map) {
        centroidArea=(int)((double)(map[0].length*map.length)/(double)POLYGON_NUM);
        
        LinkedList<Centroid> centroids = new LinkedList<>();
        
        for(int i = 0; i < POLYGON_NUM; i++){
            centroids.add(new Centroid(Utils.R.nextInt(map[0].length),Utils.R.nextInt(map.length),i));
        }
        
        centroids.forEach((c) -> generatePolygon(c,map));
        for(int i = 1; i<ITERATIONS; i++){
            setCentroidPositions(map,centroids);
            centroids.forEach((c) -> generatePolygon(c,map));
        }
        PerlinNoiseAltitudeGenerator.generatePs();
        centroids.forEach(c-> c.altitude = perlin.getAltitudeAt(c.x, c.y, perlin.OCTAVES));
        
        for(int y = 0;y < map.length;y++){
            for(int x = 0; x < map[y].length;x++){
                map[y][x].altitude = map[y][x].centroid.altitude;
            }
        }
        
        
        
        return map;
    }
    
    /**
     * @param c The centroid of the polygon
     * @param map The map to generate the polygon on
     * @return the map with the polygon on
     */
    private Point[][] generatePolygon(Centroid c, Point[][] map){
        
        LinkedList<Point> frontier = new LinkedList<>();
        frontier.add(map[c.y][c.x]);
        while(!frontier.isEmpty()){
            Point p = frontier.remove();
            
            if(checkPoint(p.x,p.y+1,c,map)){
                frontier.add(map[p.y+1][p.x]);
                map[p.y+1][p.x].centroid = c;
            }
            if(checkPoint(p.x+1,p.y,c,map)){
                frontier.add(map[p.y][p.x+1]);
                map[p.y][p.x+1].centroid = c;
            }
            if(checkPoint(p.x-1,p.y,c,map)){
                frontier.add(map[p.y][p.x-1]);
                map[p.y][p.x-1].centroid = c;
            }
            if(checkPoint(p.x,p.y-1,c,map)){
                frontier.add(map[p.y-1][p.x]);
                map[p.y-1][p.x].centroid = c;
            }
            
        }
        
        return map;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param c
     * @param map
     * @return 
     */
    private boolean checkPoint(int x, int y, Centroid c, Point[][] map){
        if(y>=map.length||y<0) return false;
        if(x>=map[y].length||x<0)return false;
        Point p = map[y][x];
        if(p.centroid == null)return true;
        return p.distanceSquaredTo(p.centroid.x,p.centroid.y)>p.distanceSquaredTo(c.x,c.y)&&Math.abs(p.x-c.x)*Math.abs(p.y-c.y)<centroidArea;
    }
    
    private LinkedList<Centroid> setCentroidPositions(Point[][] map, LinkedList<Centroid> centroids){
      
        double[] sum = new double[centroids.size()], xsum = new double[centroids.size()], ysum = new double[centroids.size()];
        for(Point[] column:map){
            for(Point p:column){
                sum[p.centroid.num]++;
                xsum[p.centroid.num]+=p.x;
                ysum[p.centroid.num]+=p.y;
            }
        }
        centroids.forEach(c->{
            c.x = (int)(xsum[c.num]/sum[c.num]);
            c.y = (int)(ysum[c.num]/sum[c.num]);
        });
        return centroids;
    }
    
    
}
