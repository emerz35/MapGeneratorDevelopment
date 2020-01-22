package generation.altitudegenerators;

import generation.AltitudeGenerator;
import gui.GUISlider;
import java.util.LinkedList;
import map.Centroid;
import map.Point;
import main.Utils;

/**
 *
 * @author Charlie
 */
public class VoronoiPerlinAltitudeGenerator implements AltitudeGenerator{

    private final PerlinNoiseAltitudeGenerator perlin = new PerlinNoiseAltitudeGenerator();
    
    private static final int POLYGON_NUM = 2000, ITERATIONS = 2, LAND_MASS_NUM = 7;
    
    private int centroidArea = 0;
    
    private final GUISlider sSlider;
    
    public VoronoiPerlinAltitudeGenerator(GUISlider s){
        sSlider = s;
    }
    
    @Override
    public Point[][] generate(Point[][] map) {
        //Calculates the average area of a voronoi polygon with the number of centroids
        centroidArea=(int)((double)(map[0].length*map.length)/((double)POLYGON_NUM));
        
        LinkedList<Centroid> centroids = new LinkedList<>();
        
        //Initialises the centroids and generates a polygon for each of them
        for(int i = 0; i < POLYGON_NUM; i++){
            Centroid c = new Centroid(Utils.randInt(0, map[0].length),Utils.randInt(0, map.length),i);
            generatePolygon(c,map);
            centroids.add(c);
        }
        
        //relaxes, then generates another polygon for each centroid
        /*for(int i = 1; i<ITERATIONS; i++){
            centroids = setCentroidPositions(map,centroids);
            centroids.forEach((c) -> generatePolygon(c,map));
        }*/
        PerlinNoiseAltitudeGenerator.generatePs();
        double s = sSlider.getNum();
        
        LinkedList<Centroid> landMasses = new LinkedList<>();
        
        for(int i = 0;i<LAND_MASS_NUM;i++)
            landMasses.add(new Centroid(Utils.randInt((int)((double)map[0].length/4d), (int)(3d*(double)map[0].length/4d)),Utils.randInt((int)((double)map.length/4d), (int)(3d*(double)map.length/4d)),0));
        
        centroids.forEach(c-> {
            
            Centroid closest = getClosestCentroid(landMasses,c.x,c.y);
            c.altitude = (int)((double)perlin.getAltitudeAt(c.x, c.y, perlin.OCTAVES)*Utils.kernel((c.x-closest.x)*(c.x-closest.x)+(c.y-closest.y)*(c.y-closest.y),s));
                    });
        
        for (Point[] map1 : map) {
            for (Point map11 : map1) {
                map11.altitude = map11.centroid.altitude;
            }
        }
       
        return map;
    }
    
    /**
     * @param c The centroid of the polygon
     * @param map The map to generate the polygon on
     * @return the map with the polygon on
     */
    private void generatePolygon(Centroid c, Point[][] map){
        
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
    
    /**
     * Lloyds' relaxation - each point is set to the position of the average of all the points under it
     * @param map
     * @param centroids
     * @return The list of centroids that have been relaxed
     */
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
    
    /**
     * 
     * @param cs
     * @param x
     * @param y
     * @return 
     */
    private Centroid getClosestCentroid(LinkedList<Centroid> cs, int x, int y){
        return cs.stream().min((c1,c2)-> (c1.x-x)*(c1.x-x)+(c1.y-y)*(c1.y-y)-(c2.x-x)*(c2.x-x)-(c2.y-y)*(c2.y-y)).get();
    }
}