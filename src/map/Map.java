package map;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.Main;

public class Map {
    
    private static final double MAX_ZOOM = 32, MIN_ZOOM = 1;
    public static final int TOP_LEFT_X = Main.M_WIDTH/4, TOP_LEFT_Y = 0, AREA_WIDTH = 3*Main.M_WIDTH/4, AREA_HEIGHT = Main.M_HEIGHT;

    private final BufferedImage image;
    
    public double zoom = 1;
    public int topX = TOP_LEFT_X,topY = TOP_LEFT_Y;
    
    public Map(BufferedImage image){
        this.image = image;
    }
    
    /**
     * Translates the image according to topX and topY, zooms the image according to zoom, and draws it to Graphics2d
     * @param g 
     */
    public void renderImage(Graphics2D g) {
        AffineTransform t = AffineTransform.getTranslateInstance(topX, topY);
        t.scale(zoom, zoom);
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
     * @param mx 
     * @param my 
     */
    public void zoom(int change, int mx, int my){
        double zoomChange = Math.pow(2, change);
        zoom*=zoomChange;
        if(zoom>MAX_ZOOM)zoom = MAX_ZOOM;
        else if(zoom<MIN_ZOOM) zoom = MIN_ZOOM;
        else{
            if(change>0){
                translateX(topX-mx);
                translateY(topY-my);
            }
            else{
                translateX((mx-topX)/2);
                translateY((my-topY)/2);
                
            }
            
        }
    }
    
    /**
     * Translates the image in the x direction  
     * @param dx The distance in the x direction to displace the image
     */
    public void translateX(int dx){
        topX+=dx;
        if(topX>TOP_LEFT_X) topX = TOP_LEFT_X;
        else if(topX-TOP_LEFT_X+image.getWidth()*zoom<AREA_WIDTH)topX=TOP_LEFT_X+AREA_WIDTH-(int)(image.getWidth()*zoom);
    }
    
    /**
     * Translates the image in the y direction  
     * @param dy The distance in the y direction to displace the image
     */
    public void translateY(int dy){
        topY+=dy;
        if(topY>TOP_LEFT_Y) topY = TOP_LEFT_Y;
        else if(topY+image.getHeight()*zoom<AREA_HEIGHT)topY=AREA_HEIGHT-(int)(image.getHeight()*zoom);
    }
}
