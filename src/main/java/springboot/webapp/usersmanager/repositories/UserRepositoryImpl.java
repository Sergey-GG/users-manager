package springboot.webapp.usersmanager.repositories;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.User;
//import springboot.webapp.usersmanager.generated_sources.jooq.tables.Users;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static springboot.webapp.usersmanager.generated_sources.jooq.tables.Users.USERS;


@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dslContext;

    @Override
    public boolean existsByEmail(String email) {
        return dslContext.fetchExists(USERS, USERS.EMAIL.eq(email));
    }

    @Override
    public boolean existsById(int id) {
        return dslContext.fetchExists(USERS, USERS.ID.eq(id));
    }

    @Override
    public Integer deleteUserById(int id) {
        return dslContext.deleteFrom(USERS)
                .where(USERS.ID.eq(id))
                .execute();
    }

    @Override
    public User put(User user) {
        if (existsById(user.getId())) {
            return Objects.requireNonNull(dslContext.update(USERS)
                            .set(USERS.NAME, user.getName())
                            .set(USERS.SURNAME, user.getSurname())
                            .set(USERS.EMAIL, user.getEmail())
                            .set(USERS.ROLE, user.getRole().toString())
                            .where(USERS.ID.eq(user.getId()))
                            .returning()
                            .fetchOne())
                    .into(User.class);
        }
        return Objects.requireNonNull(dslContext.insertInto(USERS,
                                USERS.ID,
                                USERS.NAME,
                                USERS.SURNAME,
                                USERS.EMAIL,
                                USERS.ROLE)
                        .values(user.getId(),
                                user.getName(),
                                user.getSurname(),
                                user.getEmail(),
                                user.getRole().toString())
                        .returning()
                        .fetchOne())
                .into(User.class);
    }

    @Override
    public List<User> findAll() {
        return dslContext.select()
                .from(USERS)
                .fetchInto(User.class);

    }

    @Override
    public Optional<User> findById(int id) {
        return dslContext.selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOptionalInto(User.class);
    }
}
