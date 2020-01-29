package generation.mapgenerators;

import generation.AltitudeGenerator;
import generation.BiomeGenerator;
import generation.CountryGenerator;
import generation.ImageGenerator;
import generation.LandGenerator;
import generation.MapGenerator;
import generation.RiverGenerator;
import generation.RoadGenerator;
import map.Map;
import map.Point;

/**
 *
 * @author Charlie Hands
 */
public class DefaultMapGenerator extends MapGenerator{
    
    private final static int MAP_WIDTH = 4*800, MAP_HEIGHT = 4*600;
    

    public DefaultMapGenerator(BiomeGenerator b, AltitudeGenerator a, CountryGenerator c, RiverGenerator r, RoadGenerator rd, LandGenerator l, ImageGenerator i) {
        super(b, a, c, r, rd, l, i);
    }

    @Override
    public Map generateMap() {
        Point[][] map = new Point[MAP_HEIGHT][MAP_WIDTH];
        
        for(int y = 0;y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                map[y][x] = new Point(x,y);
            }
        }
       
        long start = System.currentTimeMillis();
        map = landGen.generate(altiGen.generate(map));
        System.out.println(System.currentTimeMillis()-start);
        //map = rivGen.generate(map);
                
        return new Map(imageGen.generateImage(map));
    }
    
}
