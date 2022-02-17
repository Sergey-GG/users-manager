package springboot.webapp.usersmanager.services;

import lombok.AllArgsConstructor;
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
        return polygonRepository.put(polygon) != 0;
    }

    @Override
    public Optional<Polygon> get(long id) {
        return polygonRepository.findById(id);
    }

    @Override
    public boolean delete(long id) {
        return polygonRepository.deleteById(id) > 0;
    }


}
