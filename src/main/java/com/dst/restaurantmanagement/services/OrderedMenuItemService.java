package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.repositories.OrderedMenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderedMenuItemService {
    private final OrderedMenuItemRepository orderedMenuItemRepository;

    @Autowired
    public OrderedMenuItemService(OrderedMenuItemRepository orderedMenuItemRepository) {
        this.orderedMenuItemRepository = orderedMenuItemRepository;
    }
}
