package com.rain.rain.controller;


import com.rain.rain.exception.ResourceNotFoundException;
import com.rain.rain.models.User;
import com.rain.rain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rain")
public class UserController {

    @Autowired
    UserRepo userRepository;


    @GetMapping("/users")
    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    // Get a Single User
    @GetMapping("/users/{id}")
    public User GetUserById(@PathVariable(value = "id") int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    }

    // Create a new User
    @PostMapping("/users")
    public User CreateNewUser(@RequestBody User user) {
        return userRepository.save(user);
    }



    // Update a User
    @PutMapping("/users/{id}")
    public User UpdateUser(@PathVariable(value = "id") int userId,
                           @RequestBody User userDetails) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        user.setFirst_name(userDetails.getFirst_name());
        user.setLast_name(userDetails.getLast_name());
        user.setAvatar(userDetails.getAvatar());
        user.setEmail(userDetails.getEmail());
        user.setAccount(userDetails.getAccount());
        user.setPassword(userDetails.getPassword());

        return userRepository.save(user);
    }

    // Delete a User
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> DeleteUser(@PathVariable(value = "id") int userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userRepository.delete(user);

        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/search")
    public List<User> GetUsersByEmail(@RequestParam(value = "email", required = false) String email) {

        return userRepository.findByEmail(email);
    }


}
