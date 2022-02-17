package springboot.webapp.usersmanager;

import springboot.webapp.usersmanager.CustomGeometry.*;
import springboot.webapp.usersmanager.entities.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PolygonGenerator {
    public static Polygon getPolygon() {
        List<CustomPoint> coordinates = new ArrayList<>();
        final int MIN_POINTS_NUMB = 4;
        final int BOUND = 20;
        int size = new Random().nextInt(BOUND) + MIN_POINTS_NUMB;
        {
            int i = 0;
            while (i < size - 1) {
                coordinates.add(new CustomPoint(new Random().nextDouble() * 10, new Random().nextDouble() * 10));
                i++;
            }
            coordinates.add(coordinates.get(0));
        }

        CustomGeometry customGeometry = new CustomPolygon(List.of(coordinates));

        return new Polygon(
                new Random().nextInt(30),
                customGeometry.toString()
        );
    }
}
