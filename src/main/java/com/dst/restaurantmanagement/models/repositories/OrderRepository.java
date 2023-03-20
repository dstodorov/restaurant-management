package com.dst.restaurantmanagement.models.repositories;

import com.dst.restaurantmanagement.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
