package map;

public enum Biome {

    LAND(11,102,35), 
    SEA(16,52,166), 
    RIVER(16,52,166), 
    COAST(237,201,175), 
    MOUNTAIN(139,69,19), 
    //VISITED(255,192,203), 
    HIGH_MOUNTAIN(139,0,139), 
    LOW_MOUNTAIN(205,133,63),
    TROPICAL_RAINFOREST(0,100,0),
    TEMPERATE_RAINFOREST(34,139,34),
    SAVANNA(144,105,2),
    SUBTROPICAL_DESERT(230,166,68),
    TEMPERATE_DECIDUOUS_FOREST(11,102,35),
    WOODLAND(79,97,45),
    TEMPERATE_GRASSLANDS(153,153,0),
    TAIGA(255,250,250),
    TUNDRA(240,255,255),
    UNDECIDED(0,0,0)
    ;
    
    public int red,blue,green;
    Biome(int r, int g, int b){
        red = r;
        green = g;
        blue = b;
    }
}
