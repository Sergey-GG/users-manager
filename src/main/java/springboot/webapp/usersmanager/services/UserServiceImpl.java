package springboot.webapp.usersmanager.services;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.repositories.UserRepository;


import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<?> get(int id) {
        if (userRepository.existsById(id)) {
            return ResponseEntity.ok(userRepository.findById(id));
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with id:" + id + "is missing.");
    }

    @Override
    public ResponseEntity<String> save(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User with id: " + user.getId() + " has been successfully created or updated.");
        } else
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The user with email " + user.getEmail() + " is already created.");
    }

    @Override
    @Transactional
    public ResponseEntity<String> update(User user) {
        if (userRepository.existsById(user.getId())) {
            return save(user);
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't update the user with id:" + user.getId() + " because he is missing.");
    }


    @Override
    @Transactional
    public ResponseEntity<String> delete(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User with id: " + id + " has been deleted.");
        } else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can't delete user with id:" + id + " because he is missing.");
    }

}
