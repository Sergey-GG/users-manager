package springboot.webapp.usersmanager.services_tests;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import springboot.webapp.usersmanager.PolygonGenerator;
import springboot.webapp.usersmanager.entities.Polygon;
import springboot.webapp.usersmanager.repositories.PolygonRepository;
import springboot.webapp.usersmanager.services.PolygonService;
import springboot.webapp.usersmanager.services.PolygonServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

class PolygonServiceTest {
    private final PolygonRepository polygonRepository = Mockito.mock(PolygonRepository.class);

    private final PolygonService polygonService = new PolygonServiceImpl(polygonRepository);

    @Test
    public void getAll() {
        Polygon polygon1 = PolygonGenerator.getPolygon();

        Polygon polygon2 = PolygonGenerator.getPolygon();

        Polygon polygon3 = PolygonGenerator.getPolygon();

        List<Polygon> polygons = List.of(polygon1, polygon2, polygon3);

        when(polygonRepository.findAll()).thenReturn(polygons);

        List<Polygon> responseList = polygonService.getAll();

        MatcherAssert.assertThat(polygons.size(), is(3));
        MatcherAssert.assertThat(responseList, containsInAnyOrder(polygon1, polygon2, polygon3));
    }


    @Test
    public void getById() {
        final Polygon polygon = PolygonGenerator.getPolygon();

        final Optional<Polygon> optionalUser = Optional.of(polygon);

        when(polygonRepository.findById(polygon.getId())).thenReturn(optionalUser);

        Optional<Polygon> responseUser = polygonService.get(optionalUser.get().getId());

        MatcherAssert.assertThat(responseUser.isPresent(), is(true));
        MatcherAssert.assertThat(responseUser.get(), is(optionalUser.get()));

    }



    @Test
    public void deleteWhenExistedPolygonResultIsTrue() {
        final Polygon polygon = PolygonGenerator.getPolygon();

        when(polygonRepository.deleteById(polygon.getId())).thenReturn(1);

        MatcherAssert.assertThat(polygonService.delete(polygon.getId()), is(true));

    }


    @Test
    public void deleteByIdWhenNonExistentPolygonResultIsFalse() {
        final Polygon polygon = PolygonGenerator.getPolygon();

        when(polygonRepository.deleteById(polygon.getId())).thenReturn(0);

        MatcherAssert.assertThat(polygonService.delete(polygon.getId()), is(false));
    }
}