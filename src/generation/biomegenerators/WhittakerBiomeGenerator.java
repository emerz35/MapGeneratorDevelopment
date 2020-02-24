package generation.biomegenerators;

import generation.BiomeGenerator;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Utils;
import map.Biome;
import map.Centroid;
import map.Map;
import map.Point;

/**
 *
 * @author Charlie
 */
public class WhittakerBiomeGenerator implements BiomeGenerator{

    private static final String DIAGRAM = "images_test\\WhittakerDiagram.png";
    
    @Override
    public Point[][] generate(Point[][] map) {
        Centroid[] centroids = Map.centroids;
        return null;
    }
    
    
    private Biome getBiome(double temp, double precipitation){
        try {
            BufferedImage image = ImageIO.read(new File(DIAGRAM));
            WritableRaster r = image.getRaster();
            int[] pixel = new int[4];
            
            double x = 128d+9.4d*(30d-temp),y = 438d-0.89d*precipitation;
            pixel = r.getPixel((int)x, (int)y, pixel);
            
            if(Utils.pixelEquals(pixel,8,250,54))return Biome.TROPICAL_RAINFOREST;
            else if(Utils.pixelEquals(pixel,7,249,162))return Biome.TEMPERATE_RAINFOREST;
            else if(Utils.pixelEquals(pixel,155,224,35))return Biome.SAVANNA;
            else if(Utils.pixelEquals(pixel,250,148,24))return Biome.SUBTROPICAL_DESERT;
            else if(Utils.pixelEquals(pixel,46,177,83))return Biome.TEMPERATE_DECIDUOUS_FOREST;
            else if(Utils.pixelEquals(pixel,246,6,41))return Biome.WOODLAND;
            else if(Utils.pixelEquals(pixel,246,220,1))return Biome.TEMPERATE_GRASSLANDS;
            else if(Utils.pixelEquals(pixel,5,102,33))return Biome.TAIGA;
            else if(Utils.pixelEquals(pixel,87,235,249))return Biome.TUNDRA;
            return null;
        } catch (IOException ex) {
            //Logger.getLogger(WhittakerBiomeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
