package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.repositories.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }
}

