package springboot.webapp.usersmanager.controllers_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.postgresql.util.PGobject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import springboot.webapp.usersmanager.PolygonGenerator;
import springboot.webapp.usersmanager.controllers.PolygonController;
import springboot.webapp.usersmanager.entities.Polygon;
import springboot.webapp.usersmanager.services.PolygonService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

class PolygonControllerTest {

    private final PolygonService polygonService = Mockito.mock(PolygonService.class);

    private final PolygonController polygonController = new PolygonController(polygonService);

    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(polygonController)
            .build();


    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @SneakyThrows
    void getAllWhenListIsNotEmptyStatusOk()   {
        List<Polygon> polygons =  List.of(PolygonGenerator.getPolygon(), PolygonGenerator.getPolygon(), PolygonGenerator.getPolygon());

        when(polygonService.getAll()).thenReturn(polygons);

        MockHttpServletResponse response = this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/polygons")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(mapper.writeValueAsString(polygons)));
    }

    @Test
    @SneakyThrows
    public void getByIdWhenExistedPolygonStatusOk() {
        final Polygon polygon = PolygonGenerator.getPolygon();

        when(polygonService.get(polygon.getId())).thenReturn(Optional.of(polygon));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/polygons/" + polygon.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
        MatcherAssert.assertThat(response.getContentAsString(), is(mapper.writeValueAsString(polygon)));
    }

    @Test
    @SneakyThrows
    public void getByIdWhenNonExistentPolygonStatusNotFound() {
        when(polygonService.get(1)).thenReturn(Optional.empty());

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .get("/polygons/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }

    @Test
    void put() {
    }

    @Test
    @SneakyThrows
    public void deleteWhenExistedPolygonStatusOk() {
        final Polygon polygon = PolygonGenerator.getPolygon();

        when(polygonService.delete(polygon.getId())).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/" + polygon.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();


        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.OK.value()));
    }

    @Test
    @SneakyThrows
    public void deleteByIdWhenNonExistentPolygonStatusNotFound() {
        when(polygonService.delete(7)).thenReturn(false);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        MatcherAssert.assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
    }
}