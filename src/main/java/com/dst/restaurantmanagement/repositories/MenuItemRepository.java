package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.enums.ItemType;
import com.dst.restaurantmanagement.models.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("SELECT c FROM MenuItem c WHERE c.expiryDate < :expiryDate")
    List<MenuItem> getExpiringMenuItems(LocalDate expiryDate);

    @Query("SELECT c FROM MenuItem c WHERE c.expiryDate >= :expiryDate AND c.type = :cType")
    List<MenuItem> getNonExpiringMenuItemsByType(LocalDate expiryDate, ItemType cType);
}
