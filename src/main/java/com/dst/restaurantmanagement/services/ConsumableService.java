package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.repositories.ConsumableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumableService {
    private final ConsumableRepository consumableRepository;

    @Autowired
    public ConsumableService(ConsumableRepository consumableRepository) {
        this.consumableRepository = consumableRepository;
    }
}
