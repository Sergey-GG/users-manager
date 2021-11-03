package springboot.webapp.usersmanager.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.webapp.usersmanager.Services.UserService;
import springboot.webapp.usersmanager.entities.User;


import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @PostMapping("/users/new")
    public String createUser(@RequestBody User user) {
        userService.saveUser(user);
        return "User has been created." + " Created user:\n" + user;
    }

    @PostMapping("/user/{id}/update")
//    @PutMapping("/user/{id}")
    public String updateUser(@RequestBody User user, @PathVariable int id) {
        userService.updateUser(user.getName(), user.getSurname(), user.getEmail(), user.getRole(), id);
        return "User has been updated." + " Updated user:\n" + user;
    }

    @PostMapping("/user/{id}/delete")
//    @DeleteMapping("user/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "User has been deleted.";
    }

}
