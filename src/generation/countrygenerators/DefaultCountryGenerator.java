package generation.countrygenerators;

import generation.CountryGenerator;
import generation.namegenerators.NameGenerator;
import gui.GUISlider;
import java.util.LinkedList;
import main.Utils;
import map.Biome;
import map.Centroid;
import map.Country;
import map.Label;
import map.Map;
import map.Point;
import map.Settlement;

/**
 *
 * @author Tuesday
 */
public class DefaultCountryGenerator implements CountryGenerator{

    private static final int KERNEL_CONSTANT = 20;
    
    private final GUISlider countryNum, minAltitude;
    private final NameGenerator nameGen = new NameGenerator();
    
    public DefaultCountryGenerator(GUISlider cNum, GUISlider altiSlider){
        countryNum = cNum;
        minAltitude = altiSlider;
    }
    
    @Override
    public Point[][] generate(Point[][] map) {
        double num = countryNum.getNum();
        int count = 0;
        for(Centroid c:Map.centroids){
            if(c.altitude>minAltitude.getNum())count++;
        }
        double landDensity = (double) count / (double)Map.centroids.length;
        int countrySize = (int)(landDensity*(double)(map[0].length*map.length)/((double)num));
        String name;
        for(int j = 0; j<num;j++){
            LinkedList<Point> frontier = new LinkedList<>();
            name = nameGen.generateName();
            Country country = new Country(name,0,0,255);
            
            Centroid start = Map.centroids[Utils.randInt(0,Map.centroids.length)];
                while(!map[start.y][start.x].isLand()) start = Map.centroids[Utils.randInt(0,Map.centroids.length)];
            Point centre = map[start.y][start.x];
            Map.labels.add(new Label(name,centre.x,centre.y));
            country.settlements.add(new Settlement(Settlement.SettlementType.CAPITAL_CITY,centre.x,centre.y));
            centre.biome = Biome.SETTLEMENT;
            frontier.add(centre);
            centre.country = country;
            int spacing = Utils.randInt(1000, 10000);
            for(int i = 0; i<countrySize&&!frontier.isEmpty();i++){
                spacing--;
                Point p = frontier.remove();
                
                if(spacing<=0){
                    country.settlements.add(new Settlement(Utils.R.nextBoolean()?Settlement.SettlementType.CITY:Settlement.SettlementType.TOWN,p.x,p.y));
                    p.biome = Biome.SETTLEMENT;
                    spacing = Utils.randInt(1000, 10000);
                }
                
                if(checkPoint(p.x,p.y+1,centre,map)) {
                    insertPoint(map[p.y+1][p.x], centre, frontier);
                    map[p.y+1][p.x].country = country;
                }
                if(checkPoint(p.x,p.y-1,centre,map)) {
                    insertPoint(map[p.y-1][p.x], centre, frontier);
                    map[p.y-1][p.x].country = country;
                }
                if(checkPoint(p.x+1,p.y,centre,map)) {
                    insertPoint(map[p.y][p.x+1], centre, frontier);
                    map[p.y][p.x+1].country = country;
                }
                if(checkPoint(p.x-1,p.y,centre,map)) {
                    insertPoint(map[p.y][p.x-1], centre, frontier);
                    map[p.y][p.x-1].country = country;
                }
            }
        }
        
        for(Point[] column:map){
            for(Point p:column){
                if(checkNeighbours(p,map))p.biome = Biome.BORDER;
            }
        }
        return map;
    }
    
    private boolean checkNeighbours(Point p, Point[][] map){
        for(int i = -1;i<2;i++){
            for(int j = -1;j<2;j++){
                int ny = p.y+i,nx = p.x+j;
                if(ny>=0&&ny<map.length&&nx>=0&&nx<map[0].length&&p.country!=null&&p.isLand()){
                    if(map[ny][nx].country == null||map[ny][nx].biome == Biome.SEA)return true;
                    if(!p.country.equals(map[ny][nx].country))return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @param current
     * @param map
     * @return 
     */
    private boolean checkPoint(int x, int y, Point centre, Point[][] map){
        if(y<0||y>=map.length)return false;
        if(x<0||x>=map[y].length)return false;
        return map[y][x].country == null || !map[y][x].country.equals(centre.country)&&Utils.kernel(centre.distanceSquaredTo(x, y), KERNEL_CONSTANT)>Utils.R.nextDouble()-0.2;
    }
    
    /**
     * 
     * @param toInsert
     * @param centre
     * @param frontier 
     */
    private void insertPoint(Point toInsert, Point centre, LinkedList<Point> frontier){
        if(frontier.isEmpty()){
            frontier.add(toInsert);
            return;
        }
        for(int i = 0;i<frontier.size();i++){
            if(toInsert.biome.cost+Math.sqrt(toInsert.distanceSquaredTo(centre.x, centre.y))<frontier.get(i).biome.cost+Math.sqrt(frontier.get(i).distanceSquaredTo(centre.x, centre.y))){
                frontier.add(i,toInsert);
                return;
            }
        }
        frontier.add(toInsert);
    }
}
