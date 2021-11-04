package springboot.webapp.usersmanager.Services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user, int userId) {
        user.setId(userId);
        userRepository.save(user);
    }

    @Override
    public User get(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean notExistsByEmail(String email) {
        return !userRepository.existsByEmail(email);
    }
}
