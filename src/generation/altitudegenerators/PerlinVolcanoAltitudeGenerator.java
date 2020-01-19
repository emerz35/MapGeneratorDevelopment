package generation.altitudegenerators;

import generation.AltitudeGenerator;
import map.Point;

/**
 *
 * @author Charlie
 */
public class PerlinVolcanoAltitudeGenerator implements AltitudeGenerator{

    private final PerlinNoiseAltitudeGenerator perlin = new PerlinNoiseAltitudeGenerator();
    private final VolcanoAltitudeGenerator volcano = new VolcanoAltitudeGenerator();
   
    
    @Override
    public Point[][] generate(Point[][] map) {
        map = volcano.generate(map);
        map = perlin.generate(map);
        return map;
        
    }
    
}
