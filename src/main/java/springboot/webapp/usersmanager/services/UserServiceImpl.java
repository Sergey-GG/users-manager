package springboot.webapp.usersmanager.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.repositories.UserRepository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> get(int id) {
        return userRepository.findById(id);
    }


    @Override
    public boolean save(User user) {
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
