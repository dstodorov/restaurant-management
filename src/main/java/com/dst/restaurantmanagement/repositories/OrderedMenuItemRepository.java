package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.models.entities.OrderedMenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedMenuItemRepository extends JpaRepository<OrderedMenuItem, Long> {
}
