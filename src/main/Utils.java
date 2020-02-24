package main;

import generation.biomegenerators.WhittakerBiomeGenerator;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Utils {

    public static Random R = new Random();

    /**
     * Generates a random integer between start (inclusive) and end (exclusive)
     * @param start The lowest value the int can be (includes start)
     * @param end The upper bound the generated int can take (excludes end)
     * @return The random int
     */
    public static int randInt(int start, int end) {
        return start + R.nextInt(end-start);
    }
    
    /**
     * The Gaussian kernel
     * @param x The distance squared - input to this should be not negative
     * @param s A constant value
     * @return e^(-x/(s^2))
     */
    public static double kernel(double x, double s){
        //return 1/(1+x/(s*s));
        return Math.exp(-x/(s*s));
    }
    
    public static boolean pixelEquals(int[] a,int... b){
        for(int i=0;i<3;i++)if(a[i]!=b[i])return false;
        return true;
    }
    /*
    public static void main(String... args) throws IOException{
        BufferedImage image = ImageIO.read(new File(WhittakerBiomeGenerator.DIAGRAM));
        int[] pixel = new int[4];
        WritableRaster r = image.getRaster();
        for(int y = 0; y<image.getHeight();y++){
            for(int x = 0;x<image.getWidth();x++){
                pixel = r.getPixel(x, y, pixel);
                if(pixelEquals(pixel,255,174,201))System.out.println(x+", "+y);
                //System.out.println("ok");
                //System.out.println(image.isAlphaPremultiplied());
            }
        }
    }
    */
}
