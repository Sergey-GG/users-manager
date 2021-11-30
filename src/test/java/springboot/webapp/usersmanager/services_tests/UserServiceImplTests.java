package springboot.webapp.usersmanager.services_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springboot.webapp.usersmanager.controllers.UserController;
import springboot.webapp.usersmanager.entities.Role;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.services.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserServiceImplTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;

    User USER_1 = new User(1, "Sergey", "Gonchar", "sergey@gmail.com", Role.ROLE_ADMIN);

    User USER_2 = new User(2, "Sergey", "Danilov", "danilov@gmail.com", Role.ROLE_USER);

    User USER_3 = new User(3, "Ivan", "Petrov", "petrov@gmail.com", Role.ROLE_USER);

    @Test
    public void getAll() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(USER_1, USER_2, USER_3));

        Mockito.when(userService.getAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getById_success() throws Exception {
        Mockito.when(userService.get(USER_1.getId())).thenReturn(Optional.of(USER_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Sergey")))
                .andExpect(jsonPath("$.surname", is("Gonchar")));
    }

    @Test
    public void getById_notFound() throws Exception {
        Mockito.when(userService.get(USER_1.getId())).thenReturn(Optional.of(USER_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void put_success() throws Exception {

        Mockito.when(userService.put(put())).thenReturn(true);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(put()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", is(true)));
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void put_existedEmail() {
        userService.put(put());
        userService.put(put());

    }

    @Test
    public void delete_success() throws Exception {
        Mockito.when(userService.delete(USER_2.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteById_notFound() throws Exception {
        Mockito.when(userService.delete(6)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    public User put(){
        return User.builder()
                .id(4)
                .name("Vladislav")
                .surname("Petrov")
                .email("vladpetrov@gmail.com")
                .role(Role.ROLE_USER)
                .build();
    }
}
