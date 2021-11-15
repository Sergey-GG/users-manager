package springboot.webapp.usersmanager.services;

import org.springframework.http.ResponseEntity;
import springboot.webapp.usersmanager.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    ResponseEntity<String> save(User user);

    ResponseEntity<String> update(User user);

    ResponseEntity<?> get(int id);

    ResponseEntity<String> delete(int id);
}
