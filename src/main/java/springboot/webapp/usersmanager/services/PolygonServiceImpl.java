package springboot.webapp.usersmanager.services;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import springboot.webapp.usersmanager.entities.Polygon;
import springboot.webapp.usersmanager.repositories.PolygonRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PolygonServiceImpl implements PolygonService {
    private final PolygonRepository polygonRepository;

    @Override
    public List<Polygon> getAll() {
        return polygonRepository.findAll();
    }

    @Override
    @SneakyThrows
    public boolean put(Polygon polygon) {
        System.out.println(calcArea(polygon).toString());
        return polygonRepository.put(calcArea(polygon)) != null;
    }

    @Override
    public Optional<Polygon> get(long id) {
        return polygonRepository.findById(id);
    }

    @Override
    public boolean delete(long id) {
        return polygonRepository.deleteById(id) > 0;
    }

    @Override
    @SneakyThrows
    public Polygon calcArea(Polygon polygon) {
        WKTReader wkt = new WKTReader();
        Geometry polygon1 = wkt.read(polygon.getGeometry());
        return new Polygon(polygon.getId(), polygon1.getArea(), polygon.getGeometry());
    }
}
