package com.dst.restaurantmanagement.models.repositories;

import com.dst.restaurantmanagement.models.entities.FoodWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodWarehouseRepository extends JpaRepository<FoodWarehouse, Long> {
}
