package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.repositories.FoodWarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodWarehouseService {
    private final FoodWarehouseRepository foodWarehouseRepository;

    @Autowired
    public FoodWarehouseService(FoodWarehouseRepository foodWarehouseRepository) {
        this.foodWarehouseRepository = foodWarehouseRepository;
    }
}
