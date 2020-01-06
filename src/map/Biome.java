package map;

public enum Biome {

    LAND(0,255,0), SEA(0,0,255), RIVER(0,0,0);
    
    public int red,blue,green;
    Biome(int r, int g, int b){
        red = r;
        green = g;
        blue = b;
    }
}
