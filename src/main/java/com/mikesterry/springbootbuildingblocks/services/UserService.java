package com.mikesterry.springbootbuildingblocks.services;

import com.mikesterry.springbootbuildingblocks.entities.Order;
import com.mikesterry.springbootbuildingblocks.entities.User;
import com.mikesterry.springbootbuildingblocks.exceptions.UserExistsException;
import com.mikesterry.springbootbuildingblocks.exceptions.UserNotFoundException;
import com.mikesterry.springbootbuildingblocks.repositories.UserRepository;
import com.mikesterry.springbootbuildingblocks.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) throws UserExistsException {
        String username = user.getUsername();
        if (userRepository.findByUsername(username) != null) {
            throw new UserExistsException(Constants.USER_EXISTS_EXCEPTION_MESSAGE);
        }
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById( id );

        if(!user.isPresent()) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND_EXCEPTION_MESSAGE + id);
        }
        return user;
    }

    public User updateUserById(Long id, User user) throws UserNotFoundException {
        if(!userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND_EXCEPTION_MESSAGE + id);
        }
        user.setUserId(id);
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, Constants.USER_NOT_FOUND_EXCEPTION_MESSAGE);
        }
        userRepository.deleteById(id);
    }

    public List<Order> getAllOrdersById(Long id) throws UserNotFoundException {
        if(!userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND_EXCEPTION_MESSAGE + id);
        }
        Optional<User> user = userRepository.findById(id);
        return user.get().getOrders();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
