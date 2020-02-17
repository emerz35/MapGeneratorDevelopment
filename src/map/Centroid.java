package map;

import java.util.LinkedList;

/**
 *
 * @author Charlie
 */
public class Centroid {
    
    public final int num;
    public int x,y, altitude=0;
    public LinkedList<Centroid> adjacent = new LinkedList<>();
    
    /**
     * Constructor for a centroid used as centroids for a voronoi diagram
     * @param x x coordinate of the centroid
     * @param y y coordinate of the centroid
     * @param num a number corresponding to the centroid - used as an identifier
     */
    public Centroid(int x, int y, int num){
        this.num = num;
        this.x = x;
        this.y = y;
    }
}
