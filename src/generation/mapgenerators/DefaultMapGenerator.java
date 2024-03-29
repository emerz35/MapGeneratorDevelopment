package generation.mapgenerators;

import generation.AltitudeGenerator;
import generation.BiomeGenerator;
import generation.CountryGenerator;
import generation.ImageGenerator;
import generation.LandGenerator;
import generation.MapGenerator;
import generation.RiverGenerator;
import generation.RoadGenerator;
import static gui.Renderer.loadingScreen;
import map.Map;
import map.Point;

/**
 * The default map generator that will be used. It inherits from a super class MapGenerator in case there is need to create different map generators
 * @author Tuesday Hands
 */
public class DefaultMapGenerator extends MapGenerator{
    
    private final static int MAP_WIDTH = 2400, MAP_HEIGHT = 1600;
    
    /**
     * 
     * @param b The biome generator
     * @param a The altitude generator 
     * @param c The country generator
     * @param r The river generator
     * @param rd The road generator
     * @param l The land generator
     * @param i The image generator
     */
    public DefaultMapGenerator(BiomeGenerator b, AltitudeGenerator a, CountryGenerator c, RiverGenerator r, RoadGenerator rd, LandGenerator l, ImageGenerator i) {
        super(b, a, c, r, rd, l, i);
    }

    @Override
    public Map generateMap() {
        
        loadingScreen.message = "Initialising Map";
        Point[][] map = new Point[MAP_HEIGHT][MAP_WIDTH];
        
        for(int y = 0;y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                map[y][x] = new Point(x,y);
            }
        }
       
        loadingScreen.message = "Generating Altitudes";
        //long start = System.currentTimeMillis();
        map = altiGen.generate(map);
        
        loadingScreen.message = "Generating Land";
        map = landGen.generate(map);
        //System.out.println(System.currentTimeMillis()-start);
        
        map = rivGen.generate(map);
        
        //map = biomeGen.generate(map);
        loadingScreen.message = "Finishing up"; 
        return new Map(imageGen.generateImage(map));
    }
    
}
