package generation.landgenerators;

import generation.LandGenerator;
import map.Biome;
import map.Point;

/**
 *
 * @author Charlie
 */
public class LandFromAltitudeGenerator implements LandGenerator{

    private final static int LAND_ALTITUDE = 100;
    
    @Override
    public Point[][] generate(Point[][] map) {
        for(int y = 0;y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                if(map[y][x].altitude>LAND_ALTITUDE){
                    map[y][x].biome = Biome.LAND;
                }
            }
        }
        return map;
    }
    
}
