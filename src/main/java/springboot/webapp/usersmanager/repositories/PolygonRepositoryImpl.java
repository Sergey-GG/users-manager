package springboot.webapp.usersmanager.repositories;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.*;
import org.jooq.impl.SQLDataType;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.Polygon;

import java.util.List;
import java.util.Objects;
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
        if (existsById(polygon.getId())) {
            return Objects.requireNonNull(dslContext.update(POLYGON)
                            .set(POLYGON.AREA, polygon.getArea())
                            .set(POLYGON.GEOMETRY, /*stGeomFromText()*/
                                    new WKTReader(new GeometryFactory()).read(polygon.getGeometry()))
                            .where(POLYGON.ID.eq(polygon.getId()))
                            .returning()
                            .fetchOne())
                    .into(Polygon.class);
        }
        return Objects.requireNonNull(dslContext.insertInto(POLYGON,
                                POLYGON.ID,
                                POLYGON.AREA,
                                POLYGON.GEOMETRY
                        )
                        .values(polygon.getId(),
                                polygon.getArea(),
                                /*stGeomFromText()*/
                                new WKTReader(new GeometryFactory()).read(polygon.getGeometry())
                        )
                        .returning()
                        .fetchOne())
                .into(Polygon.class);
    }

    @Override
    public List<springboot.webapp.usersmanager.entities.Polygon> findAll() {
        return dslContext.select(/*stAsText()*/)
                .from(POLYGON)
                .fetchInto(springboot.webapp.usersmanager.entities.Polygon.class);
    }

    @Override
    public Optional<springboot.webapp.usersmanager.entities.Polygon> findById(long id) {

        return dslContext.selectFrom(/*stAsText()*/ POLYGON)
                .where(POLYGON.ID.eq(id))
                .fetchOptionalInto(springboot.webapp.usersmanager.entities.Polygon.class);
    }

//    public void example(org.locationtech.jts.geom.Polygon polygon, Binding binding){
//        DataType<Polygon> polygonType = SQLDataType.OTHER.asConvertedDataType(binding);
//        Field<Polygon> bindValue = dslContext.val(polygon, polygonType);
//        dslContext.condition("ST_WITHIN({0}, {1})", POLYGON.GEOMETRY, bindValue);
//    }
}