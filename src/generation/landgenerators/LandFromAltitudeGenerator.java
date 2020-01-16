package generation.landgenerators;

import generation.LandGenerator;
import gui.GUISlider;
import map.Biome;
import map.Point;

/**
 *
 * @author Charlie
 */
public class LandFromAltitudeGenerator implements LandGenerator{
    
    private final GUISlider altiSlider;
    
    public LandFromAltitudeGenerator(GUISlider s){
        altiSlider = s;
    }
    
    @Override
    public Point[][] generate(Point[][] map) {
        int landAltitude = (int) altiSlider.getNum();
        for(int y = 0;y<map.length;y++){
            for(int x = 0; x<map[y].length;x++){
                if(map[y][x].altitude>landAltitude){
                    map[y][x].biome = Biome.LAND;
                }
            }
        }
        return map;
    }
    
}
