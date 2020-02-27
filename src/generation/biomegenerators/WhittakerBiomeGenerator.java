package generation.biomegenerators;

import generation.BiomeGenerator;
import gui.GUISlider;
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
    private static final double ALTITUDE_CONSTANT = 1.5,DISTANCE_CONSTANT = 20;
    
    private final GUISlider seaSlider;
    
    public WhittakerBiomeGenerator(GUISlider s){
        seaSlider = s;
    }
    
    @Override
    public Point[][] generate(Point[][] map) {
        double k = (int)((double)map.length/6d);
        int seaAlti = (int)seaSlider.getNum();
        
        for(Point[] column:map){
            for(Point p:column){
                if(p.y<(double)map.length/6d){
                    p.prevailingWind[0] = -(int)((double)p.y/k);
                    p.prevailingWind[1] = -(int)(Math.sqrt(k*k-p.y*p.y)/k);
                }
                else if(p.y<(double)map.length/2d){
                    int y = (int)((double)map.length/3d) - p.y;
                    p.prevailingWind[0] = y;
                    p.prevailingWind[1] = (int)(Math.signum(y)*Math.sqrt(k*k-y*y)/k);
                }
                else if(p.y<5d*(double)map.length/6d){
                    int y = (int)(2d*(double)map.length/3d-p.y);
                    p.prevailingWind[0] = y;
                    p.prevailingWind[1] = (int)(Math.signum(y)*Math.sqrt(k*k-y*y)/k);
                }
                else{
                    int y = map.length-p.y;
                    p.prevailingWind[0] = y;
                    p.prevailingWind[1] = (int)(Math.signum(y)*Math.sqrt(k*k-y*y)/k);
                }
            }
        }        
        
        int latitude,temp = 0,precipitation = 0;
        
        for(Centroid c:Map.centroids){
            //System.out.println("ok");
            if(map[c.y][c.x].biome == Biome.LAND){
                latitude = Math.abs(c.y-(int)((double)map.length/2d));
                /*
                temp = 25-(int)(45*2d*(double)latitude/(double)map.length - (double)(c.altitude-seaAlti)/100d);
                if(temp<-15)temp = -15;
                precipitation = (int)(11.1d*(double)temp+160.7d);
                */
                Point p = map[c.y][c.x];
                int highestAlti = 0,distance=0;
                for(;distance<1.5d*DISTANCE_CONSTANT&&p.isLand();distance++){
                    if(p.altitude>highestAlti)highestAlti = p.altitude;
                    if(p.y-p.prevailingWind[1]<0||p.y-p.prevailingWind[1]>=map.length||p.x-p.prevailingWind[0]<0||p.x-p.prevailingWind[0]>=map[0].length)break;
                    p = map[p.y-p.prevailingWind[1]][p.x-p.prevailingWind[0]];
                    //p.biome = Biome.UNDECIDED;
                }
                if(latitude>5d*(double)map.length/12d)temp=0;
                else if(latitude>(double)map.length/3d)temp = 1;
                else if(latitude>(double)map.length/6d)temp = 2;
                else temp=3;
                //temp = 3-(int)(8d*(double)latitude/(double)map.length);
                //if(highestAlti<ALTITUDE_CONSTANT*(double)c.altitude)precipitation++;
                if(highestAlti>ALTITUDE_CONSTANT*(double)c.altitude)precipitation--;
                else if(distance<DISTANCE_CONSTANT)precipitation++;
                if(latitude>3d*(double)map.length/12d&&latitude<5d*(double)map.length/12d)precipitation-=3;
                
                if(latitude<(double)map.length/25d)precipitation+=2;
                
                if(precipitation<0) precipitation = 0;
                if(precipitation>4)precipitation = 4;
                map[c.y][c.x].biome= getBiome(temp,precipitation);
            }
        }
        
        for(Point[] column:map){
            for(Point p:column){
                if(p.biome==Biome.LAND)p.biome = map[p.centroid.y][p.centroid.x].biome;
            }
        }
        return map;
    }
    
    
    private Biome getBiome(double temp, double precipitation){
        if(temp<=0)return Biome.TUNDRA;
        if(temp<=1){
            if(precipitation >= 2)return Biome.TAIGA;
            return Biome.WOODLAND;
        }
        if(temp<=2){
            if(precipitation>=3)return Biome.TEMPERATE_RAINFOREST;
            if(precipitation>=2)return Biome.TEMPERATE_DECIDUOUS_FOREST;
            if(precipitation>=1)return Biome.WOODLAND;
            return Biome.TEMPERATE_GRASSLANDS;
        }
        if(precipitation>=3)return Biome.TROPICAL_RAINFOREST;
        if(precipitation>=2)return Biome.SAVANNA;
        return Biome.SUBTROPICAL_DESERT;
        
        //return Biome.UNDECIDED;
        
        /*try {
            BufferedImage image = ImageIO.read(new File(DIAGRAM));
            WritableRaster r = image.getRaster();
            int[] pixel = new int[4];
            
            double x = 128d+9.4d*(30d-temp),y = 438d-0.89d*precipitation;
            pixel = r.getPixel((int)x, (int)y, pixel);
            
            if(Utils.pixelEquals(pixel,8,250,54))return Biome.TROPICAL_RAINFOREST;
            if(Utils.pixelEquals(pixel,7,249,162))return Biome.TEMPERATE_RAINFOREST;
            if(Utils.pixelEquals(pixel,155,224,35))return Biome.SAVANNA;
            if(Utils.pixelEquals(pixel,250,148,24))return Biome.SUBTROPICAL_DESERT;
            if(Utils.pixelEquals(pixel,46,177,83))return Biome.TEMPERATE_DECIDUOUS_FOREST;
            if(Utils.pixelEquals(pixel,246,6,41))return Biome.WOODLAND;
            if(Utils.pixelEquals(pixel,246,220,1))return Biome.TEMPERATE_GRASSLANDS;
            if(Utils.pixelEquals(pixel,5,102,33))return Biome.TAIGA;
            if(Utils.pixelEquals(pixel,87,235,249))return Biome.TUNDRA;
            System.out.println(temp+", "+precipitation);
            return Biome.UNDECIDED;
        } catch (IOException ex) {
            //Logger.getLogger(WhittakerBiomeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            return Biome.UNDECIDED;
        }*/
    }
    
}
