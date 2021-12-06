package springboot.webapp.usersmanager.controllers_tests;

import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import springboot.webapp.usersmanager.controllers.UserController;
import springboot.webapp.usersmanager.entities.Role;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

//    @Autowired
//    private MockMvc mockMvc;

//    @Autowired
//    private ObjectMapper mapper;
//
//    @MockBean
//    private UserService userService;

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;


    @Test
    @SneakyThrows
    public void getAllWhenListIsNotEmptyStatusOk() {
        List<User> users = List.of(getUser(), getUser(), getUser());

        when(userService.getAll()).thenReturn(users);

//        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();

        ResponseEntity<List<User>> response = userController.getAll();

        MatcherAssert.assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getBody(), is(notNullValue()));
        MatcherAssert.assertThat(response.getBody(), is(not(empty())));
        MatcherAssert.assertThat(response.getBody().toString(), is(not("[]")));
    }

    @Test
    @SneakyThrows
    public void getAllWhenListIsEmptyStatusOk() {
        List<User> users = List.of();

        when(userService.getAll()).thenReturn(users);

//        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/users")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();

        ResponseEntity<List<User>> response = userController.getAll();

        MatcherAssert.assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getBody(), is(notNullValue()));
        MatcherAssert.assertThat(response.getBody().toString(), is("[]"));
    }


    @Test
    @SneakyThrows
    public void getByIdWhenExistedUserStatusOk() {
        final User user = getUser();

        when(userService.get(user.getId())).thenReturn(Optional.of(user));

//        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/users/" + user.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();

        ResponseEntity<User> response = userController.get(user.getId());

        MatcherAssert.assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getBody(), is(notNullValue()));
    }

    @Test
    @SneakyThrows
    public void getByIdWhenNonExistentUserStatusNotFound() {
        when(userService.get(1)).thenReturn(Optional.empty());

//        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/users/2")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();

        ResponseEntity<User> response = userController.get(1);

        MatcherAssert.assertThat(response.getStatusCodeValue(), is(HttpStatus.NOT_FOUND.value()));
        MatcherAssert.assertThat(response.getBody(), is(nullValue()));
    }

    @Test
    @SneakyThrows
    public void putWhenNonExistentUserStatusOk() {
        final User user = getUser();

        when(userService.put(user)).thenReturn(true);

//        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
//                        .put("/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(this.mapper.writeValueAsString(user)))
//                .andReturn()
//                .getResponse();

        ResponseEntity<Boolean> response = userController.put(user);

        MatcherAssert.assertThat(response.getStatusCodeValue(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getBody(), is(notNullValue()));
        MatcherAssert.assertThat(response.getBody().toString(), is("true"));
    }

    @Test
    @SneakyThrows
    public void putWhenUserWithExistedEmailStatusConflict() {
        when(userService.put(any())).thenThrow(IllegalArgumentException.class);

//        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
//                        .put("/users")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(this.mapper.writeValueAsString(getUser()))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();

        ResponseEntity<Boolean> response = userController.put(any());

        MatcherAssert.assertThat(response.getStatusCodeValue(), is(HttpStatus.CONFLICT.value()));
        MatcherAssert.assertThat(response.getBody(), is(notNullValue()));
        MatcherAssert.assertThat(response.getBody().toString(), is("false"));
    }

    @Test
    @SneakyThrows
    public void deleteWhenExistedUserStatusOk() {
        final User user = getUser();

        when(userService.delete(user.getId())).thenReturn(true);

//        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/users/" + user.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();

        ResponseEntity<Void> response = userController.delete(user.getId());

        MatcherAssert.assertThat(response.getStatusCodeValue(), is(equalTo(HttpStatus.OK.value())));
        MatcherAssert.assertThat(response.getBody(), is(nullValue()));
    }

    @Test
    @SneakyThrows
    public void deleteByIdWhenNonExistentUserStatusNotFound() {
        when(userService.delete(7)).thenReturn(false);

//        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/users/7")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andReturn()
//                .getResponse();

        ResponseEntity<Void> response = userController.delete(7);

        MatcherAssert.assertThat(response.getStatusCodeValue(), is(HttpStatus.NOT_FOUND.value()));
        MatcherAssert.assertThat(response.getBody(), is(nullValue()));
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