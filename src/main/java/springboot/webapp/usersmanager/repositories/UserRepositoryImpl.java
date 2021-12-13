package springboot.webapp.usersmanager.repositories;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.generated_sources.tables.Users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


@Repository
public class UserRepositoryImpl implements UserRepository {
    String userName = "postgres";
    String password = "eshop";
    String url = "jdbc:postgresql://localhost:5432/users_manager_db";
    Connection conn;

    {
        try {
            conn = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);


    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public Integer deleteUserById(int id) {
        return  context.deleteFrom(Users.USERS)
                .where(Users.USERS.ID.eq(id))
                .execute();
    }

    @Override
    public User save(User user) {
        return context.insertInto(Users.USERS)
                .values(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getRole())
                .returning()
                .fetchOne()
                .into(User.class);
    }

    @Override
    public List<User> findAll() {
        return context.select()
                        .from(Users.USERS)
                        .fetch()
                        .into(User.class);

    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.empty();
    }
}
