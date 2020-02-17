package map;

public enum Biome {

    LAND(11,102,35), SEA(16,52,166), RIVER(16,52,166), COAST(237,201,175), MOUNTAIN(139,69,19), VISITED(255,192,203), HIGH_MOUNTAIN(139,0,139), LOW_MOUNTAIN(205,133,63);
    
    public int red,blue,green;
    Biome(int r, int g, int b){
        red = r;
        green = g;
        blue = b;
    }
}
