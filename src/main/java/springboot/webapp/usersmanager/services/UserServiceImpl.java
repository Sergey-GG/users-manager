package springboot.webapp.usersmanager.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Optional<List<User>> getAll() {
        return Optional.of(userRepository.findAll());
    }

    @Override
    public Optional<User> get(int id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean save(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            return false;
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean delete(User user) {
        userRepository.delete(user);
        return true;
    }
}
