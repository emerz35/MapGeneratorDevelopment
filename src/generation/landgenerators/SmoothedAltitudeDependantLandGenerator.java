package generation.landgenerators;

import generation.LandGenerator;
import gui.GUISlider;
import java.util.LinkedList;
import main.Utils;
import map.Biome;
import map.Point;

/**
 *
 * @author Tuesday
 */
public class SmoothedAltitudeDependantLandGenerator implements LandGenerator{

    private final LandFromAltitudeGenerator landGen;
    
    private static final int NOISE_ITERATIONS = 3;
    
    public SmoothedAltitudeDependantLandGenerator(GUISlider s){
        landGen = new LandFromAltitudeGenerator(s);
    }
    
    @Override
    public Point[][] generate(Point[][] map) {
        
        map = landGen.generate(map);
        
        //Noises the map NOISE_ITERATIONS times
        for(int i = 0; i < NOISE_ITERATIONS;i++)
            map = noise(map);
        
        map = seaSmoother(map);
        
        return map;
    }
    
    /**
     * Adds noise to the coastline
     * @param map The map to add noise to
     * @return The noisy map
     */
    public Point[][] noise(Point[][] map){
        LinkedList<Point> toSea = new LinkedList<>();
        for(int y = 0; y < map.length;y++){
           for(int x = 0; x < map[0].length;x++){
               if(map[y][x].isLand() && hasSeaNeighbour(map,x,y)&&Utils.R.nextBoolean())toSea.add(map[y][x]);
           }
        }
        toSea.forEach(x->x.biome = Biome.SEA);
        return map;
    }
    
    //Rounds off edges of land masses and removes small bits of sea in a land mass
    public Point[][] seaSmoother(Point[][] map){
        
        boolean flag = true;
        while(flag){
            flag = false;
            for(int y = 0; y < map.length;y++){
                for(int x = 0; x < map[0].length;x++){
                    if(map[y][x].biome == Biome.SEA && isLandlocked(map,x,y,6)){map[y][x].biome = Biome.LAND;flag = true;}
                    else if(map[y][x].isLand() && isSealocked(map,x,y,6)){map[y][x].biome = Biome.SEA;flag = true;}
                }
            }
        }
        return map;
    }
    
    //checks if a certain point is surrounded by at least num bits of land
    private boolean isLandlocked(Point[][] map,int x, int y, int num){
        int count = 0;
        for(int i = -1; i < 2;i++){
            for(int j = -1;j<2;j++){
                int nx = x+j,ny = y+i;
                if((i==0 && j==0) ||nx<0||ny<0||nx>=map[0].length||ny>=map.length);
                else if(map[ny][nx].isLand())count++;
            }
        }
        return count>=num;
        
    }
    
    //checks if a certain point is surrounded by surrounded by at least num bits of sea.
    private boolean isSealocked(Point[][] map, int x,int y,int num){
        int count = 0;
        for(int i = -1; i < 2;i++){
            for(int j = -1;j<2;j++){
                int nx = x+j,ny = y+i;
                if((i==0 && j==0) ||nx<0||ny<0||nx>=map[0].length||ny>=map.length);
                else if(map[ny][nx].biome == Biome.SEA){
                    count++;
                }
                
            }
        }
        return count>=num;
    }
    
    /**
     * Checks if the given point has at least one piece of sea next to it
     * @param map The map the point is on
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     * @return if the given point has at least one piece of sea next to it
     */
    private boolean hasSeaNeighbour(Point[][] map, int x, int y){
        for(int i = -1; i < 2;i++){
            for(int j = -1;j<2;j++){
                int nx = x+j,ny = y+i;
                if(!((i==0 && j==0) ||nx<0||ny<0||nx>=map[0].length||ny>=map.length))if(map[ny][nx].biome == Biome.SEA) return true;
            }
        }
        return false;
    }
}
