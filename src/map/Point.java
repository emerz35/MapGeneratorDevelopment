package map;

public class Point {

    public final int x, y;

    public int altitude = 0;
    
    public Centroid centroid;
    
    public Biome biome = Biome.SEA;
    
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int distanceSquaredTo(int x, int y) {
        return (this.x - x)*(this.x - x) + (this.y - y)*(this.y - y);
    }
}
