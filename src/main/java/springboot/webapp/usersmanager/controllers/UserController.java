package springboot.webapp.usersmanager.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.webapp.usersmanager.entities.User;
import springboot.webapp.usersmanager.services.UserService;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return userService.getAll().size() > 0
                ? ResponseEntity.ok(userService.getAll())
                : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        return userService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<User> put(@RequestBody User user) {
        try {
            return ResponseEntity.ok(userService.put(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        return userService.delete(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}