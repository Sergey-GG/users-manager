package springboot.webapp.usersmanager;

import springboot.webapp.usersmanager.CustomGeometry.*;
import springboot.webapp.usersmanager.entities.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PolygonGenerator {
    public static Polygon getPolygon() {
        List<CustomPoint> coordinates = new ArrayList<>();
        int size = new Random().nextInt(7) + 4;
        {
            int i = 0;
            while (i < size - 1) {
                coordinates.add(new CustomPoint(new Random().nextDouble() * 10, new Random().nextDouble() * 10));
                i++;
            }
            coordinates.add(coordinates.get(0));
        }

        CustomGeometry customGeometry = new CustomPolygon(List.of(coordinates));
        String geometry = customGeometry.toString().replace('[', '(');
        geometry = geometry.replace(']', ')');

        return new Polygon(
                new Random().nextInt(30),
                new WKTParser().parse(geometry).getArea(),
                geometry
        );
    }
}
