package springboot.webapp.usersmanager.controllers_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springboot.webapp.usersmanager.UserGenerator;
import springboot.webapp.usersmanager.controllers.UserController;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.services.UserService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class UserControllerTests {
    private final UserService userService = Mockito.mock(UserService.class);

    private final UserController userController = new UserController(userService);

    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(userController)
            .build();


    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    @SneakyThrows
    public void getAllWhenListIsNotEmptyStatusOk() throws NullPointerException {
        List<User> users = List.of(UserGenerator.getUser(), UserGenerator.getUser(), UserGenerator.getUser());

        when(userService.getAll()).thenReturn(users);

        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(mapper.writeValueAsString(users)));
    }


    @Test
    @SneakyThrows
    public void getByIdWhenExistedUserStatusOk() {
        final User user = UserGenerator.getUser();

        when(userService.get(user.getId())).thenReturn(Optional.of(user));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(mapper.writeValueAsString(user)));
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
            }

    @Test
    @SneakyThrows
    public void putWhenNonExistentUserStatusOk() {
        final User user = UserGenerator.getUser();

        when(userService.put(user)).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andReturn()
                .getResponse();


        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
            }

    @Test
    @SneakyThrows
    public void putWhenUserWithExistedEmailStatusConflict() {
        when(userService.put(any())).thenThrow(IllegalArgumentException.class);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .put("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(UserGenerator.getUser()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.CONFLICT.value()));
    }

    @Test
    @SneakyThrows
    public void deleteWhenExistedUserStatusOk() {
        final User user = UserGenerator.getUser();

        when(userService.delete(user.getId())).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();


        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
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
    }
}