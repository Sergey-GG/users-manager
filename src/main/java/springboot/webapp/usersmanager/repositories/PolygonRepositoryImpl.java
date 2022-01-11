package springboot.webapp.usersmanager.repositories;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.generated_sources.jooq.tables.Polygon;
import springboot.webapp.usersmanager.generated_sources.jooq.tables.Users;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PolygonRepositoryImpl implements PolygonRepository{
    private final DSLContext dslContext;

    @Override
    public boolean existsById(long id) {
        return dslContext.fetchExists(Polygon.POLYGON, Polygon.POLYGON.ID.eq(id));
    }

    @Override
    public Integer deleteById(long id) {
        return dslContext.deleteFrom(Polygon.POLYGON)
                .where(Polygon.POLYGON.ID.eq(id))
                .execute();
    }

    @Override
    public User put(springboot.webapp.usersmanager.entities.Polygon polygon) {
        if (existsById(polygon.getId())) {
            return dslContext.update(Users.USERS)
                    .set(Polygon.POLYGON.AREA, polygon.getArea())
                    .set(Polygon.POLYGON.GEOMETRY, polygon.getGeometry())
                    .where(Polygon.POLYGON.ID.eq(polygon.getId()))
                    .returning()
                    .fetchOne()
                    .into(User.class);
        }
        return dslContext.insertInto(Polygon.POLYGON,
                        Polygon.POLYGON.ID,
                        Polygon.POLYGON.AREA,
                        Polygon.POLYGON.GEOMETRY
                       )
                .values(polygon.getId(),
                        polygon.getArea(),
                        polygon.getGeometry()
                        )
                .returning()
                .fetchOne()
                .into(User.class);
    }

    @Override
    public List<springboot.webapp.usersmanager.entities.Polygon> findAll() {
        return dslContext.select()
                .from(Polygon.POLYGON)
                .fetchInto(springboot.webapp.usersmanager.entities.Polygon.class);
    }

    @Override
    public Optional<springboot.webapp.usersmanager.entities.Polygon> findById(long id) {
        return dslContext.selectFrom(Polygon.POLYGON)
                .where(Polygon.POLYGON.ID.eq(id))
                .fetchOptionalInto(springboot.webapp.usersmanager.entities.Polygon.class);
    }
}
