package com.mikesterry.springbootbuildingblocks.services;

import com.mikesterry.springbootbuildingblocks.entities.User;
import com.mikesterry.springbootbuildingblocks.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        String username = user.getUsername();
        if (userRepository.findByUsername(username) != null) {
            // throw an exception later
        }
        return userRepository.save(user);
    }
}
