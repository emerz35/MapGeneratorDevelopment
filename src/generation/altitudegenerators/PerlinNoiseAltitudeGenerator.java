package generation.altitudegenerators;

import generation.AltitudeGenerator;
import main.Utils;
import map.Point;

/**
 *
 * @author Charlie
 */
public class PerlinNoiseAltitudeGenerator implements AltitudeGenerator{
    
    private final static double[][] OCTAVES = {{0.6,100},{0.1,250},{0.3,150}};
    private final static int MAX_ALTITUDE = 255,MIN_ALTITUDE = 0;
    private final static double[][] Gs = new double[20][2];
    private final static int[] Ps = new int[2000]; 
    static{
        for(int i = 0; i<Gs.length;i++){
            double j = Utils.R.nextDouble();
            Gs[i] = new double[]{Math.cos(2*Math.PI*j),Math.sin(2*Math.PI*j)};
        }
    }
    
    private static void generatePs(){
        for(int i = 0;i<Ps.length;i++){
            Ps[i] = Utils.R.nextInt(1000);
        }
    }
    
    @Override
    public Point[][] generate(Point[][] map) {
        generatePs();
        for(int y = 0;y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                map[y][x].altitude = getAltitudeAt(x,y,OCTAVES);
            }
        }
        return map;
    }
  
    /**
     * 
     * @param x
     * @param y
     * @param octaves Array of arrays relating to the different 'octaves' used when generating altitude. These will be different times the algorithm is run. First num is max amplitude percentage, second is spacing
     * @return 
     */
    private int getAltitudeAt(double x, double y, double[]... octaves){
        double relativeAlti = 0;
        for(double[] o:octaves){
            relativeAlti += o[0]* perlinNoiseAt(x,y,o[1]);
        }
        return MIN_ALTITUDE + (int)((relativeAlti+1)/2d *(double)(MAX_ALTITUDE - MIN_ALTITUDE));
    }
    
    private double perlinNoiseAt(double x, double y,double spacing){
        double i = roundDown(x,spacing),j = roundDown(y,spacing);
        
        double dx = (x-i)/spacing, dy = (y-j)/spacing;
        
        int i2 = ((int) i & 0xFFF)/(int)spacing ;
        int j2 = ((int) j & 0xFFF)/(int)spacing ;
        
        double[] g00 = Gs[Ps[i2 + Ps[j2]]%Gs.length], 
                g01 = Gs[Ps[i2 + Ps[j2+1]]%Gs.length],
                g11 = Gs[Ps[i2 + 1 + Ps[j2+1]]%Gs.length],
                g10 = Gs[Ps[i2 + 1 + Ps[j2]]%Gs.length];
       
        double n00 = dot(g00,dx,dy), n01 = dot(g01,dx,dy-1), n11 = dot(g11,dx-1,dy-1), n10 = dot(g10,dx-1,dy);
        
        dx = fade(dx);
        dy = fade(dy);
        
        double nx0 = mix(n00,n10,dx), nx1 = mix(n01,n11,dx);
        
        return mix(nx0,nx1,dy);
    }
    
    private double roundDown(double a, double b){
        return a - a%b;
    }
    
    private double dot(double[] g, double x, double y){
        return g[0]*x+g[1]*y;
    }
    
    private double fade(double x){
        return x*x*x*(x*(x*6-15)+10);
    }
    
    private double mix(double a, double b, double t){
        return (1-t)*a+t*b;
    }
}
