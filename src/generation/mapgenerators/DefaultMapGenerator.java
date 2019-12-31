package generation.mapgenerators;

import generation.AltitudeGenerator;
import generation.BiomeGenerator;
import generation.CountryGenerator;
import generation.LandGenerator;
import generation.MapGenerator;
import generation.RiverGenerator;
import generation.RoadGenerator;
import map.Map;

/**
 *
 * @author Charlie Hands
 */
public class DefaultMapGenerator extends MapGenerator{

    public DefaultMapGenerator(BiomeGenerator b, AltitudeGenerator a, CountryGenerator c, RiverGenerator r, RoadGenerator rd, LandGenerator l) {
        super(b, a, c, r, rd, l);
    }

    @Override
    public Map generateMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
