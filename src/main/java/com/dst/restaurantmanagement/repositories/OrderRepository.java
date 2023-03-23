package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.enums.OrderStatus;
import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByWaiterIdAndStatus(Long waiterId, OrderStatus status);
}
