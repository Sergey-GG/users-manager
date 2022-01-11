package springboot.webapp.usersmanager;

import springboot.webapp.usersmanager.entities.Polygon;
import java.util.Random;
import java.util.UUID;

public class PolygonGenerator {
    public static Polygon getPolygon() {
        return new Polygon(
                new Random().nextLong(),
                new Random().nextDouble(),
                UUID.randomUUID().toString()
        );
    }
}
