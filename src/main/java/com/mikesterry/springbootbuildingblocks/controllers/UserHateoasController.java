package com.mikesterry.springbootbuildingblocks.controllers;

import com.mikesterry.springbootbuildingblocks.entities.Order;
import com.mikesterry.springbootbuildingblocks.entities.User;
import com.mikesterry.springbootbuildingblocks.exceptions.UserExistsException;
import com.mikesterry.springbootbuildingblocks.exceptions.UserNameNotFoundException;
import com.mikesterry.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.mikesterry.springbootbuildingblocks.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "hateoas/users")
@Validated
public class UserHateoasController {

    @Autowired
    private UserService userService;

    @GetMapping
    public CollectionModel<User> getAllUsers() throws UserNotFoundException {
        List<User> users = userService.getAllUsers();
        for(User user : users) {
            Long userId = user.getUserId();
            Link selfLink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selfLink);
            CollectionModel<Order> orders = ControllerLinkBuilder.methodOn(OrderHateoasController.class)
                    .getAllOrders(userId);
            Link ordersLink = ControllerLinkBuilder.linkTo(orders).withRel("all-orders");
            user.add(ordersLink);
        }
        Link selfLinkGetAllUsers = ControllerLinkBuilder.linkTo(this.getClass()).withSelfRel();
        CollectionModel<User> finalResources = new CollectionModel<User>(users, selfLinkGetAllUsers);

        return finalResources;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder) {
        try {
            userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/{id}").buildAndExpand(user.getUserId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } catch (UserExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            Optional<User> userOptional = userService.getUserById( id );
            User user = userOptional.get();
            Long userId = user.getUserId();
            Link selfLink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selfLink);
            EntityModel<User> finalResource = new EntityModel<User>(user);
            return finalResource;
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public User updateUserById(@PathVariable("id") Long id , @RequestBody User user) {
        try {
            return userService.updateUserById( id, user );
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/byusername/{username}")
    public User getUserByUsername(@PathVariable("username") String username) throws UserNameNotFoundException {
        User user = userService.getUserByUsername(username);
        if(user == null) {
            throw new UserNameNotFoundException("Username: '" + username + "' not found in User repository");
        }
        return user;
    }
}
