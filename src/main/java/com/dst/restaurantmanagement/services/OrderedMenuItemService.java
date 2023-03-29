package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.DishStatus;
import com.dst.restaurantmanagement.models.entities.OrderedMenuItem;
import com.dst.restaurantmanagement.repositories.OrderedMenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderedMenuItemService {
    private final OrderedMenuItemRepository orderedMenuItemRepository;


    public void startCook(Long orderedItemId) {
        Optional<OrderedMenuItem> orderedItem = this.orderedMenuItemRepository.findById(orderedItemId);

        orderedItem.ifPresent(item -> {
            item.setStatus(DishStatus.COOKING);
            this.orderedMenuItemRepository.save(item);
        });
    }

    public void finishCook(Long orderedItemId) {

        Optional<OrderedMenuItem> cookingItem = this.orderedMenuItemRepository.findById(orderedItemId);

        cookingItem.ifPresent(item -> {
            item.setStatus(DishStatus.COOKED);
            this.orderedMenuItemRepository.save(item);
        });
    }
}
