package generation;

import map.Point;

public interface BiomeGenerator {

    public Point[][] generate(Point[][] map);
}
