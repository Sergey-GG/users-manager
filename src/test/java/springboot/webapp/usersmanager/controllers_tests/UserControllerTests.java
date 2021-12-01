package springboot.webapp.usersmanager.controllers_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;


    @Test
    @SneakyThrows
    public void getAllWhenListIsNotEmptyStatusOk() {
        List<User> users = List.of(getUser(), getUser(), getUser());

        when(userService.getAll()).thenReturn(users);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(notNullValue()));
        MatcherAssert.assertThat(response.getContentAsString(), is(not(emptyString())));
        MatcherAssert.assertThat(response.getContentAsString(), is(not("[]")));
    }

    @Test
    @SneakyThrows
    public void getAllWhenListIsEmptyStatusOk() {
        List<User> users = List.of();

        when(userService.getAll()).thenReturn(users);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(notNullValue()));
        MatcherAssert.assertThat(response.getContentAsString(), is("[]"));
    }


    @Test
    @SneakyThrows
    public void getByIdWhenExistedUserStatusOk() {
        final User user = getUser();

        when(userService.get(user.getId())).thenReturn(Optional.of(user));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(notNullValue()));
        MatcherAssert.assertThat(response.getContentAsString(), is(not(emptyString())));
    }

    @Test
    @SneakyThrows
    public void getByIdWhenNonExistentUserStatusNotFound() {
        when(userService.get(1)).thenReturn(Optional.empty());

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(notNullValue()));
        MatcherAssert.assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    @SneakyThrows
    public void putWhenNonExistentUserStatusOk() {
        final User user = getUser();

        when(userService.put(user)).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(user)))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(notNullValue()));
        MatcherAssert.assertThat(response.getContentAsString(), is("true"));
    }

    @Test
    @SneakyThrows
    public void putWhenUserWithExistedEmailStatusConflict() {
        when(userService.put(any())).thenThrow(IllegalArgumentException.class);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(getUser()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.CONFLICT.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(notNullValue()));
        MatcherAssert.assertThat(response.getContentAsString(), is("false"));
    }

    @Test
    @SneakyThrows
    public void deleteWhenExistedUserStatusOk() {
        final User user = getUser();

        when(userService.delete(user.getId())).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(equalTo(HttpStatus.OK.value())));
        MatcherAssert.assertThat(response.getContentAsString(), is(notNullValue()));
        MatcherAssert.assertThat(response.getContentAsString(), is(emptyString()));
    }

    @Test
    @SneakyThrows
    public void deleteByIdWhenNonExistentUserStatusNotFound() {
        when(userService.delete(7)).thenReturn(false);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(notNullValue()));
        MatcherAssert.assertThat(response.getContentAsString(), is(emptyString()));
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