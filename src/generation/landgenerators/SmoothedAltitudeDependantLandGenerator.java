package generation.landgenerators;

import generation.LandGenerator;
import gui.GUISlider;
import map.Biome;
import map.Point;

/**
 *
 * @author Charlie
 */
public class SmoothedAltitudeDependantLandGenerator implements LandGenerator{

    private final LandFromAltitudeGenerator landGen;
    
    public SmoothedAltitudeDependantLandGenerator(GUISlider s){
        landGen = new LandFromAltitudeGenerator(s);
    }
    
    @Override
    public Point[][] generate(Point[][] map) {
        
        map = landGen.generate(map);
        
        map = seaSmoother(map);
        
        return map;
    }
    
    //Rounds off edges of land masses and removes small bits of sea in a land mass
    public Point[][] seaSmoother(Point[][] map){
        
        boolean flag = true;
        while(flag){
            
            flag = false;
            for(int y = 0; y < map.length;y++){
                for(int x = 0; x < map.length;x++){
                    if(map[y][x].biome == Biome.SEA && isLandlocked(map,x,y)){map[y][x].biome = Biome.COAST;flag = true;}
                    else if(map[y][x].isLand() && isSealocked(map,x,y)){map[y][x].biome = Biome.SEA;flag = true;}
                }
            }
        }
        return map;
    }
    
    //checks if a certain point is surrounded by at least 5 bits of land
    private boolean isLandlocked(Point[][] map,int x, int y){
        int count = 0;
        for(int i = -1; i < 2;i++){
            for(int j = -1;j<2;j++){
                int nx = x+j,ny = y+i;
                if((i==0 && j==0) ||nx<0||ny<0||nx>=map[0].length||ny>=map.length);
                else if(map[ny][nx].isLand())count++;
            }
        }
        return count>4;
    }
    
    //checks if a certain point is surrounded by surrounded by at least 5 bits of sea. Sets pixel to coast if it is adjacent to
    //at least one sea
    private boolean isSealocked(Point[][] map, int x,int y){
        int count = 0;
        for(int i = -1; i < 2;i++){
            for(int j = -1;j<2;j++){
                int nx = x+j,ny = y+i;
                if((i==0 && j==0) ||nx<0||ny<0||nx>=map[0].length||ny>=map.length);
                else if(map[ny][nx].biome == Biome.SEA)count++;
                
            }
        }
        return count>4;
    }
    
    
}
