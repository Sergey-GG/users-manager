package springboot.webapp.usersmanager;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import springboot.webapp.usersmanager.entities.Polygon;

import java.util.Random;

public class PolygonGenerator {
    public static Polygon getPolygon() {
        GeometryFactory geometryFactory = new GeometryFactory();

        CoordinateXY[] coordinates = new CoordinateXY[new Random().nextInt(7) + 4];
        int i;
        for (i = 0; i < coordinates.length - 1; i++) {
            CoordinateXY coordinate = new CoordinateXY();
            coordinate.x = new Random().nextDouble() * 10;
            coordinate.y = new Random().nextDouble() * 10;
            coordinates[i] = coordinate;
        }
        coordinates[i] = coordinates[0];


        CoordinateSequence coordinateSequence = new CoordinateArraySequence(coordinates);
        LinearRing shell = new LinearRing(coordinateSequence, geometryFactory);
        LinearRing[] holes = new LinearRing[0];

        return new Polygon(
                new Random().nextLong(),
                new Random().nextDouble(),
                new org.locationtech.jts.geom.Polygon(shell, holes, geometryFactory)
        );
    }
}
