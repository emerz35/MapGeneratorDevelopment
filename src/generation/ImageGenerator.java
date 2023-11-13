package generation;

import java.awt.image.BufferedImage;
import map.Point;

/**
 *
 * @author Tuesday 
 */
public interface ImageGenerator {
    public BufferedImage generateImage(Point[][] map);
    
}
