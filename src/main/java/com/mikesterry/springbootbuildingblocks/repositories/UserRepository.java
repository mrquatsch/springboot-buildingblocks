package com.mikesterry.springbootbuildingblocks.repositories;

import com.mikesterry.springbootbuildingblocks.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);

}
