package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    private final UserRepository userRepository;

    private Logger logger = Logger.getLogger(String.valueOf(UserService.class));

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id){
        logger.info("Cache Miss for user id: " + id);
        Optional<User> user =  this.userRepository.findById(id);
        return user.orElse(null);
    }

    public User saveUser(User user){
        logger.info("Saving user with name: " + user.getName());
        return this.userRepository.save(user);
    }
}
