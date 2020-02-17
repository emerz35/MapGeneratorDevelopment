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
    
    /**
     * Calculates the euclidean distance of this point to the point (x,y)
     * @param x The x coordinate of the point to calculate the distance to
     * @param y The y coordinate of the point to calculate the distance to
     * @return The distance of this point to (x,y) squared
     */
    public int distanceSquaredTo(int x, int y) {
        return (this.x - x)*(this.x - x) + (this.y - y)*(this.y - y);
    }
    
    /**
     * 
     * @return Whether the point is land or not
     */
    public boolean isLand(){
        return biome!=Biome.RIVER&& biome!=Biome.SEA;
    }
}
