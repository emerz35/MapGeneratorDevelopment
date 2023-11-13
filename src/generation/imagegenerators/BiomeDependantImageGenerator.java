package generation.imagegenerators;

import generation.ImageGenerator;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import map.Point;
import map.Map;

/**
 *
 * @author Tuesday
 */
public class BiomeDependantImageGenerator implements ImageGenerator{
    
    /**
     * Creates and image of the map. Each point is the colour given by that point's biome
     * @param map The map to create an image of
     * @return The image of the map
     */
    @Override
    public BufferedImage generateImage(Point[][] map) {
        BufferedImage image = new BufferedImage(map[0].length,map.length,BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                image.setRGB(x, y, map[y][x].biome.red *256*256+map[y][x].biome.green*256+map[y][x].biome.blue);
            }
        }
        Graphics2D g = image.createGraphics();
        Map.labels.forEach(x->x.render(g));
        return image;
    }
    
}
