package springboot.webapp.usersmanager.repositories;

import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.generated_sources.jooq.tables.Users;

import java.util.List;
import java.util.Optional;


@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext context;

    @Override
    public boolean existsByEmail(String email) {
        return context.fetchExists(Users.USERS, Users.USERS.EMAIL.eq(email));
    }

    @Override
    public boolean existsById(int id) {
        return context.fetchExists(Users.USERS, Users.USERS.ID.eq(id));
    }

    @Override
    public Integer deleteUserById(int id) {
        return context.deleteFrom(Users.USERS)
                .where(Users.USERS.ID.eq(id))
                .execute();
    }

    @Override
    public User put(User user) {
        if (existsById(user.getId())) {
            return context.update(Users.USERS)
                    .set(Users.USERS.NAME, user.getName())
                    .set(Users.USERS.SURNAME, user.getSurname())
                    .set(Users.USERS.EMAIL, user.getEmail())
                    .set(Users.USERS.ROLE, user.getRole().toString())
                    .where(Users.USERS.ID.eq(user.getId()))
                    .returning()
                    .fetchOne()
                    .into(User.class);
        }
        return context.insertInto(Users.USERS)
                .values(
                        user.getId(),
                        user.getName(),
                        user.getSurname(),
                        user.getEmail(),
                        user.getRole()
                )
                .returning()
                .fetchOne()
                .into(User.class);
    }

    @Override
    public List<User> findAll() {
        return context.select()
                .from(Users.USERS)
                .fetchInto(User.class);

    }

    @Override
    public Optional<User> findById(int id) {
        return context.selectFrom(Users.USERS)
                .where(Users.USERS.ID.eq(id))
                .fetchOptionalInto(User.class);
    }
}
