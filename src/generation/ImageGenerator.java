package generation;

import java.awt.image.BufferedImage;
import map.Point;

/**
 *
 * @author Charlie 
 */
public interface ImageGenerator {
    public BufferedImage generateImage(Point[][] map);
    
}
