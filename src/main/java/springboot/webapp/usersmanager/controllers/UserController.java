package springboot.webapp.usersmanager.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> get(@PathVariable int id) {
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    }

    @PostMapping("/users/new")
    public ResponseEntity<String> create(@RequestBody User user) {
        if (!userService.existsByEmail(user.getEmail())) {
            userService.save(user);
            return new ResponseEntity<>("User has been created." + " Created user:\n" + user.getId(), HttpStatus.OK);
        } else
            return new ResponseEntity<>("The user with email " + user.getEmail() + " is already created.", HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/user/{id}")
    public ResponseEntity<String> update(@RequestBody User user, @PathVariable int id) {
        if (userService.existsById(id)) {
            if (!userService.existsByEmail(user.getEmail())) {
                userService.update(user, id);
                return new ResponseEntity<>("User with id:" + id + " has been successfully updated.", HttpStatus.OK);
            } else return new ResponseEntity<>("The user with email " + user.getEmail() + " is already created.", HttpStatus.BAD_REQUEST);
        } else
            return new ResponseEntity<>("Can't update the user with id:" + id + " because he is missing.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (userService.existsById(id)) {
            userService.delete(id);
            return new ResponseEntity<>("User has been deleted.", HttpStatus.OK);
        } else
            return new ResponseEntity<>("Can't delete user with id:" + id + " because he is missing.", HttpStatus.NOT_FOUND);
    }
}
