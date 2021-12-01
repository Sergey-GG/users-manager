package springboot.webapp.usersmanager.controllers_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springboot.webapp.usersmanager.controllers.UserController;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.services.UserService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;

    private final User user1 = getUser();

    private final User user2 = getUser();

    private final User user3 = getUser();

    @Test
    public void getAllWhenStatusOk() throws Exception {
        List<User> users = List.of(user1, user2, user3);

        when(userService.getAll()).thenReturn(users);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/users")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();


        MockHttpServletResponse response = result.getResponse();
        MatcherAssert.assertThat(response.getStatus(), is(equalTo(HttpStatus.OK.value())));
    }

    @Test
    public void getByIdWhenExistedUserStatusOk() throws Exception {
        when(userService.get(user1.getId())).thenReturn(Optional.of(user1));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/users/" + user1.getId())
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse response = result.getResponse();
        MatcherAssert.assertThat(response.getStatus(), is(equalTo(HttpStatus.OK.value())));
    }

    @Test
    public void getByIdWhenNonExistentUserStatusNotFound() throws Exception {
        when(userService.get(1)).thenReturn(Optional.empty());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/users/2")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse response = result.getResponse();
        MatcherAssert.assertThat(response.getStatus(), is(equalTo(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    public void putWhenNonExistentUserStatusOk() throws Exception {

        when(userService.put(getUser())).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(getUser()))).andReturn();

        MockHttpServletResponse response = result.getResponse();
        MatcherAssert.assertThat(response.getStatus(), is(equalTo(HttpStatus.OK.value())));
    }

    @Test
    public void putWhenUserWithExistedEmailStatusConflict() throws Exception {
        when(userService.put(any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put("/users")
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(getUser()))
                .contentType(MediaType.APPLICATION_JSON)).andReturn();

        MockHttpServletResponse response = result.getResponse();
        MatcherAssert.assertThat(response.getStatus(), is(equalTo(HttpStatus.CONFLICT.value())));
    }

    @Test
    public void deleteWhenExistedUserStatusOk() throws Exception {
        when(userService.delete(user2.getId())).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/" + user2.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        MatcherAssert.assertThat(response.getStatus(), is(equalTo(HttpStatus.OK.value())));
    }

    @Test
    public void deleteByIdWhenNonExistentUserStatusNotFound() throws Exception {
        when(userService.delete(6)).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        MatcherAssert.assertThat(response.getStatus(), is(equalTo(HttpStatus.NOT_FOUND.value())));
    }

    public User getUser() {
        PodamFactory factory = new PodamFactoryImpl();
        return factory.manufacturePojo(User.class);
    }
}
