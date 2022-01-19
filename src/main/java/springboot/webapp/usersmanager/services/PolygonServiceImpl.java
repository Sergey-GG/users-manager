package springboot.webapp.usersmanager.services;

import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.impl.CoordinateArraySequence;
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
    public boolean put(Polygon polygon) {
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
    public Polygon calcArea(Polygon polygon) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Coordinate[] coordinates = new Coordinate[1];
        CoordinateSequence coordinateSequence = new CoordinateArraySequence(coordinates);
        LinearRing shell = new LinearRing(coordinateSequence,geometryFactory);
        LinearRing[] holes = new LinearRing[1];
        Geometry polygonCoordinates = new org.locationtech.jts.geom.Polygon(shell,holes,geometryFactory);

        double area = polygonCoordinates.getArea();
        return new Polygon(polygon.getId(), area, polygon.getGeometry());
    }
}
