package generation.altitudegenerators;

import generation.AltitudeGenerator;
import main.Utils;
import map.Point;

/**
 *
 * @author Charlie
 */
public class PerlinNoiseAltitudeGenerator implements AltitudeGenerator{
    
    private final static double SPACING = 10;
    private final static int MAX_ALTITUDE = 255,MIN_ALTITUDE = 0;
    private final static double[][] Gs = new double[2][10];
    private final static int[] Ps = new int[200]; 
    static{
        for(double[] g:Gs){
            double j = Utils.R.nextDouble();
            g = new double[]{Math.cos(2*Math.PI*j),Math.sin(2*Math.PI*j)};
            
        }
        for(int p:Ps){
            p = Utils.R.nextInt();
        }
    }
    @Override
    public Point[][] generate(Point[][] map) {
        for(int y = 0;y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                map[y][x].altitude = MIN_ALTITUDE + (int)(perlinNoiseAt(x,y)*(double)(MAX_ALTITUDE - MIN_ALTITUDE));
            }
        }
        return map;
    }
    
    private double perlinNoiseAt(double x, double y){
        double i = roundDown(x,SPACING),j = roundDown(y,SPACING);
        
        double dx = (x-i)/SPACING, dy = (y-j)/SPACING;
        
        int i2 = ((int) i & 0xFFF)/(int)SPACING ;
        int j2 = ((int) j & 0xFFF)/(int)SPACING ;
        
        double[] g00 = Gs[Ps[i2 + Ps[j2]]%Gs.length], 
                g01 = Gs[Ps[i2 + Ps[j2+1]]%Gs.length],
                g11 = Gs[Ps[i2 + 1 + Ps[j2+1]]%Gs.length],
                g10 = Gs[Ps[i2 + 1 + Ps[j2]]%Gs.length];
        
        double n00 = dot(g00,dx,dy), n01 = dot(g01,dx,dy-1), n11 = dot(g11,dx-1,dy-1), n10 = dot(g10,dx-1,dy);
        
        dx = fade(dx);
        dy = fade(dy);
        
        double nx0 = mix(n00,n01,dx), nx1 = mix(n01,n11,dx);
        
        return mix(nx0,nx1,dy);
    }
    
    private double roundDown(double a, double b){
        return a - a%b;
    }
    
    private double dot(double[] g, double x, double y){
        return g[0]*x+g[1]*y;
    }
    
    private double fade(double x){
        return x*x*x*(x*(6*x-15)+10);
    }
    
    private double mix(double a, double b, double t){
        return (1-t)*a+t*b;
    }
}