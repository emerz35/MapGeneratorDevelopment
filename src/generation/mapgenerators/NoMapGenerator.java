package generation.mapgenerators;

import generation.MapGenerator;

import map.Map;

/**
 *
 * @author Tuesday
 */
public class NoMapGenerator extends MapGenerator{

    public NoMapGenerator() {
        super(null, null, null, null, null, null,null);
    }

    @Override
    public Map generateMap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
