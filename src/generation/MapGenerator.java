package generation;

import map.Map;

public abstract class MapGenerator {
    
    //Initialising all the generators needed to generate the map
    public BiomeGenerator biomeGen;
    public AltitudeGenerator altiGen;
    public CountryGenerator countryGen;
    public RiverGenerator rivGen;
    public RoadGenerator roadGen;
    public LandGenerator landGen;
    
    //The constructor
    public MapGenerator(BiomeGenerator b, AltitudeGenerator a, CountryGenerator c, RiverGenerator r, RoadGenerator rd, LandGenerator l){
        biomeGen = b;
        altiGen = a;
        countryGen = c;
        rivGen = r;
        roadGen = rd;
        landGen = l;
    }
    
    //The abstract method that is called. This will generate whole map.
    public abstract Map generateMap();
}
