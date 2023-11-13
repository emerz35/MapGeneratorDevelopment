package generation.altitudegenerators;

import generation.AltitudeGenerator;
import main.Utils;
import map.Point;

/**
 *
 * @author Tuesday
 */
public class PerlinNoiseAltitudeGenerator implements AltitudeGenerator{
    
    public final double[][] OCTAVES = {{0.25,100},{0.35,200},{0.4,400}};
    private final static int MAX_ALTITUDE = 500,MIN_ALTITUDE = -100;
    private final static double[][] Gs = new double[20][2];
    private final static int[] Ps = new int[2000]; 
    static{
        for(int i = 0; i<Gs.length;i++){
            double j = Utils.R.nextDouble();
            Gs[i] = new double[]{Math.cos(2*Math.PI*j),Math.sin(2*Math.PI*j)};
        }
    }
    
    /**
     * Generates new random numbers so that consecutive maps aren't the same
     */
    public static void generatePs(){
        for(int i = 0;i<Ps.length;i++){
            Ps[i] = Utils.R.nextInt(1000);
        }
    }
    
    /**
     * Assigns a pseudorandom altitude to each point based on its x and y coordinates, the random vectors in Gs, and the random numbers in Ps
     * @param map
     * @return 
     */
    @Override
    public Point[][] generate(Point[][] map) {
        generatePs();
        for(int y = 0;y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                map[y][x].altitude += getAltitudeAt(x,y,OCTAVES);
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
    public int getAltitudeAt(double x, double y, double[]... octaves){
        double relativeAlti = 0;
        for(double[] o:octaves){
            relativeAlti += o[0]* perlinNoiseAt(x,y,o[1]);
        }
        return MIN_ALTITUDE + (int)((relativeAlti+1)/2d *(double)(MAX_ALTITUDE - MIN_ALTITUDE));
    }
    
    /**
     * Gets the pseudorandom number of the point (x,y)
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     * @param spacing The pixel gap between the points assigned a vector
     * @return The generated number 
     */
    private double perlinNoiseAt(double x, double y,double spacing){
        //Finds the x and y coordinates of the top left most point of a square with lengths spacing 
        double i = roundDown(x,spacing),j = roundDown(y,spacing);
        
        //finds the distance between the coordinates and the top left point. It the divides this by spacing to get a number between 0 and 1
        double dx = (x-i)/spacing, dy = (y-j)/spacing;
        
        //shortens the i and j values to less than 4095 then divides it by spacing
        int i2 = ((int) i & 0xFFF)/(int)spacing ;
        int j2 = ((int) j & 0xFFF)/(int)spacing ;
        
        //Finds the vector corresponding to the corners of the square the point is in
        double[] g00 = Gs[Ps[i2 + Ps[j2]]%Gs.length], 
                g01 = Gs[Ps[i2 + Ps[j2+1]]%Gs.length],
                g11 = Gs[Ps[i2 + 1 + Ps[j2+1]]%Gs.length],
                g10 = Gs[Ps[i2 + 1 + Ps[j2]]%Gs.length];
        
        //Computes the scalar product of the vector corresponding to each corner with the vector from the point to that corner
        double n00 = dot(g00,dx,dy), n01 = dot(g01,dx,dy-1), n11 = dot(g11,dx-1,dy-1), n10 = dot(g10,dx-1,dy);
        
        //
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
    
    /**
     * Applies a fading formula to x
     * @param x The input to the formula
     * @return 6x^5 -15x^4 + 10x^3
     */
    private double fade(double x){
        return x*x*x*(x*(x*6-15)+10);
    }
    
    /**
     * A form of linear interpolation giving a final output between a & b depending on the given ration t
     * @param a 
     * @param b
     * @param t
     * @return The sum of parts of a and be depending on the ratio t
     */
    private double mix(double a, double b, double t){
        return (1-t)*a+t*b;
    }
}
