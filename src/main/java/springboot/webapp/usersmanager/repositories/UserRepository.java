package springboot.webapp.usersmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import springboot.webapp.usersmanager.entities.ERole;
import springboot.webapp.usersmanager.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    @Modifying
    @Query("update User u set u.name = ?1, u.surname = ?2, u.email =?3, u.role = ?4 where u.id = ?5")
    void setUserInfoById(String name, String surname, String email, ERole role, Integer userId);
}
