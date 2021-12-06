package springboot.webapp.usersmanager.services_tests;

import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import springboot.webapp.usersmanager.entities.Role;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.repositories.UserRepository;
import springboot.webapp.usersmanager.services.UserServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    @SneakyThrows
    public void getAllWhenListIsNotEmpty() {
        List<User> users = List.of(getUser(), getUser(), getUser());

        when(userRepository.findAll()).thenReturn(users);

        List<User> responseList = userService.getAll();

        MatcherAssert.assertThat(responseList, is(notNullValue()));
        MatcherAssert.assertThat(responseList, is(not(emptyIterable())));


    }

    @Test
    @SneakyThrows
    public void getAllWhenListIsEmpty() {
        List<User> users = List.of();

        when(userRepository.findAll()).thenReturn(users);

        List<User> responseList = userService.getAll();

        MatcherAssert.assertThat(responseList, is(notNullValue()));
        MatcherAssert.assertThat(responseList, is(emptyIterable()));
    }

    @Test
    @SneakyThrows
    public void getByIdWhenExistedUser() {
        final User user = getUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<User> responseUser = userService.get(user.getId());

        MatcherAssert.assertThat(responseUser, is(notNullValue()));
        MatcherAssert.assertThat(responseUser, is(not(Optional.empty())));

    }

    @Test
    @SneakyThrows
    public void getByIdWhenNonExistentUser() {

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Optional<User> responseUser = userService.get(1);

        MatcherAssert.assertThat(responseUser, is(notNullValue()));
        MatcherAssert.assertThat(responseUser, is(Optional.empty()));
    }


    @Test
    @SneakyThrows
    public void putWhenNonExistentUser() {
        final User user = getUser();

        when(userRepository.save(user)).thenReturn(user);

        boolean responseBool = userService.put(user);

        MatcherAssert.assertThat(responseBool, is(notNullValue()));
        MatcherAssert.assertThat(responseBool, is(true));
    }


    @Test
    @SneakyThrows
    public void putWhenUserWithExistedEmail() {
        final User user = getUser();

        when(userRepository.save(user)).thenReturn(null);

        boolean responseBool = userService.put(user);

        MatcherAssert.assertThat(responseBool, is(notNullValue()));
        MatcherAssert.assertThat(responseBool, is(false));
    }


    @Test
    @SneakyThrows
    public void deleteWhenExistedUser() {
        final User user = getUser();

        when(userRepository.deleteUserById(user.getId())).thenReturn(1);

        boolean responseBool = userService.delete(user.getId());

        MatcherAssert.assertThat(responseBool, is(notNullValue()));
        MatcherAssert.assertThat(responseBool, is(true));

    }


    @Test
    @SneakyThrows
    public void deleteByIdWhenNonExistent() {
        final User user = getUser();

        when(userRepository.deleteUserById(user.getId())).thenReturn(0);

        boolean responseBool = userService.delete(user.getId());

        MatcherAssert.assertThat(responseBool, is(notNullValue()));
        MatcherAssert.assertThat(responseBool, is(false));
    }

    public User getUser() {
        return new User(
                new Random().nextInt(100),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                Role.values()[new Random().nextInt(Role.values().length)]
        );
    }

}
