package springboot.webapp.usersmanager.Services;

import springboot.webapp.usersmanager.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    void save(User user);

    void update(User user, int userId);

    User get(int id);

    void delete(int id);

    boolean existsById(int id);
    boolean existsByEmail(String email);
}
