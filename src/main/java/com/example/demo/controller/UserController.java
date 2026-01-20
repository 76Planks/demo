package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class UserController {

    private final UserService userService;

    private final Logger logger = Logger.getLogger(String.valueOf(UserController.class));

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id){
        logger.info("Fetching user with id: " + id);
        return this.userService.getUserById(id);
    }

    @PostMapping("/user")
    public User saveUser(@RequestBody User user){
        logger.info("Saving user with name: " + user.getName());
        return this.userService.saveUser(user);
    }
}
