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

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class UserServiceTests {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);

    private final UserService userService = new UserServiceImpl(userRepository);

    @Test
    public void getAll() {
        List<User> users = List.of(UserGenerator.getUser(), UserGenerator.getUser(), UserGenerator.getUser());

        when(userRepository.findAll()).thenReturn(users);

        List<User> responseList = userService.getAll();

        MatcherAssert.assertThat(users.size(), is(3));
        MatcherAssert.assertThat(responseList, is(users));
        for (User user : responseList) MatcherAssert.assertThat(user, instanceOf(User.class));
    }


    @Test
    public void getById() {
        final Optional<User> user = Optional.of(UserGenerator.getUser());

        when(userRepository.findById(user.get().getId())).thenReturn(user);

        Optional<User> responseUser = userService.get(user.get().getId());

        MatcherAssert.assertThat(responseUser.get(), is(user.get()));

    }


    @Test
    public void putWhenNonExistentUserResultIsTrue() {
        final User user = UserGenerator.getUser();

        when(userRepository.save(user)).thenReturn(user);

        boolean responseBool = userService.put(user);

        MatcherAssert.assertThat(responseBool, is(true));
    }


    @Test
    public void putWhenUserWithExistedEmailResultIsFalse() {
        final User user = UserGenerator.getUser();

        when(userRepository.save(user)).thenReturn(null);

        boolean responseBool = userService.put(user);

        MatcherAssert.assertThat(responseBool, is(false));
    }


    @Test
    public void deleteWhenExistedUserResultIsTrue() {
        final User user = UserGenerator.getUser();

        when(userRepository.deleteUserById(user.getId())).thenReturn(1);

        boolean responseBool = userService.delete(user.getId());

        MatcherAssert.assertThat(responseBool, is(true));

    }


    @Test
    public void deleteByIdWhenNonExistentUserResultIsFalse() {
        final User user = UserGenerator.getUser();

        when(userRepository.deleteUserById(user.getId())).thenReturn(0);

        boolean responseBool = userService.delete(user.getId());

        MatcherAssert.assertThat(responseBool, is(false));
    }


}
