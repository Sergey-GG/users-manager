package springboot.webapp.usersmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.User;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
