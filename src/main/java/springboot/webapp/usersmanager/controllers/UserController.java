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
        return userService.getAll()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        return userService.get(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Void> save(@RequestBody User user) {
//        return ResponseEntity.ok(userService.save(user));

//        return ResponseEntity.ok().build();

//        if (userService.getByEmail(user.getEmail()).isPresent())
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
//        return ResponseEntity.ok().body(userService.save(user));

//        if (userService.getByEmail(user.getEmail()).isEmpty())
//            return ResponseEntity.ok().body(userService.save(user));
//        return ResponseEntity.status(HttpStatus.CONFLICT).body(false);

//        userService.getByEmail(user.getEmail())
//                .map(ResponseEntity.status(HttpStatus.CONFLICT).body(false))
//                .orElseGet(return ResponseEntity.ok().body(userService.save(user));

//        return userService.getEmptyIfEmailIsAlreadyExists(user.getEmail())
//                .map((user1) -> ResponseEntity.ok(userService.save(user1)))
//                .orElse(ResponseEntity.notFound().build());

        if (userService.save(user))
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable int id) {
        return userService.get(id)
                .map(user -> ResponseEntity.ok().body(userService.delete(user)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(false));
    }
}
