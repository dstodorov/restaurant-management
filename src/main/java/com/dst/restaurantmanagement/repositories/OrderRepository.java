package com.dst.restaurantmanagement.repositories;

import com.dst.restaurantmanagement.enums.DishStatus;
import com.dst.restaurantmanagement.enums.OrderStatus;
import com.dst.restaurantmanagement.models.dto.CookingItemDTO;
import com.dst.restaurantmanagement.models.dto.OrderedItemDTO;
import com.dst.restaurantmanagement.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByWaiterIdAndStatus(Long waiterId, OrderStatus status);

    @Query("SELECT new com.dst.restaurantmanagement.models.dto.OrderedItemDTO(o.table.id, mi.id, mi.menuItem.name) FROM Order o JOIN o.menuItems mi WHERE o.waiter.id = :waiterId AND mi.status = 'COOKED' ORDER BY o.table.id")
    List<OrderedItemDTO> findAllByWaiterId(Long waiterId);

    @Query("SELECT new com.dst.restaurantmanagement.models.dto.CookingItemDTO(o.id, mi.menuItem.name, mi.id) FROM Order o JOIN o.menuItems mi WHERE mi.status = :itemStatus")
    List<CookingItemDTO> findAllByOrderedItemStatus(DishStatus itemStatus);

    @Query("SELECT new com.dst.restaurantmanagement.models.dto.CookingItemDTO(o.id, mi.menuItem.name, mi.id) FROM Order o JOIN o.menuItems mi WHERE mi.status = :itemStatus AND mi.cook.id = :cookId")
    List<CookingItemDTO> findAllByOrderedItemsByStatusAndCook(DishStatus itemStatus, Long cookId);

    @Query("SELECT COUNT(o) FROM Order o JOIN o.menuItems mi WHERE o.id = :orderId AND mi.status <> 'SERVED'")
    Long countOfNonServedItems(Long orderId);

    @Query("SELECT oi.menuItem.name FROM Order o JOIN o.menuItems oi WHERE DATE(o.orderTime) = DATE(NOW()) GROUP BY oi.menuItem.id ORDER BY COUNT(oi.menuItem.id) DESC LIMIT 1")
    String getProductOfTheDay();

    @Query("SELECT o.table.id FROM Order o WHERE DATE(o.orderTime) = DATE(NOW()) GROUP BY o.table.id ORDER BY COUNT(o.id) DESC LIMIT 1")
    Long getMostUsedTable();

    @Query("SELECT CONCAT(o.waiter.firstName, ' ', o.waiter.lastName, ' (', o.waiter.username, ')') FROM Order o WHERE DATE(o.orderTime) = DATE(NOW()) GROUP BY o.waiter.username ORDER BY COUNT(o.waiter.username) DESC LIMIT 1")
    String getEmployeeOfTheDay();

    @Query("SELECT SUM(omi.menuItem.salePrice) FROM Order o JOIN o.menuItems omi WHERE DATE(o.orderTime) = CURDATE()")
    BigDecimal getDailyTurnover();

    @Query("SELECT SUM(omi.menuItem.salePrice) - SUM(omi.menuItem.purchasePrice) FROM Order o JOIN o.menuItems omi WHERE DATE(o.orderTime) = CURDATE()")
    BigDecimal getDailyPureProfit();

    @Query("SELECT SUM(omi.menuItem.salePrice) FROM Order o JOIN o.menuItems omi")
    BigDecimal getTotalTurnover();

    @Query("SELECT SUM(omi.menuItem.salePrice) - SUM(omi.menuItem.purchasePrice) FROM Order o JOIN o.menuItems omi")
    BigDecimal getTotalPureProfit();
}
