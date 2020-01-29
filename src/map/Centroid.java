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
    
    public Centroid(int x, int y, int num){
        this.num = num;
        this.x = x;
        this.y = y;
    }
}
