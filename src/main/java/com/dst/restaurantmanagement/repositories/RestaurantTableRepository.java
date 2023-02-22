package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
}
