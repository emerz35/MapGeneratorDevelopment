package map;

public enum Biome {

    LAND(11,102,35,1), 
    SEA(16,52,166,200), 
    RIVER(16,52,166,50), 
    COAST(237,201,175,1), 
    MOUNTAIN(139,69,19,70), 
    //VISITED(255,192,203), 
    HIGH_MOUNTAIN(139,0,139,75), 
    LOW_MOUNTAIN(205,133,63,45),
    TROPICAL_RAINFOREST(0,100,0,75),
    TEMPERATE_RAINFOREST(34,139,34,60),
    SAVANNA(144,105,2,50),
    SUBTROPICAL_DESERT(230,166,68,75),
    TEMPERATE_DECIDUOUS_FOREST(11,102,35,10),
    WOODLAND(79,97,45,5),
    TEMPERATE_GRASSLANDS(153,153,0,10),
    TAIGA(255,250,250,20),
    TUNDRA(240,255,255,50),
    SETTLEMENT(255,0,0,1000),
    BORDER(0,0,0,0);
    
    public int red,blue,green,cost;
    Biome(int r, int g, int b, int c){
        red = r;
        green = g;
        blue = b;
        cost = c;
    }
}
