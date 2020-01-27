package com.mikesterry.springbootbuildingblocks.controllers;

import com.mikesterry.springbootbuildingblocks.entities.Order;
import com.mikesterry.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.mikesterry.springbootbuildingblocks.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class OrderController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}/orders")
    public List<Order> getAllOrders(@PathVariable("id") Long id) throws UserNotFoundException {
        return userService.getAllOrdersById(id);
    }
}
