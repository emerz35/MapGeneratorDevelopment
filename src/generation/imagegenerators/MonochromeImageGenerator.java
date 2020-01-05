package generation.imagegenerators;

import generation.ImageGenerator;
import java.awt.image.BufferedImage;
import map.Point;

/**
 *
 * @author Charlie
 */
public class MonochromeImageGenerator implements ImageGenerator{

    @Override
    public BufferedImage generateImage(Point[][] map) {
        BufferedImage image = new BufferedImage(map[0].length,map.length,BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                image.setRGB(x, y, map[y][x].altitude*256*256 + map[y][x].altitude*256+map[y][x].altitude);
            }
        }
        return image;
    }
}
