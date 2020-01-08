package com.mikesterry.springbootbuildingblocks.services;

import com.mikesterry.springbootbuildingblocks.entities.User;
import com.mikesterry.springbootbuildingblocks.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            return null;
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    public User updateUserById(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
