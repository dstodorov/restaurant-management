package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.DishStatus;
import com.dst.restaurantmanagement.enums.EventType;
import com.dst.restaurantmanagement.models.entities.Employee;
import com.dst.restaurantmanagement.models.entities.MenuItem;
import com.dst.restaurantmanagement.models.entities.OrderedMenuItem;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.repositories.EmployeeRepository;
import com.dst.restaurantmanagement.repositories.MenuItemRepository;
import com.dst.restaurantmanagement.repositories.OrderedMenuItemRepository;
import com.dst.restaurantmanagement.util.EventPublisher;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderedMenuItemService {
    private final OrderedMenuItemRepository orderedMenuItemRepository;
    private final EmployeeRepository employeeRepository;


    @Transactional
    public void startCook(Long orderedItemId, RMUserDetails userDetails) {
        Optional<OrderedMenuItem> orderedItem = this.orderedMenuItemRepository.findById(orderedItemId);
        Optional<Employee> cook = this.employeeRepository.findById(userDetails.getId());


        orderedItem.ifPresent(item -> {
            item.setStatus(DishStatus.COOKING);
            item.setCook(cook.get());
            Long itemId = this.orderedMenuItemRepository.save(item).getId();

            EventPublisher.publish(userDetails, itemId, this, EventType.COOKING_ITEM_STATE.name(), DishStatus.ORDERED.name(), DishStatus.COOKING.name());
        });
    }

    public void finishCook(Long orderedItemId, RMUserDetails userDetails) {

        Optional<OrderedMenuItem> cookingItem = this.orderedMenuItemRepository.findById(orderedItemId);

        cookingItem.ifPresent(item -> {
            item.setStatus(DishStatus.COOKED);
            Long itemId = this.orderedMenuItemRepository.save(item).getId();

            EventPublisher.publish(userDetails, itemId, this, EventType.COOKING_ITEM_STATE.name(), DishStatus.COOKING.name(), DishStatus.COOKED.name());
        });
    }
}
