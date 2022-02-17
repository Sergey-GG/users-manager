package springboot.webapp.usersmanager.repositories;

import springboot.webapp.usersmanager.entities.Polygon;

import java.util.List;
import java.util.Optional;

public interface PolygonRepository {
    boolean doesExistById(long id);

    Integer deleteById(long id);

    int put(Polygon polygon);

    List<Polygon> findAll();

    Optional<Polygon> findById(long id);
}
