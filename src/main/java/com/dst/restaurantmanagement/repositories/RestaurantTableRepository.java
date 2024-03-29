package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    List<RestaurantTable> findAllBySeats(Integer seats);

    List<RestaurantTable> findAllByStatus(TableStatus status);

    List<RestaurantTable> findAllBySeatsAndStatus(Integer seats, TableStatus status);
}
