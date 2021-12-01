package springboot.webapp.usersmanager.services_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springboot.webapp.usersmanager.controllers.UserController;
import springboot.webapp.usersmanager.entities.Role;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.repositories.UserRepository;
import springboot.webapp.usersmanager.services.UserService;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserServiceTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserRepository userRepository;

    @MockBean
    UserService userService;

    User user1 = new User(1, "Sergey", "Gonchar", "sergey@gmail.com", Role.ROLE_ADMIN);

    User user2 = new User(2, "Sergey", "Danilov", "danilov@gmail.com", Role.ROLE_USER);

    User user3 = new User(3, "Ivan", "Petrov", "petrov@gmail.com", Role.ROLE_USER);

    @Test
    public void getAll_usersList_ok() throws Exception {
        List<User> users = new ArrayList<>(Arrays.asList(user1, user2, user3));

        when(userService.getAll()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void getById_existedUser_ok() throws Exception {
        when(userService.get(user1.getId())).thenReturn(Optional.of(user1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Sergey")))
                .andExpect(jsonPath("$.surname", is("Gonchar")));
    }

    @Test
    public void getById_nonExistentUser_notFound() throws Exception {
        when(userService.get(user1.getId())).thenReturn(Optional.of(user1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void put_newUser_ok() throws Exception {

        when(userService.put(putUser())).thenReturn(true);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(putUser()));

        mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void put_userWithExistedEmail_conflict() throws Exception {
        when(userService.put(any())).thenThrow(IllegalArgumentException.class);

        String content = "{\"id\" : \"4\",\"name\" : \"Vladislav\",\"surname\" : \"Petrov\",\"role\" : \"ROLE_USER\"}";

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    }

    @Test
    public void delete_existedUser_ok() throws Exception {
        when(userService.delete(user2.getId())).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteById_nonExistentUser_notFound() throws Exception {
        when(userService.delete(6)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    public User putUser() {
        return User.builder()
                .id(4)
                .name("Vladislav")
                .surname("Petrov")
                .email("vladpetrov@gmail.com")
                .role(Role.ROLE_USER)
                .build();
    }
}