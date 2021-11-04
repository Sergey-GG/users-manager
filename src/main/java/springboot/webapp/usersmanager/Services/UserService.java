package springboot.webapp.usersmanager.Services;

import org.springframework.stereotype.Service;
import springboot.webapp.usersmanager.entities.User;

import java.util.List;

@Service
public interface UserService {
    List<User> getAll();

    void save(User user);

    void update(User user, int userId);

    User get(int id);

    void delete(int id);

    boolean existsById(int id);
    boolean existsByEmail(String email);
}
