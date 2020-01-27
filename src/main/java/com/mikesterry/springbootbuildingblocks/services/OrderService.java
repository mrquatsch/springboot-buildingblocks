package com.mikesterry.springbootbuildingblocks.services;

import com.mikesterry.springbootbuildingblocks.entities.Order;
import com.mikesterry.springbootbuildingblocks.exceptions.OrderNotFoundException;
import com.mikesterry.springbootbuildingblocks.repositories.OrderRepository;
import com.mikesterry.springbootbuildingblocks.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public Order createOrder(Order order) {
        Long orderId = order.getId();
        if (orderRepository.findById(orderId) != null) {
            // throw exception later
        }
        System.out.println("Saving order");
        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long id) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(id);

        if(!order.isPresent()) {
            throw new OrderNotFoundException(Constants.ORDER_NOT_FOUND_EXCEPTION_MESSAGE + id);
        }
        return order;
    }
}
