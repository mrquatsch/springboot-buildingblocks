package com.mikesterry.springbootbuildingblocks.controllers;

import com.mikesterry.springbootbuildingblocks.entities.User;
import com.mikesterry.springbootbuildingblocks.exceptions.UserExistsException;
import com.mikesterry.springbootbuildingblocks.exceptions.UserNameNotFoundException;
import com.mikesterry.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.mikesterry.springbootbuildingblocks.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/createUser")
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
        try {
            userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } catch (UserExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable("id") Long id) {
        try {
            return userService.getUserById( id );
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/users/{id}")
    public User updateUserById(@PathVariable("id") Long id , @RequestBody User user) {
        try {
            return userService.updateUserById( id, user );
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/users/byusername/{username}")
    public User getUserByUsername(@PathVariable("username") String username) throws UserNameNotFoundException {
        User user = userService.getUserByUsername(username);
        if(user == null) {
            throw new UserNameNotFoundException("Username: '" + username + "' not found in User repository");
        }
        return user;
    }
}
