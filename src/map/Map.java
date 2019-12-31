package map;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Map {

    private final BufferedImage image;
    
    public double zoom = 1;
    public int topX = 0,topY = 0;
    
    public Map(BufferedImage image){
        this.image = image;
    }

    public void renderImage(Graphics2D g) {
        AffineTransform t = AffineTransform.getTranslateInstance(topX, topY);
        t.concatenate(AffineTransform.getScaleInstance(zoom, zoom));
        g.drawImage(image, t, null);
    }

    public void saveToFile(String filePath) throws IOException {
        ImageIO.write(image, "png", new File(filePath));
    }
}
