package springboot.webapp.usersmanager.repositories;

import springboot.webapp.usersmanager.entities.PolygonEntity;

import java.util.List;
import java.util.Optional;

public interface PolygonRepository {
    boolean doesExistById(long id);

    Integer deleteById(long id);

    int put(PolygonEntity polygon);

    List<PolygonEntity> findAll();

    Optional<PolygonEntity> findById(long id);
}
