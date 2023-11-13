/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generation.imagegenerators;

import generation.ImageGenerator;
import gui.GUISlider;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import map.Map;
import map.Point;

/**
 *
 * @author Tuesday Hands
 */
public class AltitudeDependantImageGenerator implements ImageGenerator{
    
    private final GUISlider altiSlider;
    
    public AltitudeDependantImageGenerator(GUISlider slider){
        altiSlider = slider;
    }
    
    @Override
    public BufferedImage generateImage(Point[][] map) {
        int r=0,g=0,b=0;
        double alti=altiSlider.getNum();
        BufferedImage image = new BufferedImage(map[0].length,map.length,BufferedImage.TYPE_INT_RGB);
        for(int y = 0; y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                if(map[y][x].altitude>alti&&map[y][x].isLand()){
                    if(map[y][x].altitude>alti*1.5d){
                        if(map[y][x].altitude>alti*1.7d) {
                            if(map[y][x].altitude>alti*1.9d){
                                r=255;b=255;g=255;
                            }
                            else{
                                r=139;
                                g=linearInterpolate(69,0,((double)map[y][x].altitude-alti*1.7d)/(alti*0.2d));
                                b=linearInterpolate(19,139,((double)map[y][x].altitude-alti*1.7d)/(alti*0.2d));
                                //System.out.println(""+((double)map[y][x].altitude-alti*1.7d)+"/"+(alti*0.2) + "=" + ((double)map[y][x].altitude-alti*1.7d)/alti*0.2d);
                            }
                        }
                        else {
                                r=linearInterpolate(205,139,((double)map[y][x].altitude-alti*1.5d)/(alti*0.2d));
                                g=linearInterpolate(133,69,((double)map[y][x].altitude-alti*1.5d)/(alti*0.2d));
                                b=linearInterpolate(63,19,((double)map[y][x].altitude-alti*1.5d)/(alti*0.2d));
                            }
                    }
                else {
                        r=linearInterpolate(11,205,((double)map[y][x].altitude-alti)/(alti*0.5d));
                        g=linearInterpolate(102,133,((double)map[y][x].altitude-alti)/(alti*0.5d));
                        b=linearInterpolate(35,63,((double)map[y][x].altitude-alti)/(alti*0.5d));
                    }
                }else{
                    r = map[y][x].biome.red;
                    g = map[y][x].biome.green;
                    b = map[y][x].biome.blue;
                }
                image.setRGB(x, y, r *256*256+g*256+b);
            }
        }
        Graphics2D gr = image.createGraphics();
        Map.labels.forEach(x->x.render(gr));
        return image;
    }
    
    private int linearInterpolate(int a, int b, double t){
        return (int)((double)b*t+(1f-t)*(double)a);
    }
}
