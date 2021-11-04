package springboot.webapp.usersmanager.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springboot.webapp.usersmanager.Services.UserService;
import springboot.webapp.usersmanager.entities.User;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {

    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        return userService.get(id);
    }

    @PostMapping("/users/new")
    public ResponseEntity<String> create(@RequestBody User user) {
        return userService.save(user);
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<String> update(@RequestBody User user, @PathVariable int id) {
        return userService.update(user, id);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
       return userService.delete(id);
    }
}
