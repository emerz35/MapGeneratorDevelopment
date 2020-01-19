package map;

/**
 *
 * @author Charlie
 */
public class Centroid {
    public final int num;
    public int x,y, altitude=0;
    
    public Centroid(int x, int y, int num){
        this.num = num;
        this.x = x;
        this.y = y;
    }
}
