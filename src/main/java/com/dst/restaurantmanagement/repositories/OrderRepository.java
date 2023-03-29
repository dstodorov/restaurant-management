package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.enums.DishStatus;
import com.dst.restaurantmanagement.enums.OrderStatus;
import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.models.dto.CookingItemDTO;
import com.dst.restaurantmanagement.models.dto.OrderedItemDTO;
import com.dst.restaurantmanagement.models.entities.MenuItem;
import com.dst.restaurantmanagement.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByWaiterIdAndStatus(Long waiterId, OrderStatus status);

    @Query("SELECT new com.dst.restaurantmanagement.models.dto.OrderedItemDTO(o.table.id, mi.id, mi.menuItem.name) FROM Order o JOIN o.menuItems mi WHERE o.waiter.id = :waiterId AND mi.status = 'COOKED' ORDER BY o.table.id")
    List<OrderedItemDTO> findAllByWaiterId(Long waiterId);

    @Query("SELECT new com.dst.restaurantmanagement.models.dto.CookingItemDTO(o.id, mi.menuItem.name, mi.id) FROM Order o JOIN o.menuItems mi WHERE mi.status = :itemStatus")
    List<CookingItemDTO> findAllByOrderedItemStatus(DishStatus itemStatus);

    @Query("SELECT COUNT(o) FROM Order o JOIN o.menuItems mi WHERE o.id = :orderId AND mi.status <> 'DONE'")
    Long countOfNonServedItems(Long orderId);
}
