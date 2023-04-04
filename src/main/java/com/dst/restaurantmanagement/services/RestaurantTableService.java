package com.dst.restaurantmanagement.services;

import com.dst.restaurantmanagement.enums.EventType;
import com.dst.restaurantmanagement.enums.TableStatus;
import com.dst.restaurantmanagement.events.ChangeStatusEvent;
import com.dst.restaurantmanagement.models.dto.AddTableDTO;
import com.dst.restaurantmanagement.models.entities.RestaurantTable;
import com.dst.restaurantmanagement.models.user.RMUserDetails;
import com.dst.restaurantmanagement.repositories.RestaurantTableRepository;
import com.dst.restaurantmanagement.util.AppConstants;
import com.dst.restaurantmanagement.util.EventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;

    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public void saveTable(AddTableDTO addTableDTO, RMUserDetails userDetails) {
        RestaurantTable table = new RestaurantTable();
        table.setSeats(addTableDTO.getSeats());
        table.setStatus(TableStatus.FREE);

        Long tableId = this.restaurantTableRepository.save(table).getId();

        EventPublisher.publish(userDetails, tableId, this, EventType.TABLE_STATE.name(), AppConstants.CREATED_OBJECT_STATUS, TableStatus.FREE.name());
    }

    public List<RestaurantTable> getTables() {
        return this.restaurantTableRepository.findAll();
    }

    public List<RestaurantTable> getTables(TableStatus status) {
        return this.restaurantTableRepository.findAllByStatus(status);
    }

    public List<RestaurantTable> getTables(Integer seats) {
        return this.restaurantTableRepository.findAllBySeatsAndStatus(seats, TableStatus.FREE);
    }

    public void deleteTable(Long id) {
        this.restaurantTableRepository.deleteById(id);
    }

    public void accommodateTable(Long id, RMUserDetails userDetails) {
        Optional<RestaurantTable> restaurantTable = this.restaurantTableRepository.findById(id);

        restaurantTable.get().setStatus(TableStatus.PENDING);
        Long objectId = this.restaurantTableRepository.saveAndFlush(restaurantTable.get()).getId();

        // Publish event for the updated status
        EventPublisher.publish(userDetails, objectId, this, EventType.TABLE_STATE.name(), TableStatus.FREE.name(), TableStatus.PENDING.name());
    }

    public List<RestaurantTable> getAllPendingTables() {
        return this.restaurantTableRepository.findAllByStatus(TableStatus.PENDING);
    }

    public Optional<RestaurantTable> findById(Long tableId) {
        return this.restaurantTableRepository.findById(tableId);
    }
}

