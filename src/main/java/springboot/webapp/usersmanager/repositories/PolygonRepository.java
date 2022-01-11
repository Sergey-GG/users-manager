package springboot.webapp.usersmanager.repositories;

import springboot.webapp.usersmanager.entities.Polygon;
import springboot.webapp.usersmanager.entities.User;

import java.util.List;
import java.util.Optional;

public interface PolygonRepository {
    boolean existsById(long id);

    Integer deleteById(long id);

    User put(Polygon polygon);

    List<Polygon> findAll();

    Optional<Polygon> findById(long id);
}
