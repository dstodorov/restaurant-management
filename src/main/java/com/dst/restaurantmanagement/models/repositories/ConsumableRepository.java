package com.dst.restaurantmanagement.models.repositories;

import com.dst.restaurantmanagement.models.entities.Consumable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumableRepository extends JpaRepository<Consumable, Long> {
}
