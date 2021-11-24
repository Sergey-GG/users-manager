package springboot.webapp.usersmanager.services;

import springboot.webapp.usersmanager.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<List<User>> getAll();

    boolean save(User user);


    Optional<User> get(int id);

    boolean delete(User user);
}
