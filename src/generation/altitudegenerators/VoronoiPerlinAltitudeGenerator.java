package generation.altitudegenerators;

import generation.AltitudeGenerator;
import map.Centroid;
import map.Point;

/**
 *
 * @author Charlie
 */
public class VoronoiPerlinAltitudeGenerator implements AltitudeGenerator{

    private final PerlinNoiseAltitudeGenerator perlin = new PerlinNoiseAltitudeGenerator();
    
    private static final int POLYGON_NUM = 500;
    
    private int centroidArea=0;
    
    @Override
    public Point[][] generate(Point[][] map) {
        centroidArea=2*(int)((double)(map[0].length*map.length)/(double)POLYGON_NUM);
        
        
        
        return map;
    }
    
    /**
     * @param c The centroid of the polygon
     * @param map The map to generate the polygon on
     * @return the map with the polygon on
     */
    private Point[][] generatePolygon(Centroid c, Point[][] map){
        return map;
    }
    
    
}
