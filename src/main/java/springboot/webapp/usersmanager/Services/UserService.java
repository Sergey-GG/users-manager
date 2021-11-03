package springboot.webapp.usersmanager.Services;

import org.springframework.stereotype.Service;
import springboot.webapp.usersmanager.entities.ERole;
import springboot.webapp.usersmanager.entities.User;

import java.util.List;

@Service
public interface UserService {
    List<User> getUsers();

    void saveUser(User user);

    void updateUser(String name, String surname, String email, ERole role, Integer userId);

    User getUser(int id);

    void deleteUser(int id);
}
