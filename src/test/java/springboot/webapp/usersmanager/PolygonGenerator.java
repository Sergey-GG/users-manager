package springboot.webapp.usersmanager;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
import springboot.webapp.usersmanager.entities.Polygon;

import java.util.Random;

public class PolygonGenerator {
    public static Polygon getPolygon() {
        CoordinateXY[] coordinates = new CoordinateXY[new Random().nextInt(7) + 4];
       {
            int i = 0;
            while (i < coordinates.length - 1) {
                coordinates[i].x = new Random().nextDouble() * 10;
                coordinates[i].y = new Random().nextDouble() * 10;
                i++;
            }
            coordinates[i] = coordinates[0];
        }

        Geometry geometry = new org.locationtech.jts.geom.Polygon(
                new LinearRing(new CoordinateArraySequence(coordinates), new GeometryFactory()),
                new LinearRing[0],
                new GeometryFactory());

        return new Polygon(
                new Random().nextInt(30),
                geometry.getArea(),
                geometry.toString()
        );
    }
}
