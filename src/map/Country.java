package map;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tuesday
 */
public class Country {
    
    public String name;
    public int[] colour = new int[3];
    public List<Settlement> settlements = new LinkedList<>();
    
    public Country(String name, int r, int g, int b){
        this.name = name;
        colour[0] = r;
        colour[1] = g;
        colour[2] = b;
    }
}
