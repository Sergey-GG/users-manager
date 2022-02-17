package springboot.webapp.usersmanager.repositories;

import lombok.AllArgsConstructor;
import org.jooq.*;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.PolygonEntity;

import java.util.List;
import java.util.Optional;

import static springboot.webapp.usersmanager.generated_sources.jooq.Tables.POLYGONENTITY;

@Repository
@AllArgsConstructor
public class PolygonRepositoryImpl implements PolygonRepository {

    private final DSLContext dslContext;

    @Override
    public boolean doesExistById(long id) {
        return dslContext.fetchExists(POLYGONENTITY, POLYGONENTITY.ID.eq(id));
    }

    @Override
    public Integer deleteById(long id) {
        return dslContext.deleteFrom(POLYGONENTITY)
                .where(POLYGONENTITY.ID.eq(id))
                .execute();
    }


    @Override
    public int put(PolygonEntity polygonEntity) {
        if (doesExistById(polygonEntity.getId())) {
            return dslContext.query(
                    "UPDATE polygon SET area = ST_Area('"+ polygonEntity.getGeometry() + "')," +
                            " geometry = ST_GeomFromText('" + polygonEntity.getGeometry() +
                            "') where id ='" + polygonEntity.getId() + "';").execute();


        }
        return dslContext.query(
                "INSERT INTO polygon VALUES('" + polygonEntity.getId() +
                        "', ST_Area('"+ polygonEntity.getGeometry() + "') "+
                        ", ST_GeomFromText('" +
                        polygonEntity.getGeometry() +
                        "'))").execute();
    }

    @Override
    public List<PolygonEntity> findAll() {
        return dslContext.resultQuery("SELECT id, area, ST_AsText(geometry) FROM polygon")
                .fetchInto(PolygonEntity.class);
    }

    @Override
    public Optional<PolygonEntity> findById(long id) {
        return dslContext
                .resultQuery("SELECT id, area, ST_AsText(geometry) FROM polygon WHERE id = '" + id + "'")
                .fetchOptionalInto(PolygonEntity.class);

    }


}