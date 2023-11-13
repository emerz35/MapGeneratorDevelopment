package generation.altitudegenerators;

import generation.AltitudeGenerator;
import main.Utils;
import map.Point;

/**
 *
 * @author Tuesday Hands
 */
public class VolcanoAltitudeGenerator implements AltitudeGenerator{

    public static final int MAX_ALTITUDE = 1000000, LANDMASS_NUM = 1;
    public static final double DECAY = 0.9;
    
    /**
     * 
     * @param map
     * @return The altitude 
     */
    @Override
    public Point[][] generate(Point[][] map) {
        //for(int i = 0;i<LANDMASS_NUM;i++){
            map = generateLandMass(Utils.R.nextInt(map[0].length),Utils.R.nextInt(map.length),map,MAX_ALTITUDE,DECAY);
        //}
        return map;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param map
     * @param altitude
     * @param decay
     * @return 
     */
    private Point[][] generateLandMass(int x, int y, Point[][] map, int altitude, double decay){
        if(altitude<300)return map;
        map[y][x].altitude+=altitude;

        for(int i = -1; i<2; i++){
            for(int j = -1;j<2;j++){
                if(!(j==0&&i==0)){
                    if(x+j<map[0].length && x+j>=0&&y+i<map.length&&y+i>=0){
                        /*if(map[y+i][x+j].altitude<altitude)*/map = generateLandMass(x+j,y+i,map,(int)((double)altitude*decay),decay*(0.7+0.3*Utils.R.nextDouble()));
                    }
                }
            }
        }
        return map;
        
    }
    
}
