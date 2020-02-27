package generation.countrygenerators;

import generation.CountryGenerator;
import java.util.LinkedList;
import map.Point;

/**
 *
 * @author Charlie
 */
public class DefaultCountryGenerator implements CountryGenerator{

    @Override
    public Point[][] generate(Point[][] map) {
        LinkedList<Point> frontier = new LinkedList<>();
        
        while(!frontier.isEmpty()){
            Point p = frontier.remove();
            
            
        }
    }
    
    private boolean checkPoint(){
        
    }
    
}
