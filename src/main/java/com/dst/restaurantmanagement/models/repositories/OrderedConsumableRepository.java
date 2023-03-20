package com.dst.restaurantmanagement.models.repositories;

import com.dst.restaurantmanagement.models.entities.OrderedConsumable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderedConsumableRepository extends JpaRepository<OrderedConsumable, Long> {
}
