package springboot.webapp.usersmanager.controllers;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.webapp.usersmanager.entities.Polygon;
import springboot.webapp.usersmanager.services.PolygonService;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/polygons")
public class PolygonController {
    private final PolygonService polygonService;

    @GetMapping
    public ResponseEntity<List<Polygon>> getAll() {
        return ResponseEntity.ok(polygonService.getAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<Polygon> get(@PathVariable int id) {
        return polygonService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    @SneakyThrows
    public ResponseEntity<Boolean> put(@RequestBody Polygon polygon) {
        return ResponseEntity.ok(polygonService.put(polygon));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return polygonService.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
