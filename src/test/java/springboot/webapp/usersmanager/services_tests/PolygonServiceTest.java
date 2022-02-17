package springboot.webapp.usersmanager.services_tests;

import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import springboot.webapp.usersmanager.PolygonGenerator;
import springboot.webapp.usersmanager.entities.PolygonEntity;
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
        PolygonEntity polygon1 = PolygonGenerator.getPolygon();

        PolygonEntity polygon2 = PolygonGenerator.getPolygon();

        PolygonEntity polygon3 = PolygonGenerator.getPolygon();

        List<PolygonEntity> polygons = List.of(polygon1, polygon2, polygon3);

        when(polygonRepository.findAll()).thenReturn(polygons);

        List<PolygonEntity> responseList = polygonService.getAll();

        MatcherAssert.assertThat(polygons.size(), is(3));
        MatcherAssert.assertThat(responseList, containsInAnyOrder(polygon1, polygon2, polygon3));
    }


    @Test
    public void getById() {
        final PolygonEntity polygon = PolygonGenerator.getPolygon();

        final Optional<PolygonEntity> optionalPolygon = Optional.of(polygon);

        when(polygonRepository.findById(polygon.getId())).thenReturn(optionalPolygon);

        Optional<PolygonEntity> responsePolygon = polygonService.get(optionalPolygon.get().getId());

        MatcherAssert.assertThat(responsePolygon.isPresent(), is(true));
        MatcherAssert.assertThat(responsePolygon.get(), is(optionalPolygon.get()));

    }


    @Test
    public void deleteWhenExistedPolygonResultIsTrue() {
        final PolygonEntity polygon = PolygonGenerator.getPolygon();

        when(polygonRepository.deleteById(polygon.getId())).thenReturn(1);

        MatcherAssert.assertThat(polygonService.delete(polygon.getId()), is(true));

    }


    @Test
    public void deleteByIdWhenNonExistentPolygonResultIsFalse() {
        final PolygonEntity polygon = PolygonGenerator.getPolygon();

        when(polygonRepository.deleteById(polygon.getId())).thenReturn(0);

        MatcherAssert.assertThat(polygonService.delete(polygon.getId()), is(false));
    }

    @Test
    @SneakyThrows
    public void putWhenNonExistentPolygonResultIsTrue() {
        final PolygonEntity polygon = PolygonGenerator.getPolygon();

        when(polygonRepository.put(polygon)).thenReturn(1);

        MatcherAssert.assertThat(polygonService.put(polygon), is(true));
    }
}
