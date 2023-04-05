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
    @Query("SELECT c FROM MenuItem c WHERE c.wasted = false and c.expiryDate < :expiryDate")
    List<MenuItem> getExpiringMenuItems(LocalDate expiryDate);

    @Query("SELECT i FROM MenuItem i WHERE i.expiryDate >= :expiryDate AND i.type = :cType")
    List<MenuItem> getNonExpiringMenuItemsByType(LocalDate expiryDate, ItemType cType);

    @Query("SELECT i FROM MenuItem i WHERE i.currentQuantity >= 1 AND i.wasted = false AND i.expiryDate >= :localDate ORDER BY i.type")
    List<MenuItem> getAvailableMenuItems(LocalDate localDate);
}