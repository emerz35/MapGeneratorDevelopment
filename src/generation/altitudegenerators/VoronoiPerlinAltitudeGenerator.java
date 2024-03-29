package generation.altitudegenerators;

import generation.AltitudeGenerator;
import gui.GUISlider;
import java.util.LinkedList;
import map.Centroid;
import map.Point;
import main.Utils;
import map.Map;

/**
 *
 * @author Tuesday
 */
public class VoronoiPerlinAltitudeGenerator implements AltitudeGenerator{

    private final PerlinNoiseAltitudeGenerator perlin = new PerlinNoiseAltitudeGenerator();
    
    private static final int POLYGON_NUM = 8000, ITERATIONS = 2;
    
    private int centroidArea = 0;
    
    private final GUISlider sSlider, landMassSlider;
    
    public VoronoiPerlinAltitudeGenerator(GUISlider s, GUISlider landMass){
        sSlider = s;
        landMassSlider = landMass;
    }
    
    @Override
    public Point[][] generate(Point[][] map) {
        //Calculates the average area of a voronoi polygon with the number of centroids
        centroidArea=(int)((double)(map[0].length*map.length)/((double)POLYGON_NUM));
        
        Centroid[] centroids = new Centroid[POLYGON_NUM];
        
        //Initialises the centroids and generates a polygon for each of them
        for(int i = 0; i < POLYGON_NUM; i++){
            Centroid c = new Centroid(Utils.randInt(0, map[0].length),Utils.randInt(0, map.length),i);
            generatePolygon(c,map);
            centroids[i] = c;
        }
        
        //relaxes, then generates another polygon for each centroid
        for(int i = 1; i<ITERATIONS; i++){
            centroids = setCentroidPositions(map,centroids);
            for(Centroid c:centroids)generatePolygon(c,map);
        }
        PerlinNoiseAltitudeGenerator.generatePs();
        double s = sSlider.getNum();
        
        //Creates a centroid corresponding to each 'landmass' specified by the land mass slider
        Centroid[] landMasses = new Centroid[(int)landMassSlider.getNum()];
        
        for(int i = 0;i<landMassSlider.getNum();i++)
            landMasses[i] = new Centroid(Utils.randInt((int)((double)map[0].length/4d), (int)(3d*(double)map[0].length/4d)),Utils.randInt((int)((double)map.length/4d), (int)(3d*(double)map.length/4d)),0);
        
        //Sets the altitude of each voronoi centroid as perlin noise altitude * kernel (depending on distance to nearest land mass centroid)
        for(Centroid c:centroids){
            Centroid closest = getClosestCentroid(landMasses,c.x,c.y);
            c.altitude = (int)((double)perlin.getAltitudeAt(c.x, c.y, perlin.OCTAVES)*Utils.kernel((c.x-closest.x)*(c.x-closest.x)+(c.y-closest.y)*(c.y-closest.y),s));
        }
        
        Map.centroids = centroids;
        
        //Sets each point's altitude to the altitude of its centroid
        for (Point[] map1 : map) {
            for (Point map11 : map1) {
                map11.altitude = map11.centroid.altitude;
                //setNeighbours(map11,map);
            }
        }
        
        
        return map;
    }
    
    /**
     * @deprecated 
     * No longer needed
     * @param p
     * @param map 
     */
    private void setNeighbours(Point p, Point[][] map){
        for(int i = -1;i<2;i++){
            for(int j = -1;j<2;j++){
                if(j!=0 && i!=0 && p.x+j>=0&&p.x+j<map[0].length&&p.y+i>=0&&p.y+i<map.length){
                    if(!map[p.y+i][p.x+j].centroid.equals(p.centroid))p.centroid.adjacent.add(map[p.y+i][p.x+j].centroid);
                }
            }
        }
    }
    
    /**
     * Generates a voronoi polygon for the given centroid
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
     * Checks the point to see if it should be part of the centroid c
     * @param x x coordinate of point being checked
     * @param y y coordinate of point being checked
     * @param c The current centroid
     * @param map The map the point is on
     * @return Whether the point should belong to the centroid c
     */
    private boolean checkPoint(int x, int y, Centroid c, Point[][] map){
        if(y>=map.length||y<0) return false;
        if(x>=map[y].length||x<0)return false;
        Point p = map[y][x];
        if(p.centroid == null)return true;
        //c.adjacent.add(p.centroid);
        return p.distanceSquaredTo(p.centroid.x,p.centroid.y)>p.distanceSquaredTo(c.x,c.y)&&Math.abs(p.x-c.x)*Math.abs(p.y-c.y)<centroidArea;
    }
    
    /**
     * Lloyds' relaxation - each point is set to the position of the average of all the points under it
     * @param map
     * @param centroids
     * @return The list of centroids that have been relaxed
     */
    private Centroid[] setCentroidPositions(Point[][] map, Centroid[] centroids){
      
        double[] sum = new double[centroids.length], xsum = new double[centroids.length], ysum = new double[centroids.length];
        for(Point[] column:map){
            for(Point p:column){
                sum[p.centroid.num]++;
                xsum[p.centroid.num]+=p.x;
                ysum[p.centroid.num]+=p.y;
            }
        }
        for(Centroid c:centroids){
            c.x = (int)(xsum[c.num]/sum[c.num]);
            c.y = (int)(ysum[c.num]/sum[c.num]);
        }
        return centroids;
    }
    
    /**
     * Gets the closest centroid in a list to the point (x,y)
     * @param cs The list of centroids
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     * @return The closest centroid to (x,y)
     */
    private Centroid getClosestCentroid(Centroid[] cs, int x, int y){
        Centroid c2 = cs[0];
        for(Centroid c1:cs){
            if((c1.x-x)*(c1.x-x)+(c1.y-y)*(c1.y-y)<(c2.x-x)*(c2.x-x)-(c2.y-y)*(c2.y-y))c2 = c1;
        }
        return c2;
    }
}