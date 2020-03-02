package generation.roadgenerators;

import generation.RoadGenerator;
import java.util.LinkedList;
import main.Utils;
import map.Biome;
import map.Country;
import map.Point;
import map.Settlement;
import pathfinding.Heuristic;
import pathfinding.PathFinder;

/**
 *
 * @author Charlie
 */
public class DefaultRoadGenerator implements RoadGenerator{

    private final PathFinder roadFinder = new PathFinder(new Heuristic(0.5,PathFinder.euclideanDistance), new Heuristic(3,(a,b)->(double)Utils.randInt(0,100)),new Heuristic(20,(a,b)->(double)a.altitude));
    
    @Override
    public Point[][] generate(Point[][] map) {
        LinkedList<Country> countries = new LinkedList<>();
        
        for(Point[] column:map){
            for(Point p:column){
                if(!countries.contains(p.country)&&p.country!=null)countries.add(p.country);
            }
        }
        System.out.println(countries.size());
        Settlement capital = null;
        LinkedList<Point> path = new LinkedList<>();
        for(Country c:countries){
            for(Settlement s:c.settlements){
                if(s.type == Settlement.SettlementType.CAPITAL_CITY){
                    capital = s;
                    break;
                }   
            }
            for(Settlement s:c.settlements){
                if(s.type != Settlement.SettlementType.CAPITAL_CITY) path = roadFinder.generatePath(map[capital.y][capital.x], map[s.y][s.x], map);
                path.forEach(x->{
                    if(x.biome != Biome.SETTLEMENT&&x.biome != Biome.SEA){
                        if(x.biome == Biome.RIVER) x.biome = Biome.BRIDGE;
                        else x.biome = Biome.ROAD;
                    }
                });
                
            }
        }
        return map;
    }
    
}
