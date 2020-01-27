package com.mikesterry.springbootbuildingblocks.repositories;

import com.mikesterry.springbootbuildingblocks.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
