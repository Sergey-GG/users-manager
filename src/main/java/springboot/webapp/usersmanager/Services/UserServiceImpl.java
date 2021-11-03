package springboot.webapp.usersmanager.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.webapp.usersmanager.entities.ERole;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(String name, String surname, String email, ERole role, Integer userId) {
      userRepository.setUserInfoById(name, surname, email, role, userId);
    }

    @Override
    @Transactional
    public User getUser(int id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
    userRepository.deleteById(id);
    }
}
