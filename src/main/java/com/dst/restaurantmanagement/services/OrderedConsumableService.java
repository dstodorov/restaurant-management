package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.repositories.OrderedConsumableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderedConsumableService {
    private final OrderedConsumableRepository orderedConsumableRepository;

    @Autowired
    public OrderedConsumableService(OrderedConsumableRepository orderedConsumableRepository) {
        this.orderedConsumableRepository = orderedConsumableRepository;
    }
}
