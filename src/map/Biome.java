package map;

public enum Biome {

    LAND(11,102,35), SEA(16,52,166), RIVER(59,179,208), COAST(237,201,175);
    
    public int red,blue,green;
    Biome(int r, int g, int b){
        red = r;
        green = g;
        blue = b;
    }
}
