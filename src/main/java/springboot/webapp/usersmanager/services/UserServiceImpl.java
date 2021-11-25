package springboot.webapp.usersmanager.services;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> get(int id) {
        return userRepository.findById(id);
    }

    @Override
    public User put(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new IllegalArgumentException();
        userRepository.save(user);
        return user;
    }

    @Override
    public boolean deleteWithThrowingException(int id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public long deleteWithCount(int id) {
        long before = userRepository.count();
        try {
            userRepository.deleteById(id);
            return before - userRepository.count();
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
}
