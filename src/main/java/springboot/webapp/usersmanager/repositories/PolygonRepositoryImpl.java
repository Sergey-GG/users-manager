package springboot.webapp.usersmanager.repositories;

import lombok.AllArgsConstructor;
import org.jooq.*;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.Polygon;

import java.util.List;
import java.util.Optional;

import static springboot.webapp.usersmanager.generated_sources.jooq.Tables.POLYGON;

@Repository
@AllArgsConstructor
public class PolygonRepositoryImpl implements PolygonRepository {

    private final DSLContext dslContext;

    @Override
    public boolean doesExistById(long id) {
        return dslContext.fetchExists(POLYGON, POLYGON.ID.eq(id));
    }

    @Override
    public Integer deleteById(long id) {
        return dslContext.deleteFrom(POLYGON)
                .where(POLYGON.ID.eq(id))
                .execute();
    }


    @Override
    public int put(Polygon polygon) {
        if (doesExistById(polygon.getId())) {
            return dslContext.query(
                    "UPDATE polygon SET area = ST_Area('"+ polygon.getGeometry() + "')," +
                            " geometry = ST_GeomFromText('" + polygon.getGeometry() +
                            "') where id ='" + polygon.getId() + "';").execute();


        }
        return dslContext.query(
                "INSERT INTO polygon VALUES('" + polygon.getId() +
                        "', ST_Area('"+ polygon.getGeometry() + "') "+
                        ", ST_GeomFromText('" +
                        polygon.getGeometry() +
                        "'))").execute();
    }

    @Override
    public List<springboot.webapp.usersmanager.entities.Polygon> findAll() {
        return dslContext.resultQuery("SELECT id, area, ST_AsText(geometry) FROM polygon")
                .fetchInto(springboot.webapp.usersmanager.entities.Polygon.class);
    }

    @Override
    public Optional<springboot.webapp.usersmanager.entities.Polygon> findById(long id) {
        return dslContext
                .resultQuery("SELECT id, area, ST_AsText(geometry) FROM polygon WHERE id = '" + id + "'")
                .fetchOptionalInto(springboot.webapp.usersmanager.entities.Polygon.class);

    }


}