package springboot.webapp.usersmanager.repositories;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.locationtech.jts.io.WKTReader;
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
    public boolean existsById(long id) {
        return dslContext.fetchExists(POLYGON, POLYGON.ID.eq(id));
    }

    @Override
    public Integer deleteById(long id) {
        return dslContext.deleteFrom(POLYGON)
                .where(POLYGON.ID.eq(id))
                .execute();
    }

    @Override
    @SneakyThrows
    public Polygon put(Polygon polygon) {
        WKTReader wktReader = new WKTReader();
        if (existsById(polygon.getId())) {
            return dslContext.update(POLYGON)
                    .set(POLYGON.AREA, polygon.getArea())
                    .set(POLYGON.GEOMETRY, wktReader.read(polygon.getGeometry()))
                    .where(POLYGON.ID.eq(polygon.getId()))
                    .returning()
                    .fetchOne()
                    .into(springboot.webapp.usersmanager.entities.Polygon.class);
        }
        return dslContext.insertInto(POLYGON,
                        POLYGON.ID,
                        POLYGON.AREA,
                        POLYGON.GEOMETRY
                )
                .values(polygon.getId(),
                        polygon.getArea(),
                        wktReader.read(polygon.getGeometry())
                )
                .returning()
                .fetchOne()
                .into(springboot.webapp.usersmanager.entities.Polygon.class);
    }

    @Override
    public List<springboot.webapp.usersmanager.entities.Polygon> findAll() {
        return dslContext.select()
                .from(POLYGON)
                .fetchInto(springboot.webapp.usersmanager.entities.Polygon.class);
    }

    @Override
    public Optional<springboot.webapp.usersmanager.entities.Polygon> findById(long id) {
        return dslContext.selectFrom(POLYGON)
                .where(POLYGON.ID.eq(id))
                .fetchOptionalInto(springboot.webapp.usersmanager.entities.Polygon.class);
    }
}
