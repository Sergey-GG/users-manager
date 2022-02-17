package springboot.webapp.usersmanager.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.webapp.usersmanager.entities.PolygonEntity;
import springboot.webapp.usersmanager.services.PolygonService;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/polygons")
public class PolygonController {
    private final PolygonService polygonService;

    @GetMapping
    public ResponseEntity<List<PolygonEntity>> getAll() {
        return ResponseEntity.ok(polygonService.getAll());

    }

    @GetMapping("/{id}")
    public ResponseEntity<PolygonEntity> get(@PathVariable int id) {
        return polygonService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Boolean> put(@RequestBody PolygonEntity polygon) {
        return ResponseEntity.ok(polygonService.put(polygon));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return polygonService.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
