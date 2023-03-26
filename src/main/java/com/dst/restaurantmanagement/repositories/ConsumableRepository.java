package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.enums.ConsumableType;
import com.dst.restaurantmanagement.models.entities.Consumable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Repository
public interface ConsumableRepository extends JpaRepository<Consumable, Long> {
    @Query("SELECT c FROM Consumable c WHERE c.expiryDate < :expiryDate")
    List<Consumable> getExpiringConsumables(LocalDate expiryDate);

    @Query("SELECT c FROM Consumable c WHERE c.expiryDate >= :expiryDate AND c.type = :cType")
    List<Consumable> getNonExpiringConsumablesByType(LocalDate expiryDate, ConsumableType cType);
}
