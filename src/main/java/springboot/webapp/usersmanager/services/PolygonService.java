package springboot.webapp.usersmanager.services;

import springboot.webapp.usersmanager.entities.PolygonEntity;

import java.util.List;
import java.util.Optional;

public interface PolygonService {
    List<PolygonEntity> getAll();

    boolean put(PolygonEntity polygon);

    Optional<PolygonEntity> get(long id);

    boolean delete(long id);

}
