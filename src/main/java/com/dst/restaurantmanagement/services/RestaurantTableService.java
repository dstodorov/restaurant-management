package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.events.SaveTableEvent;
import com.dst.restaurantmanagement.models.dto.AddTableDTO;
import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.repositories.RestaurantTableRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void saveTable(AddTableDTO addTableDTO) {
        RestaurantTable table = new RestaurantTable();
        table.setSeats(addTableDTO.getSeats());
        table.setStatus(TableStatus.FREE);

        this.restaurantTableRepository.save(table);

        applicationEventPublisher.publishEvent(new SaveTableEvent(this));
    }

    public List<RestaurantTable> getTables() {
        return this.restaurantTableRepository.findAll();
    }

    public List<RestaurantTable> getTables(Integer seats) {
        return this.restaurantTableRepository.findAllBySeats(seats);
    }

    public void deleteTable(Long id) {
        this.restaurantTableRepository.deleteById(id);
    }
}

