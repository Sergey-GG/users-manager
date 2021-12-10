package springboot.webapp.usersmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    boolean existsByEmail(String email);

    Integer deleteUserById(int id);

    User save(User user);

    List<User> findAll();

    Optional<User> findById(int id);
}
