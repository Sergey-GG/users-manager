package springboot.webapp.usersmanager;

import springboot.webapp.usersmanager.entities.Role;
import springboot.webapp.usersmanager.entities.User;

import java.util.Random;
import java.util.UUID;

public class UserGenerator {
   public static User getUser() {
      Role [] values = Role.values();
      return new User(
              new Random().nextInt(100),
              UUID.randomUUID().toString(),
              UUID.randomUUID().toString(),
              UUID.randomUUID().toString(),
              values[new Random().nextInt(values.length)]
        );
    }
}
