package com.mikesterry.springbootbuildingblocks.controllers;

import com.mikesterry.springbootbuildingblocks.entities.Order;
import com.mikesterry.springbootbuildingblocks.entities.User;
import com.mikesterry.springbootbuildingblocks.exceptions.OrderNotFoundException;
import com.mikesterry.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.mikesterry.springbootbuildingblocks.services.OrderService;
import com.mikesterry.springbootbuildingblocks.services.UserService;
import com.mikesterry.springbootbuildingblocks.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hateoas/users")
public class OrderHateoasController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}/orders")
    public List<Order> getAllOrders(@PathVariable("id") Long id) throws UserNotFoundException {
        return userService.getAllOrdersById(id);
    }

    @GetMapping("/orders/{id}")
    public Optional<Order> getOrderById(@PathVariable("id") Long id) {
        try {
            return orderService.getOrderById(id);
        } catch (OrderNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PostMapping("/{id}/orders")
    public Order createOrder(@PathVariable Long id, @RequestBody Order order) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);
        if(!userOptional.isPresent()) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        User user = userOptional.get();
        order.setUser(user);
        return orderService.createOrder(order);
    }
}
