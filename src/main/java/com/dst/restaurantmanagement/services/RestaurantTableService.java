package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.models.dto.AddTableDTO;
import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.repositories.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public void saveTable(AddTableDTO addTableDTO) {
        RestaurantTable table = new RestaurantTable();
        table.setSeats(addTableDTO.getSeats());
        table.setStatus(TableStatus.FREE);

        this.restaurantTableRepository.save(table);
    }

    public List<RestaurantTable> getAllTables() {
        return this.restaurantTableRepository.findAll();
    }
}

