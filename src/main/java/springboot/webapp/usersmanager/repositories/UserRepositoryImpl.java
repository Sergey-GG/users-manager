package springboot.webapp.usersmanager.repositories;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.generated_sources.jooq.tables.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final String userName = "postgres";
    private static final String password = "postgres";
    private static final String url = "jdbc:postgresql://localhost:5432/users_manager_db";
    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);


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
    public User save(User user) {
        return existsById(user.getId()) ?
                context.update(Users.USERS)
                        .set(Users.USERS.NAME, user.getName())
                        .set(Users.USERS.SURNAME, user.getSurname())
                        .set(Users.USERS.EMAIL, user.getEmail())
                        .set(Users.USERS.ROLE, user.getRole().toString())
                        .where(Users.USERS.ID.eq(user.getId()))
                        .returning()
                        .fetchOne()
                        .into(User.class)
                :
                context.insertInto(Users.USERS)
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
