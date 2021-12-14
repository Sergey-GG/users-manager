package springboot.webapp.usersmanager.services_tests;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import springboot.webapp.usersmanager.UserGenerator;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.repositories.UserRepository;
import springboot.webapp.usersmanager.services.UserService;
import springboot.webapp.usersmanager.services.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

public class UserServiceTests {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    
    private final UserService userService = new UserServiceImpl(userRepository);

    @Test
    public void getAll() {
        User user1 = UserGenerator.getUser();

        User user2 = UserGenerator.getUser();

        User user3 = UserGenerator.getUser();

        List<User> users = List.of(user1, user2, user3);

        when(userRepository.findAll()).thenReturn(users);

        List<User> responseList = userService.getAll();

        MatcherAssert.assertThat(users.size(), is(3));
        MatcherAssert.assertThat(responseList, containsInAnyOrder(user1, user2, user3));
    }


    @Test
    public void getById() {
        final User user = UserGenerator.getUser();

        final Optional<User> optionalUser = Optional.of(user);

        when(userRepository.findById(user.getId())).thenReturn(optionalUser);

        Optional<User> responseUser = userService.get(optionalUser.get().getId());

        MatcherAssert.assertThat(responseUser.isPresent(), is(true));
        MatcherAssert.assertThat(responseUser.get(), is(optionalUser.get()));
    }


    @Test
    public void putWhenNonExistentUserResultIsTrue() {
        final User user = UserGenerator.getUser();

        when(userRepository.save(user)).thenReturn(user);

        MatcherAssert.assertThat(userService.put(user), is(true));
    }


    @Test
    public void putWhenUserWithExistedEmailResultIsFalse() {
        final User user = UserGenerator.getUser();

        when(userRepository.save(user)).thenReturn(null);

        MatcherAssert.assertThat(userService.put(user), is(false));
    }


    @Test
    public void deleteWhenExistedUserResultIsTrue() {
        final User user = UserGenerator.getUser();

        when(userRepository.deleteUserById(user.getId())).thenReturn(1);

        MatcherAssert.assertThat(userService.delete(user.getId()), is(true));

    }


    @Test
    public void deleteByIdWhenNonExistentUserResultIsFalse() {
        final User user = UserGenerator.getUser();

        when(userRepository.deleteUserById(user.getId())).thenReturn(0);

        MatcherAssert.assertThat(userService.delete(user.getId()), is(false));
        
    }
}
