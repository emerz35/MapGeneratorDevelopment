package generation.imagegenerators;

import generation.ImageGenerator;
import java.awt.image.BufferedImage;
import map.Point;

/**
 *
 * @author Charlie
 */
public class BiomeDependantImageGenerator implements ImageGenerator{

    @Override
    public BufferedImage generateImage(Point[][] map) {
        BufferedImage image = new BufferedImage(map[0].length,map.length,BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                image.setRGB(x, y, map[y][x].biome.red *256*256+map[y][x].biome.green*256+map[y][x].biome.blue);
            }
        }
        return image;
    }
    
}
