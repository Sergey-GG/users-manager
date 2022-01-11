package springboot.webapp.usersmanager.services;

import springboot.webapp.usersmanager.entities.Polygon;

import java.util.List;
import java.util.Optional;

public interface PolygonService {
    List<Polygon> getAll();

    boolean put(Polygon polygon);

    Optional<Polygon> get(long id);

    boolean delete(long id);

    Polygon calcArea(Polygon polygon);
}
