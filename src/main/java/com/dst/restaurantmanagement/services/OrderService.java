package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.DishStatus;
import com.dst.restaurantmanagement.enums.ItemType;
import com.dst.restaurantmanagement.enums.OrderStatus;
import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.models.dto.CookingItemDTO;
import com.dst.restaurantmanagement.models.dto.OrderedItemDTO;
import com.dst.restaurantmanagement.models.dto.UserOpenOrderDTO;
import com.dst.restaurantmanagement.models.entities.*;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.repositories.MenuItemRepository;
import com.dst.restaurantmanagement.repositories.OrderRepository;
import com.dst.restaurantmanagement.repositories.OrderedMenuItemRepository;
import com.dst.restaurantmanagement.repositories.RestaurantTableRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmployeeService employeeService;
    private final RestaurantTableRepository restaurantTableRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderedMenuItemRepository orderedMenuItemRepository;

    public void startService(Long tableId, Principal user) {
        Optional<Employee> employee = this.employeeService.getByUsername(user.getName());
        Optional<RestaurantTable> table = this.restaurantTableRepository.findById(tableId);

        table.get().setStatus(TableStatus.USED);

        restaurantTableRepository.saveAndFlush(table.get());

        Order order = Order.builder().waiter(employee.get()).table(table.get()).orderTime(LocalDateTime.now()).menuItems(new ArrayList<>()).status(OrderStatus.OPEN).build();

        this.orderRepository.save(order);
    }

    public List<UserOpenOrderDTO> getCurrentUserUsedTablesIds(RMUserDetails userDetails) {
        return this.orderRepository.findAllByWaiterIdAndStatus(userDetails.getId(), OrderStatus.OPEN)
                .stream()
                .map(o -> UserOpenOrderDTO.builder()
                        .orderId(o.getId())
                        .tableId(o.getTable().getId())
                        .build())
                .toList();
    }

    @Transactional
    public void addToOrder(Long orderId, Long itemId) {

        Optional<Order> order = this.orderRepository.findById(orderId);

        Optional<MenuItem> menuItem = menuItemRepository.findById(itemId);

        order.ifPresent(o -> {
            // Default - cook will get all items with ORDERED status for cooking
            DishStatus status = DishStatus.ORDERED;

            // Cook will not get this menu item for cooking if it's a drink
            if (menuItem.get().getType().equals(ItemType.DRINK)) {
                status = DishStatus.COOKED;
            }

            // Update order with the added item
            updateOrder(order, menuItem, status);

            // Decrease item current quantity
            updateItemQuantity(menuItem);
        });
    }



    public List<OrderedItemDTO> getOrderedItemsByUser(RMUserDetails userDetails) {
        return this.orderRepository.findAllByWaiterId(userDetails.getId());
    }

    public void serve(Long id) {
        Optional<OrderedMenuItem> orderedMenuItem = this.orderedMenuItemRepository.findById(id);

        orderedMenuItem.ifPresent(item -> {
            item.setStatus(DishStatus.DONE);
            this.orderedMenuItemRepository.save(item);
        });
    }

    public List<CookingItemDTO> getOrderedItemsByStatus(DishStatus itemStatus) {
        return this.orderRepository.findAllByOrderedItemStatus(itemStatus);
    }

    private void updateItemQuantity(Optional<MenuItem> menuItem) {
        int currentQuantity = menuItem.get().getCurrentQuantity();

        menuItem.get().setCurrentQuantity(currentQuantity - 1);

        this.menuItemRepository.save(menuItem.get());
    }

    private void updateOrder(Optional<Order> order, Optional<MenuItem> menuItem, DishStatus status) {
        OrderedMenuItem orderedMenuItem = OrderedMenuItem.builder()
                .menuItem(menuItem.get())
                .orderTime(LocalDateTime.now())
                .status(status)
                .build();

        this.orderedMenuItemRepository.save(orderedMenuItem);

        order.get().getMenuItems().add(orderedMenuItem);

        this.orderRepository.save(order.get());
    }
}
