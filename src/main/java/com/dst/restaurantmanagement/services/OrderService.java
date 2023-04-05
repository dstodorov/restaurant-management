package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.*;
import com.dst.restaurantmanagement.models.dto.*;
import com.dst.restaurantmanagement.models.entities.*;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.repositories.MenuItemRepository;
import com.dst.restaurantmanagement.repositories.OrderRepository;
import com.dst.restaurantmanagement.repositories.OrderedMenuItemRepository;
import com.dst.restaurantmanagement.repositories.RestaurantTableRepository;
import com.dst.restaurantmanagement.util.AppConstants;
import com.dst.restaurantmanagement.util.EventPublisher;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EmployeeService employeeService;
    private final RestaurantTableRepository restaurantTableRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderedMenuItemRepository orderedMenuItemRepository;

    public void startService(Long tableId, RMUserDetails userDetails) {
        Optional<Employee> employee = this.employeeService.getByUsername(userDetails.getUsername());
        Optional<RestaurantTable> table = this.restaurantTableRepository.findById(tableId);

        table.get().setStatus(TableStatus.USED);

        Long tableObjectId = restaurantTableRepository.saveAndFlush(table.get()).getId();

        EventPublisher.publish(userDetails, tableObjectId, this, EventType.TABLE_STATE.name(), TableStatus.PENDING.name(), TableStatus.USED.name());

        Order order = Order
                .builder()
                .waiter(employee.get())
                .table(table.get())
                .orderTime(LocalDateTime.now())
                .menuItems(new ArrayList<>())
                .status(OrderStatus.OPEN)
                .build();

        Long newOrderId = this.orderRepository.save(order).getId();

        EventPublisher.publish(userDetails, newOrderId, this, EventType.ORDER_STATE.name(), AppConstants.CREATED_OBJECT_STATUS, OrderStatus.OPEN.name());
    }

    public List<UserOpenOrderDTO> getCurrentUserUsedTablesIds(RMUserDetails userDetails) {
        return this.orderRepository.findAllByWaiterIdAndStatus(userDetails.getId(), OrderStatus.OPEN)
                .stream()
                .map(o -> UserOpenOrderDTO.builder()
                        .orderId(o.getId())
                        .tableId(o.getTable().getId())
                        .canBeClosed(hasNonServedItems(o.getId()))
                        .build())
                .toList();
    }

    @Transactional
    public void addToOrder(Long orderId, Long itemId, RMUserDetails userDetails) {

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
            updateOrder(order, menuItem, status, userDetails);

            // Decrease item current quantity
            updateItemQuantity(menuItem);
        });
    }


    public List<OrderedItemDTO> getOrderedItemsByUser(RMUserDetails userDetails) {
        return this.orderRepository.findAllByWaiterId(userDetails.getId());
    }

    public void serve(Long id, RMUserDetails userDetails) {
        Optional<OrderedMenuItem> orderedMenuItem = this.orderedMenuItemRepository.findById(id);

        orderedMenuItem.ifPresent(item -> {
            item.setStatus(DishStatus.SERVED);
            Long dishId = this.orderedMenuItemRepository.save(item).getId();

            EventPublisher.publish(userDetails, dishId, this, EventType.COOKING_ITEM_STATE.name(), DishStatus.COOKED.name(), DishStatus.SERVED.name());
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

    private void updateOrder(Optional<Order> order, Optional<MenuItem> menuItem, DishStatus status, RMUserDetails userDetails) {
        OrderedMenuItem orderedMenuItem = OrderedMenuItem.builder()
                .menuItem(menuItem.get())
                .orderTime(LocalDateTime.now())
                .status(status)
                .build();

        Long orderedItemId = this.orderedMenuItemRepository.save(orderedMenuItem).getId();

        if (status == DishStatus.ORDERED) {
            EventPublisher.publish(userDetails, orderedItemId, this, EventType.COOKING_ITEM_STATE.name(), AppConstants.CREATED_OBJECT_STATUS, DishStatus.ORDERED.name());
        } else if (status == DishStatus.COOKED) {
            EventPublisher.publish(userDetails, orderedItemId, this, EventType.COOKING_ITEM_STATE.name(), AppConstants.CREATED_OBJECT_STATUS, DishStatus.COOKED.name());
        }

        order.get().getMenuItems().add(orderedMenuItem);

        this.orderRepository.save(order.get());
    }

    private Boolean hasNonServedItems(Long orderId) {
        Long countOfNonServedItems = this.orderRepository.countOfNonServedItems(orderId);
        return countOfNonServedItems > 0;
    }

    public OrderDetailsDTO getOrderById(Long orderId) {

        Optional<Order> orderById = this.orderRepository.findById(orderId);

        Map<String, ItemDetails> items = new LinkedHashMap<>();
        Order order = orderById.get();

        order.getMenuItems().forEach(item -> {
            ItemDetails itemDetails;

            if (items.containsKey(item.getMenuItem().getName())) {
                itemDetails = items.get(item.getMenuItem().getName());
                itemDetails.setCount(itemDetails.getCount() + 1);

            } else {
                itemDetails = ItemDetails
                        .builder()
                        .name(item.getMenuItem().getName())
                        .pricePerItem(item.getMenuItem().getSalePrice())
                        .count(1)
                        .build();

            }
            items.put(item.getMenuItem().getName(), itemDetails);
        });

        BigDecimal totalAmount = items.values()
                .stream()
                .map(item -> item.getPricePerItem().multiply(BigDecimal.valueOf(item.getCount())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return OrderDetailsDTO
                .builder()
                .currentTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                .tableNumber(order.getTable().getId())
                .orderId(order.getId())
                .waiterName(String.format("%s %s", order.getWaiter().getFirstName(), order.getWaiter().getLastName()))
                .items(items)
                .totalAmount(totalAmount)
                .build();
    }

    public void closeOrder(Long orderId, RMUserDetails userDetails) {
        Optional<Order> orderById = this.orderRepository.findById(orderId);

        orderById.ifPresent(order -> {
            order.setOrderClosed(LocalDateTime.now());
            order.setStatus(OrderStatus.CLOSED);
            Long orderIdEvent = this.orderRepository.save(order).getId();
            EventPublisher.publish(userDetails, orderIdEvent, this, EventType.ORDER_STATE.name(), OrderStatus.OPEN.name(), OrderStatus.CLOSED.name());

            RestaurantTable table = order.getTable();

            table.setStatus(TableStatus.FREE);

            Long tableId = this.restaurantTableRepository.save(table).getId();

            EventPublisher.publish(userDetails, tableId, this, EventType.TABLE_STATE.name(), TableStatus.USED.name(), TableStatus.FREE.name());
        });
    }

    public List<CookingItemDTO> getOrderedItemsByStatusAndCook(DishStatus itemStatus, RMUserDetails userDetails) {
        return this.orderRepository.findAllByOrderedItemsByStatusAndCook(itemStatus, userDetails.getId());
    }
}
