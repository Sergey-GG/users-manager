package springboot.webapp.usersmanager.services;

import springboot.webapp.usersmanager.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    boolean put(User user);

    Optional<User> get(int id);

    boolean delete(int id);
}
