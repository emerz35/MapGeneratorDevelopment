package map;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Main;

public class Map {
    
    private static final double MAX_ZOOM = 8, MIN_ZOOM = 1;

    private final BufferedImage image;
    
    public double zoom = 1;
    public int topX = 0,topY = 0;
    
    public Map(BufferedImage image){
        this.image = image;
    }
    
    /**
     * Translates the image according to topX and topY, zooms the image according to zoom, and draws it to Graphics2d
     * @param g 
     */
    public void renderImage(Graphics2D g) {
        AffineTransform t = AffineTransform.getTranslateInstance(topX, topY);
        t.concatenate(AffineTransform.getScaleInstance(zoom, zoom));
        g.drawImage(image, t, null);
    }
    /**
     * 
     * @param filePath The path of the file to write to
     * @throws IOException 
     */
    public void saveToFile(String filePath) throws IOException {
        ImageIO.write(image, "png", new File(filePath));
    }
    /**
     * Zooms the map in or out dependant on the integer change. Math.pow has been used instead of coding own power to handle negative powers 
     * @param change The number of mouse wheel 'clicks' that have been moved 
     */
    public void zoom(int change){
        zoom*=Math.pow(2, change);
        if(zoom>MAX_ZOOM)zoom = MAX_ZOOM;
        else if(zoom<MIN_ZOOM) zoom =  MIN_ZOOM;
    }
    
    /**
     * Translates the image in the x direction  
     * @param dx The distance in the x direction to displace the image
     */
    public void translateX(int dx){
        topX+=dx;
        if(topX>0) topX = 0;
        else if(topX+image.getWidth()*zoom<Main.M_WIDTH)topX=Main.M_WIDTH-(int)(image.getWidth()*zoom);
    }
    
    /**
     * Translates the image in the y direction  
     * @param dy The distance in the y direction to displace the image
     */
    public void translateY(int dy){
        topY+=dy;
        if(topY>0) topY = 0;
        else if(topY+image.getHeight()*zoom<Main.M_HEIGHT)topX=Main.M_HEIGHT-(int)(image.getHeight()*zoom);
    }
}
